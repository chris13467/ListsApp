package com.Monsoon.lists.listeners;

import com.Monsoon.lists.ListItems.BaseListItem;

public interface onListItemClickedListener<I> {
    void onItemClick(I item);
    void onItemLongClick(I item);
}
