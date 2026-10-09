package com.Monsoon.lists.ListItems;

import com.Monsoon.lists.Lists.baseList;

import java.util.AbstractList;

public class ListOfListsWrapper extends BaseListItem {
    private static final long serialVersionUID = 1L;
    private baseList list;

    public ListOfListsWrapper(baseList list) {
        super();
        this.list = list;
    }

    public baseList getList(){ return list; }
}
