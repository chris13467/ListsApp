package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.Settings;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfLists implements Serializable {
    private ArrayList<baseList> userLists;
    private baseList CurrentList;
    private Settings settings;
//TODO: add Note type
    public ListOfLists(Settings settings){
        userLists = new ArrayList<>();
        CurrentList = new BasicList();
        this.settings = settings;
    }

    public ArrayList<baseList> getUserLists(){
        return userLists;
    }

    public baseList getCurrentList(){
        return CurrentList;
    }
    public void setCurrentList(baseList currentList){
        this.CurrentList = currentList;
    }

    public baseList CreateList(String listType){
        baseList newList;
        switch (listType) {
            case Constants.TODO:
                newList = new TodoList();
                break;
            case Constants.NUMBERED:
                newList = new NumberedList();
                break;
            default:
                newList = new BasicList();
        }
        userLists.add(newList);
        CurrentList = newList;
        return newList;
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
            default:
                newList = new BasicList(name);
        }
        userLists.add(newList);
        CurrentList = newList;
        return newList;
    }

    public void moveCurrentListToFront(){
        userLists.remove(CurrentList);
        userLists.add(0, CurrentList);
    }

    public boolean removeList(baseList userList){
        return userLists.remove(userList);
    }

    public boolean removeList(String name){
        boolean found = false;
        for (baseList l : userLists){
            if (l.getName().equals(name)){
                found = true;
                userLists.remove(l);
                break;
            }
        }
        return found;
    }

    public static ListOfLists getListFirstOnFirstLoad(Settings settings){
        ListOfLists lists = new ListOfLists(settings);
        return lists;
    }

}
