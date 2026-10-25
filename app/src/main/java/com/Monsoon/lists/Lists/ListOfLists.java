package com.Monsoon.lists.Lists;

import android.util.Log;
import android.widget.Toast;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;
import com.Monsoon.lists.Settings;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

public class ListOfLists extends baseList {
    private static final long serialVersionUID = 1L;
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
            case Constants.BOOK_SERIES:
                newList = new BookSeriesList(name);
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
            int curIndex = findList(CurrentList);

            for (int i = curIndex; i > 0; i--){
               Collections.swap(items_, i, i-1);
            }
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

    public ListOfLists getCompatibleLists(baseList callingList, BaseListItem item) {
        ListOfLists compatibleLists = new ListOfLists(this.settings);
        Log.d("Debug", "getCompatibleLists: Calling list ID: " + callingList.getId());
        for (String s : getCompatibleListTypes(item)){
            for (BaseListItem b : items_){
                baseList list = ((ListOfListsWrapper)b).getList();
                Log.d("Debug", "getCompatibleLists: list ID->" + list.getId() + " List Type->" + list.getListType());
                if (list.getListType().equals(s) && list.getId() != callingList.getId()) {
                    Log.d("Debug", "getCompatibleLists: Added");
                    compatibleLists.addItem(b);
                }
            }
        }
        return compatibleLists;
    }

    private ArrayList<String> getCompatibleListTypes(BaseListItem item){
        ArrayList<String> compatibleTypes = new ArrayList<>();

        if (item instanceof BookItem){
            compatibleTypes.add(Constants.BOOK);
            compatibleTypes.add(Constants.BOOK_SERIES);
        }
        if (item instanceof BookSeriesItem){
            compatibleTypes.add(Constants.BOOK_SERIES);
        }

        return compatibleTypes;
    }

    public static ListOfLists getListFirstOnFirstLoad(Settings settings){
        ListOfLists lists = new ListOfLists(settings);
        return lists;
    }

}
