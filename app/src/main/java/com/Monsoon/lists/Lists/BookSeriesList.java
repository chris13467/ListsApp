package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.ListItems.NumberedItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class BookSeriesList extends baseList {
    BookSeriesItem currentItem;
    private static final long serialVersionUID = 1L;

    public BookSeriesList(){
        super();
        items_ = new ArrayList<>();
        listType_ = Constants.BOOK_SERIES;
    }
    public BookSeriesList(String name){
        super(name);
        items_ = new ArrayList<>();
        listType_ = Constants.BOOK_SERIES;
    }

    public BookList getAllUpcomingBooks(){
        BookList upcomingBooks = new BookList();
        for (BaseListItem item : items_){
            if (item instanceof BookSeriesItem){
                for (BaseListItem book : ((BookSeriesItem) item).getUpcomingBooks().getList()){
                    if (book instanceof BookItem){
                        StringBuilder sb = new StringBuilder(((BookItem) book).getSeriesTitle());
                        sb.append(" book ").append(((BookSeriesItem) item).getBookList().getList().size() + ((BookItem) book).getPosition() + 1);
                        if (!book.getContents().isBlank()) sb.append(": ").append(book.getContents());
                        BookItem newBook = new BookItem(sb.toString(), ((BookSeriesItem) item).getTags());
                        newBook.setReleaseDate(((BookItem) book).getReleaseDate());
                        upcomingBooks.addItem(newBook);
                    }
                }
            }
        }
        upcomingBooks.getList().sort(new Comparator<BaseListItem>() {
            @Override
            public int compare(BaseListItem baseListItem, BaseListItem t1) {
                if (baseListItem instanceof BookItem && t1 instanceof BookItem){
                    return ((BookItem) baseListItem).getReleaseDate().compareTo(((BookItem) t1).getReleaseDate());
                }
                return 0;
            }
        });
        return upcomingBooks;
    }

    public BookList getAllEstimatedUpcomingBooks(){
        BookList upcomingBooks = new BookList();
        for (BaseListItem item : items_){
            if (item instanceof BookSeriesItem){
                if (!((BookSeriesItem) item).hasUpcomingBook() && ((BookSeriesItem) item).getEstimatedNewReleaseDate() != null){
                    StringBuilder sb = new StringBuilder(item.getContents());
                    sb.append(" book ").append(((BookSeriesItem) item).getBookList().getList().size() + 1);
                    BookItem newRelease = new BookItem(sb.toString(), ((BookSeriesItem) item).getTags());
                    newRelease.setUpcoming(true);
                    newRelease.setReleaseDate(((BookSeriesItem) item).getEstimatedNewReleaseDate());
                    upcomingBooks.addItem(newRelease);
                }
            }
        }
        upcomingBooks.getList().sort(new Comparator<BaseListItem>() {
            @Override
            public int compare(BaseListItem baseListItem, BaseListItem t1) {
                if (baseListItem instanceof BookItem && t1 instanceof BookItem){
                    return ((BookItem) baseListItem).getReleaseDate().compareTo(((BookItem) t1).getReleaseDate());
                }
                return 0;
            }
        });
        return upcomingBooks;
    }

    public void setCurrentItem(BookSeriesItem cur){
        this.currentItem = cur;
    }

    public BookSeriesItem getCurrentItem(){
        return currentItem;
    }

}
