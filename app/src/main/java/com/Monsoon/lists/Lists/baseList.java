package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public abstract class baseList implements Serializable {
    protected long id;
    protected String listType_;
    protected String name_;
    protected AbstractList<BaseListItem> items_;
    protected BaseListItem currentItem_;
    protected boolean firstLoad;
    private boolean isSelected;
    private static final long serialVersionUID = 1L;

    //Constructors
    public baseList() {
        name_ = "";
        listType_ = Constants.ABSTRACT;
        currentItem_ = null;
        isSelected = false;
        this.firstLoad = true;
    }
    public baseList(String name) {
        this.name_ = name;
        listType_ = Constants.ABSTRACT;
        currentItem_ = null;
        isSelected = false;
        this.firstLoad = true;
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
    public long getId(){ return id; }
    public void setId(long id){
        this.id = id;
    }

    public AbstractList<BaseListItem> getList(){
        return items_;
    }

    public BaseListItem getSelected(){
        for (BaseListItem i : items_){
            if (i.isSelected()) {
                return i;
            }
        }
        return new BaseListItem();
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
        if (item == currentItem_) {
            for (BaseListItem i : items_){
                if (i != item) {
                    currentItem_ = i;
                    break;
                }
            }
        }
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

    public BaseListItem getNextItem(){
        return items_.get(0);
    }

    public int getNumSelected(){
        int count = 0;
        for (BaseListItem i : items_){
            if (i.isSelected()) count++;
        }
        return count;
    }

    public void deleteSelected(){
        for (int i = items_.size() - 1; i >= 0; i--){
            if (items_.get(i).isSelected()) removeItem(i);
        }
    }

    public void clearSelections(){
        for (BaseListItem i : items_){
            i.setSelected(false);
        }
    }

    public boolean isFirstLoad(){
        return this.firstLoad;
    }

    public void onFirstLoad(){
        this.firstLoad = false;
    }

}
