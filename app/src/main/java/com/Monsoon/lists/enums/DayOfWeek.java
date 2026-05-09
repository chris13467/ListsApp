package com.Monsoon.lists.enums;

import com.Monsoon.lists.Constants;

import java.io.Serializable;
import java.time.LocalDate;

public enum DayOfWeek implements Serializable {
    SUNDAY(Constants.SUNDAY_V, Constants.SUNDAY),
    MONDAY(Constants.MONDAY_V, Constants.MONDAY),
    TUESDAY(Constants.TUESDAY_V, Constants.TUESDAY),
    WEDNESDAY(Constants.WEDNESDAY_V, Constants.WEDNESDAY),
    THURSDAY(Constants.THURSDAY_V, Constants.THURSDAY),
    FRIDAY(Constants.FRIDAY_V, Constants.FRIDAY),
    SATURDAY(Constants.SATURDAY_V, Constants.SATURDAY);

    public final int index;
    public final String label;

    private DayOfWeek(int index, String label){
        this.label = label;
        this.index = index;
    }

    public static DayOfWeek getDayByIndex(int index){
        switch (index){
            case 0:
                return DayOfWeek.SUNDAY;
            case 1:
                return DayOfWeek.MONDAY;
            case 2:
                return DayOfWeek.TUESDAY;
            case 3:
                return DayOfWeek.WEDNESDAY;
            case 4:
                return DayOfWeek.THURSDAY;
            case 5:
                return DayOfWeek.FRIDAY;
            default:
                return DayOfWeek.SATURDAY;
        }
    }

    public static DayOfWeek getDayOfWeekFromDate(LocalDate date){
        return DayOfWeek.getDayByIndex(date.getDayOfWeek().getValue() % Constants.DAYS_PER_WEEK);
    }
}
