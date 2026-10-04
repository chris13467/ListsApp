package com.Monsoon.lists.ListItems;

import java.io.Serializable;

public class BaseListItem implements Serializable {
    protected String contents_;
    protected boolean selected_;
    private static final long serialVersionUID = 1L;

    public BaseListItem(){
        contents_ = "";
        selected_ = false;
    }

    public BaseListItem(String contents){
        contents_ = contents;
        selected_ = false;
    }

    public String getContents() {
        return contents_;
    }

    public void setContents(String contents) {
        this.contents_ = contents;
    }

    public void setSelected(boolean b){
        this.selected_ = b;
    }

    public boolean isSelected(){
        return this.selected_;
    }
}
