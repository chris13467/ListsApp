package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Random;

public abstract class baseList implements Serializable {
    protected String listType_;
    protected String name_;
    protected AbstractList<BaseListItem> items_;
    protected BaseListItem currentItem_;
    private boolean isSelected;

    //Constructors
    public baseList() {
        name_ = "";
        listType_ = Constants.ABSTRACT;
        currentItem_ = new BaseListItem();
        isSelected = false;
    }
    public baseList(String name) {
        this.name_ = name;
        listType_ = Constants.ABSTRACT;
        currentItem_ = new BaseListItem();
        isSelected = false;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean b){
        isSelected = b;
    }

    //Getters

    public String getListType() {
        return listType_;
    }

    public String getName(){
        return name_;
    }

    public AbstractList<BaseListItem> getList(){
        return items_;
    }

    public BaseListItem getCurrentItem(){
        return currentItem_;
    }


    //Setters
    public void setName_(String name){
        this.name_ = name;
    }

    //Abstract
    public void addItem(BaseListItem item){
        items_.add(item);
    }
    public boolean removeItem(BaseListItem item){
        return items_.remove(item);
    }
    public boolean removeItem(String contents){
        int index = -1;
        boolean found = false;
        for (int i = 0; i < items_.size(); i++){
            if (items_.get(i).getContents().equals(contents)){
                index = i;
                found = true;
                break;
            }
        }
        items_.remove(index);
        return found;
    }
    public boolean removeItem(int position){
        if (position >= items_.size() || position < 0) return false;
        items_.remove(position);
        return false;
    }
    public BaseListItem getRandomItem(){
        Random rand = new Random();
        return items_.get(rand.nextInt(items_.size()));
    }

}
