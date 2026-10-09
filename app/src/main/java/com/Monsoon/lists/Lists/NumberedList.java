package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.NumberedItem;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class NumberedList extends baseList {
    private static final long serialVersionUID = 1L;
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

    @Override
    public void deleteSelected() {
        super.deleteSelected();
        reassignPositions();
    }

    //overrides
    @Override
    public void addItem(BaseListItem item) {
        ((NumberedItem)item).setPosition(items_.size());
        super.addItem(item);
    }

    public void addItem(BaseListItem item, int position){
        ((NumberedItem)item).setPosition(position);
        items_.add(position - 1, item);
        reassignPositions();
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

    public void Randomize(int numPasses){
        Random rand = new Random();
        for (int j = 0; j < numPasses; j++) {
            for (int i = 0; i < items_.size(); i++) {
                int newPos = rand.nextInt(items_.size());
                Collections.swap(items_, i, newPos);
            }
        }
        reassignPositions();
    }


}
