package com.Monsoon.lists.ListItems;

import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.util.Tags;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class BookSeriesItem extends BaseListItem {
    private BookList booksInSeries;
    private BookList upcomingBooks;
    private LocalDate estimatedNewReleaseDate;
    private String author;
    private Tags tags;
    private static final long serialVersionUID = 1L;


    public BookSeriesItem(){
        super();
        this.booksInSeries = new BookList();
        this.upcomingBooks = new BookList();
        this.author = "";
        this.tags = new Tags();
        estimatedNewReleaseDate = null;

    }

    public BookSeriesItem(String seriesName){
        super(seriesName);
        this.booksInSeries = new BookList();
        this.upcomingBooks = new BookList();
        this.author = "";
        this.tags = new Tags();
        estimatedNewReleaseDate = null;
    }

    public void addBook(BookItem book){
        if (book.isUpcoming()){
            upcomingBooks.addItem(book);
        } else {
            booksInSeries.addItem(book);
        }
    }

    public BookList getUpcomingBooks(){
        return upcomingBooks;
    }

    public boolean removeBookFromBookSeries(BookItem book){
        return booksInSeries.removeItem(book);
    }

    public boolean removeBookFromUpcomingBooks(BookItem book){
        return upcomingBooks.removeItem(book);
    }

    public void moveBookFromUpcomingToSeries(BookItem book){
        upcomingBooks.removeItem(book);
        booksInSeries.addItem(book);
    }

    public BookList getBookList(){
        return booksInSeries;
    }

    public void setTags(Tags tags){
        this.tags.addTags(tags.getList());
    }
    public Tags getTags() {
        return tags;
    }

    public int getNumberOfReleasedBooks(){
        int count = 0;
        for (BaseListItem item : booksInSeries.getList()){
            if (!((BookItem) item).isUpcoming()) count++;
        }
        return count;
    }

    public int getNumberOfReadBooks(){
        int count = 0;
        for (BaseListItem item : booksInSeries.getList()){
            if (((BookItem) item).isRead()) count++;
        }
        return count;
    }

    public BookItem getNextUpcomingBook(){
        return (BookItem) upcomingBooks.getList().get(0);
    }

    public boolean hasUpcomingBook(){
        return !upcomingBooks.getList().isEmpty();
    }

    public void clearSelections(){
        booksInSeries.clearSelections();
        upcomingBooks.clearSelections();
    }

    public void deleteSelections(){
        booksInSeries.deleteSelected();
        upcomingBooks.deleteSelected();
    }

    public BookItem getSelected(){
        if (booksInSeries.getNumSelected() == 1 && upcomingBooks.getNumSelected() == 0) return (BookItem) booksInSeries.getSelected();
        else if (upcomingBooks.getNumSelected() == 1 && booksInSeries.getNumSelected() == 0) return (BookItem) upcomingBooks.getSelected();
        else return null;
    }

    public int getNumSelected(){
        return booksInSeries.getNumSelected() + upcomingBooks.getNumSelected();
    }

    public void estimateNewReleaseDate(){
        long totalDaysBetween = 0;
        int count = 0;
        LocalDate prev = null;
        for (BaseListItem item : booksInSeries.getList()){
            BookItem book = (BookItem) item;
            if(book.getReleaseDate() != null){
                if (prev != null) {
                    totalDaysBetween += ChronoUnit.DAYS.between(prev, book.getReleaseDate());
                    count++;
                }
                prev = book.getReleaseDate();
            }
        }
        if (count > 0){
            long averageIntervalDays = Math.round((double) totalDaysBetween / count);
            estimatedNewReleaseDate = prev.plusDays(averageIntervalDays);
        } else estimatedNewReleaseDate = null;
    }

    public LocalDate getEstimatedNewReleaseDate(){
        return estimatedNewReleaseDate;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return author;
    }

}
