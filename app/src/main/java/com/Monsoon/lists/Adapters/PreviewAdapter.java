package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.content.res.Resources;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.QueueItem;
import com.Monsoon.lists.ListItems.TodoItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.TodoList;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.R;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick();
    }

       baseList data;
        OnItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView basicListView;
        public ViewHolder(View itemView){
            super(itemView);
            basicListView = itemView.findViewById(R.id.basic_item_text);


        }
        public void bind(final BaseListItem item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick();
                }
            });
            StringBuilder sb = new StringBuilder();
            switch (data.getListType()){
                case Constants.TODO:
                    basicListView.setText(item.getContents());
                    if (((TodoItem)item).isChecked()) basicListView.setPaintFlags(basicListView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    break;
                case Constants.QUEUE:
                    sb.append(((QueueItem)item).getPosition() + 1).append(" - ").append(item.getContents());
                    basicListView.setText(sb.toString());
                    break;
            }


        }
    }

    public PreviewAdapter(baseList data, OnItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PreviewAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new PreviewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.basic_list_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaseListItem currentItem = data.getList().get(position);
        holder.bind(currentItem, listener);
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }

}
