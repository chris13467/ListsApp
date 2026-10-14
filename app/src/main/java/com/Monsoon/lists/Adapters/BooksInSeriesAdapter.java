package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Util;

import java.time.format.DateTimeFormatter;

public class BooksInSeriesAdapter extends AbstractListAdapter<BookList, BookItem, BooksInSeriesAdapter.ViewHolder> {
    private int positionOffset;

    public class ViewHolder extends ListsViewHolder<BookItem>{
        private TextView queueNumberView, releaseDate;
        private Button actionUpdate;


        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.book_item_text);
            queueNumberView = itemView.findViewById(R.id.book_number_view);
            actionUpdate = itemView.findViewById(R.id.action_update);
            releaseDate = itemView.findViewById(R.id.release_date_text_view);
        }

        @Override
        public void bind(BookItem item, onListItemClickedListener<BookItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            if (item.getContents().isBlank()){
                StringBuilder sb = new StringBuilder(item.getSeriesTitle());
                sb.append(" book ").append(item.getPosition()+positionOffset);
                textContent.setText(sb.toString());
            } else {
                textContent.setText(item.getContents());
            }
            queueNumberView.setText(String.valueOf(item.getPosition()+positionOffset));
            if (item.getReleaseDate() == null)
                releaseDate.setText(new StringBuilder("Release Date: ").append("Unknown"));
            else
                releaseDate.setText(new StringBuilder("Release Date: ").append(
                        item.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
            if (item.isUpcoming()){
                actionUpdate.setBackgroundColor(ContextCompat.getColor(actionUpdate.getContext(),R.color.upcoming));
                actionUpdate.setText("Release");
            } else {
                if (item.isRead()){
                    actionUpdate.setBackgroundColor(ContextCompat.getColor(actionUpdate.getContext(),R.color.read));
                    actionUpdate.setText(" Read  ");
                } else {
                    actionUpdate.setBackgroundColor(ContextCompat.getColor(actionUpdate.getContext(),R.color.not_read));
                    actionUpdate.setText("Unread ");
                }
            }
            actionUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.isUpcoming()){
                        item.setUpcoming(false);
                    } else {
                        item.setRead(!item.isRead());
                    }
                    listener.onItemSelected(item);
                }
            });

        }

        @Override
        protected boolean onListItemLongClicked(BookItem item, onListItemClickedListener<BookItem> listener, boolean isSelectMode) {
            // do nothing
            return false;
        }
    }

    public BooksInSeriesAdapter(BookList data, onListItemClickedListener<BookItem> listener){
        super(data, listener);
        this.positionOffset = 1;
    }
    public BooksInSeriesAdapter(BookList data, int positionOffset, onListItemClickedListener<BookItem> listener){
        super(data, listener);
        this.positionOffset = positionOffset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BooksInSeriesAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new BooksInSeriesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_in_series_item, parent, false));
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
