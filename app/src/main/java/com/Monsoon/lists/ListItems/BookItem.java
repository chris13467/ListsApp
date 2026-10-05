package com.Monsoon.lists.ListItems;

import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;

public class BookItem extends NumberedItem{
    private int position_;
    private Tags tags;

    public BookItem(){
        super();
        tags = new Tags();
    }

    public BookItem(String contents, Tags tags){
        super(contents);
        this.tags = new Tags();
        this.tags.addTags(tags.getList());
    }

    public BookItem(String contents, int position, Tags tags){
        super(contents);
        this.tags = new Tags();
        this.tags.addTags(tags.getList());
    }

    public Tags getTags() {
        return tags;
    }
}
