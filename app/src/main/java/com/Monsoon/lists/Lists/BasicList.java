package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;

import java.util.ArrayList;

public class BasicList extends baseList{
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
