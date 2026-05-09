package com.Monsoon.lists.ListItems;

public class QueueItem extends BaseListItem{
    private int position_;

    public QueueItem(){
        super();
        position_ = 0;
    }

    public  QueueItem(String contents){
        super(contents);
        position_ = 0;
    }

    public QueueItem(String contents, int position){
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
