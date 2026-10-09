package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Util;

public class BooksInSeriesAdapter extends AbstractListAdapter<BookList, BookItem, BooksInSeriesAdapter.ViewHolder> {

    public class ViewHolder extends ListsViewHolder<BookItem>{
        TextView queueNumberView, upcomingBanner;
        ImageButton actionUpdate;


        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.book_item_text);
            queueNumberView = itemView.findViewById(R.id.book_number_view);
            upcomingBanner = itemView.findViewById(R.id.upcoming_banner);
            actionUpdate = itemView.findViewById(R.id.action_update);
        }

        @Override
        public void bind(BookItem item, onListItemClickedListener<BookItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            if (item.getContents().isEmpty()){
                StringBuilder sb = new StringBuilder(item.getSeriesTitle());
                sb.append(" book ").append(item.getPosition()+1);
                textContent.setText(sb.toString());
            } else {
                textContent.setText(item.getContents());
            }
            textContent.setText(item.getContents());
            queueNumberView.setText(String.valueOf(item.getPosition()+1));
            if (item.isUpcoming()){
                upcomingBanner.setVisibility(View.VISIBLE);
                actionUpdate.setImageResource(android.R.drawable.ic_menu_revert);
                actionUpdate.setRotation(90f);
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.upcoming_purple));
            } else {
                upcomingBanner.setVisibility(View.GONE);
                actionUpdate.setImageResource(android.R.drawable.ic_menu_view);
                actionUpdate.setRotation(0f);
                if (item.isRead()){
                    itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.read_green));
                } else {
                    itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.not_read_yellow));
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
                    notifyDataSetChanged();
                    listener.onItemClick(item);
                }
            });

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

    public BooksInSeriesAdapter(BookList data, onListItemClickedListener<BookItem> listener){
        super(data, listener);
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


}
