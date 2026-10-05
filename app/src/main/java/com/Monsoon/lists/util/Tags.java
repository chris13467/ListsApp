package com.Monsoon.lists.util;

import java.io.Serializable;
import java.util.ArrayList;

public class Tags implements Serializable {
    private ArrayList<String> tags;
    private ArrayList<Boolean> selectedTags;

    public Tags(){
        tags = new ArrayList<>();
        selectedTags = new ArrayList<>();
    }

    public void addTags(ArrayList<String> newTags){
        for (String tag : newTags){
            if (!tags.contains(tag)){
                tags.add(tag);
                selectedTags.add(false);
            }
        }
    }

    public void addTag(String tag){
        if (!tags.contains(tag)){
            tags.add(tag);
            selectedTags.add(true);
        }
    }

    public boolean removeTag(String tag){
        int index = tags.indexOf(tag);
        return tags.remove(tag) && selectedTags.remove(index);
    }

    public boolean containsTag(String tag){
        return tags.contains(tag);
    }

    public void clearSelections(){
        for (int i = 0; i < selectedTags.size(); i++){
            selectedTags.set(i, false);
        }
    }

    public ArrayList<String> getList() {return tags; }
    public ArrayList<Boolean> getSelectedTags() { return selectedTags; }
}
