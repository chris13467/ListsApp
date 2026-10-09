package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;
import com.Monsoon.lists.ListItems.NumberedItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;


public class ListOfListsAdapter extends AbstractListAdapter<ListOfLists, ListOfListsWrapper, ListOfListsAdapter.ViewHolder> {

    public class ViewHolder extends ListsViewHolder<ListOfListsWrapper> {
        RecyclerView listPreview;
        CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.list_name_view);
            listPreview = itemView.findViewById(R.id.list_preview);
            cardView = itemView.findViewById(R.id.card_view);
        }

        @Override
        public void bind(ListOfListsWrapper item, onListItemClickedListener<ListOfListsWrapper> listener) {
            if (isSelectMode){
                if (item.isSelected()) itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext() ,R.color.transparent_gray));
                else itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext() ,R.color.clear));
            } else itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext() ,R.color.clear));

            textContent.setText(item.getList().getName());
            listPreview.setLayoutManager(new LinearLayoutManager(listPreview.getContext()));
            switch (item.getList().getListType()){
                case Constants.BASIC:
                    BasicListAdapter basicListAdapter = new BasicListAdapter((BasicList) item.getList(), new onListItemClickedListener<BaseListItem>() {
                        @Override
                        public void onItemClick(BaseListItem item_b) {
                            if (!isSelectMode) listener.onItemClick(item);
                            else {
                                item.setSelected(!item.isSelected());
                                listener.onItemLongClick(item);
                            }
                        }

                        @Override
                        public void onItemLongClick(BaseListItem item) {

                        }

                        @Override
                        public void onItemSelected(BaseListItem item) {

                        }
                    });
                    listPreview.setAdapter(basicListAdapter);
                    break;
                case Constants.TODO:
                case Constants.NUMBERED:
                case Constants.BOOK:
                    PreviewAdapter previewAdapter = new PreviewAdapter(item.getList(), new PreviewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick() {
                            if (!isSelectMode) listener.onItemClick(item);
                            else {
                                item.setSelected(!item.isSelected());
                                listener.onItemLongClick(item);
                            }
                        }

                        @Override
                        public void onItemLongClick() {
                            item.setSelected(!item.isSelected());
                            listener.onItemLongClick(item);
                        }
                    });
                    listPreview.setAdapter(previewAdapter);
                    break;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isSelectMode) listener.onItemClick(item);
                    else {
                        item.setSelected(!item.isSelected());
                        listener.onItemLongClick(item);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(item);
                    item.setSelected(!item.isSelected());
                    return true;
                }
            });
        }

    }

    public ListOfListsAdapter(ListOfLists data, onListItemClickedListener<ListOfListsWrapper> listener) {
        super(data, listener);
    }

    @NonNull
    @Override
    public ListOfListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListOfListsAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new ListOfListsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_card, parent, false));
        return viewHolder;
    }

    @Override
    protected ListOfListsWrapper getCurrentItem(int position) {
        return (ListOfListsWrapper) data.getList().get(position);
    }


    @Override
    public int getItemCount() {
        return data.getList().size();
    }

}
