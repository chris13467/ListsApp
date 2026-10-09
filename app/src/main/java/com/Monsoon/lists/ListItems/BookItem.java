package com.Monsoon.lists.ListItems;

import com.Monsoon.lists.util.Tags;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookItem extends NumberedItem{
    private Tags tags;
    private boolean hasRead;
    private LocalDate releaseDate;
    private boolean isUpcoming;
    private String SeriesTitle;
    private static final long serialVersionUID = 1L;
    public BookItem(String contents, Tags tags){
        super(contents);
        this.tags = new Tags();
        this.tags.addTags(tags.getList());
        this.hasRead = false;
        this.releaseDate = null;
    }

    public void setReleaseDate(LocalDate date){
        this.releaseDate = date;
    }

    public LocalDate getReleaseDate(){
        return releaseDate;
    }

    public void setRead(boolean b){
        hasRead = b;
    }
    public boolean isRead(){ return hasRead; }

    public Tags getTags() {
        return tags;
    }

    public void setUpcoming(boolean upcoming){
        this.isUpcoming = upcoming;
    }

    public boolean isUpcoming(){
        return isUpcoming;
    }

    public void setSeriesTitle(String title){
        this.SeriesTitle = title;
    }

    public String getSeriesTitle(){
        return SeriesTitle;
    }
}
