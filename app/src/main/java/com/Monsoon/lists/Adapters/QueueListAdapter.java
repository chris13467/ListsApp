package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.QueueItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.QueueList;
import com.Monsoon.lists.R;

public class QueueListAdapter extends RecyclerView.Adapter<QueueListAdapter.ViewHolder> {

    //TODO: add on long click listener
    public interface OnItemClickListener {
        void onItemClick(QueueItem item);
    }
    QueueList data;
    QueueListAdapter.OnItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView queueListView, queueNumberView;
        public ViewHolder(View itemView){
            super(itemView);
            queueListView = itemView.findViewById(R.id.queue_item_text);
            queueNumberView = itemView.findViewById(R.id.queue_number_view);

        }
        @SuppressLint("SetTextI18n")
        public void bind(final QueueItem item, final QueueListAdapter.OnItemClickListener listener) {
            queueListView.setText(item.getContents());
            StringBuilder sb = new StringBuilder();
            queueNumberView.setText(sb.append(item.getPosition()+1).toString());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public QueueListAdapter(QueueList data, QueueListAdapter.OnItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QueueListAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new QueueListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.queue_list_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QueueItem currentItem = (QueueItem) data.getList().get(position);
        holder.bind(currentItem, listener);
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }

}
