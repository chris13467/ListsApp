package com.Monsoon.lists.Adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public abstract class ListsViewHolder<I extends BaseListItem> extends RecyclerView.ViewHolder {
    protected TextView textContent;
    protected FrameLayout overlay;

    protected CheckBox selectButton;
    public ListsViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bind(final I item, final onListItemClickedListener<I> listener);


    protected void setUniversalViewBinding(I item, onListItemClickedListener<I> listener, boolean isSelectMode){
        if (isSelectMode) overlay.setVisibility(View.VISIBLE);
        else item.setSelected(false);
        selectButton.setChecked(item.isSelected());

        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton.setChecked(!selectButton.isChecked());
            }
        });
        selectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setSelected(selectButton.isChecked());
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(item);
                item.setSelected(true);
                return false;
            }
        });
    }

}
