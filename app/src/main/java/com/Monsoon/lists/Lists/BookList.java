package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.NumberedItem;
import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class BookList extends NumberedList {
    public BookList(){
        super();
        listType_ = Constants.BOOK;
    }
    public BookList(String name){
        super(name);
        listType_ = Constants.BOOK;
    }

    public void taggedItemsToTop(String tag){
        ArrayList<Integer> tag_locations = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < items_.size(); i++){
            BookItem currentItem = (BookItem) items_.get(i);
            if (currentItem.getTags().containsTag(tag)) {
                Collections.swap(items_, i, j);
                j++;
            }
        }
    }

    public BookItem getRandomItem(String tag){
        ArrayList<Integer> tag_locations = new ArrayList<>();
        for (int i = 0; i < items_.size(); i++){
            BookItem currentItem = (BookItem) items_.get(i);
            if (currentItem.getTags().containsTag(tag)) tag_locations.add(i);
        }
        if (tag_locations.isEmpty()){
            return null;
        } else {
            Random rand = new Random();
            int index = tag_locations.get(rand.nextInt(tag_locations.size()));
            return (BookItem) items_.get(index);
        }
    }


}
