package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.Lists.BookSeriesList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Util;

public class BookSeriesListAdapter extends AbstractListAdapter<BookSeriesList, BookSeriesItem, BookSeriesListAdapter.ViewHolder> {
    TextView tagTextView;
    ImageView circleIcon;
    public class ViewHolder extends ListsViewHolder<BookSeriesItem>{

        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.series_item_text);
            tagTextView = itemView.findViewById(R.id.series_tags_text_view);
            circleIcon = itemView.findViewById(R.id.circle_icon);
        }

        @Override
        public void bind(BookSeriesItem item, onListItemClickedListener<BookSeriesItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            textContent.setText(item.getContents());

            if (item.getNumberOfReleasedBooks() == item.getNumberOfReadBooks() && item.hasUpcomingBook()){
                circleIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), R.color.read_with_upcoming)));
            } else if (item.getNumberOfReleasedBooks() == item.getNumberOfReadBooks()){
                circleIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), R.color.read_no_upcoming)));
            } else if (item.hasUpcomingBook()) {
                circleIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), R.color.not_read_with_upcoming)));
            }else {
                circleIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), R.color.not_read_no_upcoming)));
            }
            tagTextView.setText(Util.getTagsString(item.getTags(), false));
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
                inflate(R.layout.book_series_list_item, parent, false));
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
