package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;

import java.util.ArrayList;

public class BasicList extends baseList{

    private static final long serialVersionUID = 1L;
    public BasicList(){
        super();
        items_ = new ArrayList<>();
        listType_ = Constants.BASIC;
    }

    public BasicList(String name){
        super(name);
        items_ = new ArrayList<>();
        listType_ = Constants.BASIC;
    }
}
