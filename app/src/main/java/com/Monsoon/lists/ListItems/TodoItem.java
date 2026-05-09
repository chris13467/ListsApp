package com.Monsoon.lists.ListItems;

import com.Monsoon.lists.enums.Frequency;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class TodoItem extends BaseListItem{
    private boolean checked_;
    private boolean repeatable_;
    private Frequency frequency_;
    private int numberOfDays_;
    private LocalDate setDate_;
    private LocalDate refDate_;
    private boolean endRepeat_;
    private LocalDate endDate_;

    //Constructors
    public TodoItem(){
        super();
        checked_ = false;
        repeatable_ = false;
        frequency_ = Frequency.DAILY;
        numberOfDays_ = 0;
        setDate_ = LocalDate.now();
        refDate_ = LocalDate.now();
        endRepeat_ = false;
        endDate_ = LocalDate.now();
    }
    public TodoItem(String contents){
        super(contents);
        checked_ = false;
        repeatable_ = false;
        frequency_ = Frequency.DAILY;
        numberOfDays_ = 0;
        setDate_ = LocalDate.now();
        refDate_ = LocalDate.now();
        endRepeat_ = false;
        endDate_ = LocalDate.now();
    }
    // getters
    public boolean isChecked(){
        return checked_;
    }

    public boolean isRepeatable(){
        return repeatable_;
    }

    public Frequency getFrequency(){
        return frequency_;
    }

    public int getNumberOfDays(){
        return numberOfDays_;
    }

    public LocalDate getSetDate(){
        return setDate_;
    }

    public LocalDate getRefDate(){
        return refDate_;
    }
    public boolean hasEnd(){
        return endRepeat_;
    }

    public LocalDate getEndDate(){
        return endDate_;
    }
    // setters
    public void setRefDate(LocalDate refDate){
        refDate_ = refDate;
    }

    public void toggleChecked(){
        checked_ = !checked_;
    }

    public void turnOffRepeat(){
        repeatable_ = false;
    }
    public void turnOnRepeat(Frequency frequency, int numberOfDays){
        repeatable_ = true;
        frequency_ = frequency;
        numberOfDays_ = numberOfDays;
        setDate_ = LocalDate.now();
        refDate_ = LocalDate.now();
    }
    public void turnOnRepeat(Frequency frequency, int numberOfDays, boolean endRepeat, LocalDate endDate){
        repeatable_ = true;
        frequency_ = frequency;
        numberOfDays_ = numberOfDays;
        setDate_ = LocalDate.now();
        refDate_ = LocalDate.now();
        endRepeat_ = endRepeat;
        endDate_ = endDate;
    }

}
