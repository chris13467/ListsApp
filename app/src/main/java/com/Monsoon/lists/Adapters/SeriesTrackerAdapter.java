package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SeriesTrackerAdapter extends AbstractListAdapter<BookList, BookItem, SeriesTrackerAdapter.ViewHolder> {
    private int positionOffset;

    public class ViewHolder extends ListsViewHolder<BookItem>{
        private TextView releaseDate;
        private ImageView circleIcon;


        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.series_item_text);
            circleIcon = itemView.findViewById(R.id.circle_icon);
            releaseDate = itemView.findViewById(R.id.series_tags_text_view);
        }

        @Override
        public void bind(BookItem item, onListItemClickedListener<BookItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            textContent.setText(item.getContents());
            if (item.getReleaseDate().isBefore(LocalDate.now())){
                circleIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), R.color.read_no_upcoming)));
            } else if (item.getReleaseDate().minusDays(45).isBefore(LocalDate.now())){
                circleIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), R.color.coming_soon)));
            } else {
                circleIcon.setImageTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(itemView.getContext(), R.color.coming_eventually)));
            }

            releaseDate.setText("Release Date: " +
                    item.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        }

        @Override
        protected void onListItemClicked(BookItem item, onListItemClickedListener<BookItem> listener, boolean isSelectMode) {
            // do nothing
        }

        @Override
        protected boolean onListItemLongClicked(BookItem item, onListItemClickedListener<BookItem> listener, boolean isSelectMode) {
            // do nothing
            return false;
        }
    }

    public SeriesTrackerAdapter(BookList data, onListItemClickedListener<BookItem> listener){
        super(data, listener);
        this.positionOffset = 1;
    }
    public SeriesTrackerAdapter(BookList data, int positionOffset, onListItemClickedListener<BookItem> listener){
        super(data, listener);
        this.positionOffset = positionOffset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SeriesTrackerAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new SeriesTrackerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_series_list_item, parent, false));
        return viewHolder;
    }

    @Override
    protected BookItem getCurrentItem(int position) {
            return (BookItem) data.getList().get(position);
    }


    @Override
    public int getItemCount() {
        return data.getList().size();
    }

    public void onDataChanged(BookList data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setPositionOffset(int positionOffset){
        this.positionOffset = positionOffset;
    }
}
