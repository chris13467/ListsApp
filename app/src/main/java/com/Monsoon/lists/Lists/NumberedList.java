package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.NumberedItem;

import java.util.LinkedList;

public class NumberedList extends baseList {
    public NumberedList(){
        super();
        items_ = new LinkedList<>();
        listType_ = Constants.NUMBERED;
    }
    public NumberedList(String name){
        super(name);
        items_ = new LinkedList<>();
        listType_ = Constants.NUMBERED;
    }



    public void reassignPositions(){
        for (int i = 0; i < items_.size(); i++){
            ((NumberedItem)items_.get(i)).setPosition(i);
        }
    }
    //overrides
    @Override
    public void addItem(BaseListItem item) {
        ((NumberedItem)item).setPosition(items_.size());
        super.addItem(item);
    }

    public void addItem(BaseListItem item, int position){
        ((NumberedItem)item).setPosition(items_.size());
        items_.add(position, item);
    }

    @Override
    public boolean removeItem(BaseListItem item) {
        boolean found = super.removeItem(item);
        reassignPositions();
        return found;
    }

    @Override
    public boolean removeItem(String contents) {
        boolean found = super.removeItem(contents);
        reassignPositions();
        return found;
    }

    @Override
    public boolean removeItem(int position) {
        boolean found = super.removeItem(position);
        reassignPositions();
        return false;
    }

    @Override
    public BaseListItem getRandomItem() {
        BaseListItem rand = super.getRandomItem();
        removeItem(rand);
        return rand;
    }
    @Override
    public BaseListItem getNextItem() {
        NumberedItem temp = (NumberedItem)items_.get(0);
        items_.remove(0);
        return temp;
    }

    public void Make_selected_current(){
        NumberedItem selected = null;
        if (getNumSelected() == 1){
            for (int i = 0; i < items_.size(); i++){
                selected = (NumberedItem) items_.get(i);
                if (selected.isSelected()) break;
            }
            setCurrentItem(selected);
        }
    }

    @Override
    public void setCurrentItem(BaseListItem item) {
        currentItem_ = item;
        removeItem(item);
    }

    public void reAddCurrentItem(){
        addItem(currentItem_, ((NumberedItem)currentItem_).getPosition());
        currentItem_ = null;
        reassignPositions();
    }

    public void removeCurrentItem(){
        currentItem_ = null;
    }



}
