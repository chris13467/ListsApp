package com.Monsoon.lists.enums;

import com.Monsoon.lists.Constants;

import java.io.Serializable;
import java.util.BitSet;

public enum Frequency implements Serializable {
    DAILY(Constants.DAILY_V, Constants.DAILY),
    EVERY_OTHER_DAY(Constants.EVERY_OTHER_DAY_V, Constants.EVERY_OTHER_DAY),
    WEEKLY(Constants.WEEKLY_V, Constants.WEEKLY),
    BI_WEEKLY(Constants.BI_WEEKLY_V, Constants.BI_WEEKLY),
    MONTHLY(Constants.MONTHLY_V, Constants.MONTHLY),
    BI_MONTHLY(Constants.BI_MONTHLY_V, Constants.BI_MONTHLY),
    ANNUALLY(Constants.ANNUALLY_V, Constants.ANNUALLY),
    CUSTOM_DAYS(Constants.CUSTOM_DAYS_V, Constants.CUSTOM_DAYS),
    CUSTOM_WEEKS(Constants.CUSTOM_WEEKS_V, Constants.CUSTOM_WEEKS),
    CUSTOM_MONTHS(Constants.CUSTOM_MONTHS_V, Constants.CUSTOM_MONTHS);

    public final int days;
    public final String label;

    private Frequency(int days, String label){
        this.days = days;
        this.label = label;
    }

    public static Frequency getFrequencyByIndex(int index){
        switch (index){
            case 0:
                return DAILY;
            case 1:
                return EVERY_OTHER_DAY;
            case 2:
                return WEEKLY;
            case 3:
                return BI_WEEKLY;
            case 4:
                return MONTHLY;
            case 5:
                return BI_MONTHLY;
            case 6:
                return ANNUALLY;
            default:
                return CUSTOM_DAYS;
        }
    }

    public static String[] getLabels(){
        return new String[]{
                DAILY.label,
                EVERY_OTHER_DAY.label,
                WEEKLY.label,
                BI_WEEKLY.label,
                MONTHLY.label,
                BI_MONTHLY.label,
                ANNUALLY.label,
                CUSTOM_DAYS.label
        };
    }
}
