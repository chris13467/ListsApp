package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.Lists.BookSeriesList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public class BookSeriesListAdapter extends AbstractListAdapter<BookSeriesList, BookSeriesItem, BookSeriesListAdapter.ViewHolder> {

    public class ViewHolder extends ListsViewHolder<BookSeriesItem>{

        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.basic_item_text);
        }

        @Override
        public void bind(BookSeriesItem item, onListItemClickedListener<BookSeriesItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            textContent.setText(item.getContents());
        }

        @Override
        protected void onListItemClicked(BookSeriesItem item, onListItemClickedListener<BookSeriesItem> listener, boolean isSelectMode) {
            if (isSelectMode){
                item.setSelected(!item.isSelected());
                listener.onItemLongClick(item);
            }
            else listener.onItemClick(item);
        }

        @Override
        protected boolean onListItemLongClicked(BookSeriesItem item, onListItemClickedListener<BookSeriesItem> listener, boolean isSelectMode) {
            item.setSelected(!item.isSelected());
            return super.onListItemLongClicked(item, listener, isSelectMode);
        }
    }

    public BookSeriesListAdapter(BookSeriesList data, onListItemClickedListener<BookSeriesItem> listener){
        super(data, listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookSeriesListAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new BookSeriesListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.basic_list_item, parent, false));
        return viewHolder;
    }

    @Override
    protected BookSeriesItem getCurrentItem(int position) {
            return (BookSeriesItem) data.getList().get(position);
    }


    @Override
    public int getItemCount() {
        return data.getList().size();
    }

}
