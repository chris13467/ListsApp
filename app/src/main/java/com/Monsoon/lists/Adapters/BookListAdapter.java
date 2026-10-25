package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Util;

public class BookListAdapter extends AbstractListAdapter<BookList, BookItem, BookListAdapter.ViewHolder> {

    public class ViewHolder extends ListsViewHolder<BookItem>{
        TextView queueNumberView;
        TextView tagsView;
        ImageView lockIcon;

        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.book_item_text);
            queueNumberView = itemView.findViewById(R.id.book_number_view);
            tagsView = itemView.findViewById(R.id.book_tags_text_view);
            lockIcon = itemView.findViewById(R.id.lock_icon);
        }

        @Override
        public void bind(BookItem item, onListItemClickedListener<BookItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            textContent.setText(item.getContents());
            queueNumberView.setText(String.valueOf(item.getPosition()+1));
            tagsView.setText(Util.getTagsString(item.getTags(), false));
            if (item.isLocked()){
                lockIcon.setImageResource(R.drawable.baseline_lock_24);
            } else {
                lockIcon.setImageResource(R.drawable.baseline_lock_open_24);
            }
        }

    }

    public BookListAdapter(BookList data, onListItemClickedListener<BookItem> listener){
        super(data, listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookListAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new BookListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_list_item, parent, false));
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

}
