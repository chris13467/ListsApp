package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.ListItems.NumberedItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class BookSeriesList extends baseList {
    BookSeriesItem currentItem;
    private static final long serialVersionUID = 1L;

    public BookSeriesList(){
        super();
        items_ = new ArrayList<>();
        listType_ = Constants.BOOK_SERIES;
    }
    public BookSeriesList(String name){
        super(name);
        items_ = new ArrayList<>();
        listType_ = Constants.BOOK_SERIES;
    }

    public void setCurrentItem(BookSeriesItem cur){
        this.currentItem = cur;
    }

    public BookSeriesItem getCurrentItem(){
        return currentItem;
    }

}
