package com.Monsoon.lists;

import java.io.Serializable;
import java.util.Set;

public class Settings implements Serializable {
    //TODO: theme Settings
    //TODO: strikethrough on todochecked
    private int numberOfDaysBeforeMealRemoval_;


    public Settings(){
        numberOfDaysBeforeMealRemoval_ = 7;
    }

    public int getNumberOfDaysBeforeMealRemoval(){
        return numberOfDaysBeforeMealRemoval_;
    }

    public void setNumberOfDaysBeforeMealRemoval(int numDays){
        numberOfDaysBeforeMealRemoval_ = numDays;
    }

}
