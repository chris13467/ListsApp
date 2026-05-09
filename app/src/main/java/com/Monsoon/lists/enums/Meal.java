package com.Monsoon.lists.enums;

import com.Monsoon.lists.Constants;

import java.io.Serializable;

public enum Meal implements Serializable {
    BREAKFAST(0, Constants.BREAKFAST),
    LUNCH(1, Constants.LUNCH),
    DINNER(2, Constants.DINNER);

    public final int index;
    public final String label;

    private Meal(int index, String label){
        this.index = index;
        this.label = label;
    }

    public static String[] getLabels(){
        return new String[]{
                Meal.BREAKFAST.label,
                Meal.LUNCH.label,
                Meal.DINNER.label
        };
    }

    public static Meal getMealByLabel(String label){
        switch (label){
            case Constants.DINNER:
                return DINNER;
            case Constants.LUNCH:
                return LUNCH;
            default:
                return BREAKFAST;
        }
    }
}
