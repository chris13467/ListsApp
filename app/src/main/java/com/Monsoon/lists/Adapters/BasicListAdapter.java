package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.R;

public class BasicListAdapter extends RecyclerView.Adapter<BasicListAdapter.ViewHolder> {

    //TODO: add on long click listener
    public interface OnItemClickListener {
        void onItemClick(BaseListItem item);
        void onItemLongClick(BaseListItem item);
    }
    BasicList data;
    BasicListAdapter.OnItemClickListener listener;

    boolean isSelectMode;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView basicListView;
        FrameLayout overlay;

        CheckBox selectButton;
        public ViewHolder(View itemView){
            super(itemView);
            basicListView = itemView.findViewById(R.id.basic_item_text);
            overlay = itemView.findViewById(R.id.basic_overlay);
            selectButton = itemView.findViewById(R.id.selected_radio);

        }
        public void bind(final BaseListItem item, final BasicListAdapter.OnItemClickListener listener) {
            if (isSelectMode) overlay.setVisibility(View.VISIBLE);
            else item.setSelected(false);
            selectButton.setChecked(item.isSelected());

            overlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectButton.setChecked(!selectButton.isChecked());
                }
            });
            selectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item.setSelected(selectButton.isChecked());
                }
            });

            basicListView.setText(item.getContents());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(item);
                    item.setSelected(true);
                    return false;
                }
            });
        }
    }

    public BasicListAdapter(BasicList data, BasicListAdapter.OnItemClickListener listener){
        this.data = data;
        this.listener = listener;
        this.isSelectMode = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BasicListAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new BasicListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
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

    public void changeSelectMode(boolean b){
        this.isSelectMode = b;
    }

}
