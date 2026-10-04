package com.Monsoon.lists.ListItems;

import java.util.ArrayList;

public class BookItem extends NumberedItem{
    private int position_;
    private ArrayList<String> tags;

    public BookItem(){
        super();
        tags = new ArrayList<>();
    }

    public BookItem(String contents){
        super(contents);
        tags = new ArrayList<>();
    }

    public BookItem(String contents, int position){
        super(contents);
        tags = new ArrayList<>();
    }

    public void addTags(ArrayList<String> newTags){
        for (String tag : newTags){
            if (!tags.contains(tag)){
                tags.add(tag);
            }
        }
    }

    public boolean containsTag(String tag){
        return tags.contains(tag);
    }

}
