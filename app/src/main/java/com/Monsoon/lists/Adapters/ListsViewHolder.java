package com.Monsoon.lists.Adapters;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public abstract class ListsViewHolder<I extends BaseListItem> extends RecyclerView.ViewHolder {
    protected TextView textContent;

    public ListsViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bind(final I item, final onListItemClickedListener<I> listener);


    protected void setUniversalViewBinding(I item, onListItemClickedListener<I> listener, boolean isSelectMode){
        if (isSelectMode){
            if (item.isSelected()) itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext() ,R.color.transparent_gray));
            else itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext() ,R.color.white));
        } else itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext() ,R.color.white));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setSelected(!item.isSelected());
                listener.onItemClick(item);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(item);
                return false;
            }
        });
    }

}
