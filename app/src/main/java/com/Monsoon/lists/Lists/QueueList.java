package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.QueueItem;

import java.util.LinkedList;

public class QueueList extends baseList {
    public QueueList(){
        super();
        items_ = new LinkedList<>();
        listType_ = Constants.QUEUE;
    }
    public QueueList(String name){
        super(name);
        items_ = new LinkedList<>();
        listType_ = Constants.QUEUE;
    }

    //getters
    public QueueItem getNextItem(){
        QueueItem temp = (QueueItem)items_.get(0);
        items_.remove(0);
        return temp;
    }
    //setters

    //utility
    public void reAddCurrentItem(){
        addItem(currentItem_);
    }

    public void reassignPositions(){
        for (int i = 0; i < items_.size(); i++){
            ((QueueItem)items_.get(i)).setPosition(i);
        }
    }
    //overrides
    @Override
    public void addItem(BaseListItem item) {
        ((QueueItem)item).setPosition(items_.size());
        super.addItem(item);
    }

    public void addItem(BaseListItem item, int position){
        ((QueueItem)item).setPosition(items_.size());
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
}
