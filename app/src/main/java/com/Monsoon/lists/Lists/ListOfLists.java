package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;
import com.Monsoon.lists.Settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ListOfLists extends baseList {
    private baseList CurrentList;
    private Settings settings;
    public ListOfLists(Settings settings){
        items_ = new ArrayList<>();
        listType_ = Constants.LIST_OF_LISTS;
        CurrentList = new BasicList();
        this.settings = settings;
    }

    public baseList getCurrentList(){
        return CurrentList;
    }
    public void setCurrentList(baseList currentList){
        this.CurrentList = currentList;
    }


    public baseList CreateList(String listType, String name){
        baseList newList;
        switch (listType) {
            case Constants.TODO:
                newList = new TodoList(name);
                break;
            case Constants.NUMBERED:
                newList = new NumberedList(name);
                break;
            case Constants.BOOK:
                newList = new BookList(name);
                break;
            default:
                newList = new BasicList(name);
        }
        ListOfListsWrapper wrapper = new ListOfListsWrapper(newList);
        items_.add(wrapper);
        CurrentList = newList;
        return newList;
    }

    public void moveCurrentListToFront(){
        if (items_.size() > 1){
            Collections.swap(items_, 0, findList(CurrentList));
        }

    }
    public int findList(baseList item){
        int index = -1;
        for (int i = 0; i < items_.size(); i++) {
            if (((ListOfListsWrapper)items_.get(i)).getList().getId() == item.getId()) {
                index = i;
                break;
            }
        }
        return index;
    }
    public static ListOfLists getListFirstOnFirstLoad(Settings settings){
        ListOfLists lists = new ListOfLists(settings);
        return lists;
    }

}
