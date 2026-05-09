package com.Monsoon.lists.Lists;

import android.util.Log;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.TodoItem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TodoList extends baseList{
    private ArrayList<TodoItem> repeatableList_;
    private boolean removeOnChecked_;

    //constructors
    public TodoList(){
        super();
        items_ = new ArrayList<>();
        repeatableList_ = new ArrayList<>();
        removeOnChecked_ = false;
        listType_ = Constants.TODO;
    }

    public TodoList(String name){
        super(name);
        items_ = new ArrayList<>();
        repeatableList_ = new ArrayList<>();
        removeOnChecked_ = false;
        listType_ = Constants.TODO;
    }

    public TodoList(String name, boolean removeOnChecked){
        super(name);
        items_ = new ArrayList<>();
        repeatableList_ = new ArrayList<>();
        removeOnChecked_ = removeOnChecked;
        listType_ = Constants.TODO;
    }

    //getters
    public boolean isRemoveOnChecked(){
        return removeOnChecked_;
    }

    public ArrayList<TodoItem> getRepeatableItems(){
        return repeatableList_;
    }

    //setters
    public void setRemoveOnChecked_(boolean removeOnChecked){
        removeOnChecked_ = removeOnChecked;
    }

    public void toggleCheckedOnItem(TodoItem item){
        item.toggleChecked();
        if (removeOnChecked_){
            removeItem(item);
        }
    }
    //utility functions

    public void checkRepeatable(){
        for (int i = 0; i < repeatableList_.size(); i++){
            int numDays = repeatableList_.get(i).getNumberOfDays();
            while (ChronoUnit.DAYS.between(repeatableList_.get(i).getRefDate(), LocalDate.now()) >= numDays){
                items_.add(repeatableList_.get(i));
                repeatableList_.get(i).setRefDate(repeatableList_.get(i).getRefDate().plusDays(numDays));
            }
        }
    }

    //overrides
    @Override
    public void addItem(BaseListItem item) {
        super.addItem(item);
        if (((TodoItem)item).isRepeatable()){
            repeatableList_.add((TodoItem)item);
        }
    }


}
