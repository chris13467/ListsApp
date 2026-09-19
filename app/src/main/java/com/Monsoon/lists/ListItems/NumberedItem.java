package com.Monsoon.lists.ListItems;

public class NumberedItem extends BaseListItem{
    private int position_;

    public NumberedItem(){
        super();
        position_ = 0;
    }

    public NumberedItem(String contents){
        super(contents);
        position_ = 0;
    }

    public NumberedItem(String contents, int position){
        super(contents);
        position_ = position;
    }

    public int getPosition() {
        return position_;
    }

    public void setPosition(int position){
        position_ = position;
    }
}
