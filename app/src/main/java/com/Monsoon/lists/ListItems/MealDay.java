package com.Monsoon.lists.ListItems;

import com.Monsoon.lists.Lists.MealPlan;
import com.Monsoon.lists.enums.DayOfWeek;
import com.Monsoon.lists.enums.Meal;

import java.time.LocalDate;

public class MealDay extends BaseListItem {
    private String[] meals_names;
    private LocalDate date;
    private DayOfWeek dayOfWeek;


    public MealDay(LocalDate date){
        super();
        meals_names = new String[3];
        for (int i = 0; i < 3; i++){
            meals_names[i] = "";
        }
        this.date = date;
        dayOfWeek = DayOfWeek.getDayOfWeekFromDate(date);
    }

    public String[] getMeals() {
        return meals_names;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalDate getDate() {
        return date;
    }

    public void addMeal(String meal_name, Meal type){
        meals_names[type.index] = meal_name;
    }

    public void remove_meal(Meal type){
        meals_names[type.index] = "";
    }



}
