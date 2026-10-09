package com.Monsoon.lists.ListItems;

import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;
import java.util.Collections;

public class BookSeriesItem extends BaseListItem {
    private ArrayList<BookItem> booksInSeries;
    private String author;
    private Tags tags;
    private static final long serialVersionUID = 1L;


    public BookSeriesItem(){
        super();
        this.booksInSeries = new ArrayList<>();
        this.tags = new Tags();

    }

    public BookSeriesItem(String seriesName){
        super(seriesName);
        this.booksInSeries = new ArrayList<>();
        this.tags = new Tags();
    }

    public void addBook(BookItem book){
        if (book.isUpcoming()){
            booksInSeries.add(book);
        } else {
            int i;
            for (i = booksInSeries.size()-1; i >= 0 && booksInSeries.get(i).isUpcoming(); i--);
            booksInSeries.add(i + 1, book);
        }
    }

    public boolean removeBook(BookItem book){
        return booksInSeries.remove(book);
    }

    public ArrayList<BookItem> getBooksInSeries(){
        return booksInSeries;
    }

    public BookList getBookList(){
        return new BookList(booksInSeries);
    }

    public void setTags(Tags tags){
        tags.addTags(tags.getList());
    }
    public Tags getTags() {
        return tags;
    }


    public boolean hasUpcomingBook(){
        return booksInSeries.get(booksInSeries.size() - 1).isUpcoming();
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return author;
    }

}
