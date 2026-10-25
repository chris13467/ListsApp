package com.Monsoon.lists.ListItems;

public class NumberedItem extends BaseListItem{
    private static final long serialVersionUID = 1L;
    private int position_;
    protected boolean locked;

    public NumberedItem(){
        super();
        position_ = 0;
        this.locked = false;
    }

    public NumberedItem(String contents){
        super(contents);
        position_ = 0;
        this.locked = false;
    }

    public NumberedItem(String contents, int position){
        super(contents);
        position_ = position;
        this.locked = false;
    }

    public int getPosition() {
        return position_;
    }

    public void setPosition(int position){
        position_ = position;
    }

    public boolean isLocked(){
        return this.locked;
    }
    public void setLock(boolean locked){
        this.locked = locked;
    }
}
