package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.Monsoon.lists.ListItems.NumberedItem;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public class NumberedListAdapter extends AbstractListAdapter<NumberedList, NumberedItem, NumberedListAdapter.ViewHolder> {


    public class ViewHolder extends ListsViewHolder<NumberedItem>{
        TextView queueNumberView;

        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.queue_item_text);
            queueNumberView = itemView.findViewById(R.id.queue_number_view);
            overlay = itemView.findViewById(R.id.queue_overlay);
            selectButton = itemView.findViewById(R.id.selected_radio_queue);

        }

        @Override
        public void bind(NumberedItem item, onListItemClickedListener<NumberedItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            textContent.setText(item.getContents());
            StringBuilder sb = new StringBuilder();
            queueNumberView.setText(sb.append(item.getPosition()+1).toString());
        }

    }

    public NumberedListAdapter(NumberedList data, onListItemClickedListener<NumberedItem> listener){
        super(data, listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NumberedListAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new NumberedListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.queue_list_item, parent, false));
        return viewHolder;
    }

    @Override
    protected NumberedItem getCurrentItem(int position) {
            return (NumberedItem) data.getList().get(position);
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }


}
