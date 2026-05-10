package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.QueueItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.QueueList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public class QueueListAdapter extends AbstractListAdapter<QueueList, QueueItem, QueueListAdapter.ViewHolder> {


    public class ViewHolder extends ListsViewHolder<QueueItem>{
        TextView queueNumberView;

        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.queue_item_text);
            queueNumberView = itemView.findViewById(R.id.queue_number_view);
            overlay = itemView.findViewById(R.id.queue_overlay);
            selectButton = itemView.findViewById(R.id.selected_radio_queue);

        }

        @Override
        public void bind(QueueItem item, onListItemClickedListener<QueueItem> listener) {
            setUniversalViewBinding(item, listener, false);
            textContent.setText(item.getContents());
            StringBuilder sb = new StringBuilder();
            queueNumberView.setText(sb.append(item.getPosition()+1).toString());
        }

    }

    public QueueListAdapter(QueueList data, onListItemClickedListener<QueueItem> listener){
        super(data, listener);
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
    protected QueueItem getCurrentItem(int position) {
            return (QueueItem) data.getList().get(position);
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }


}
