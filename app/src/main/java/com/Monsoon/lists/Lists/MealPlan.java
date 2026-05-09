package com.Monsoon.lists.Lists;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.MealDay;
import com.Monsoon.lists.enums.Meal;

import java.time.LocalDate;
import java.util.ArrayList;


public class MealPlan extends BasicList{
    private int DaysBeforeRemoval_;

    public MealPlan(int DaysBeforeRemoval){
        super();
        listType_ = Constants.MEAL_PLAN;
        items_ = new ArrayList<>();
        DaysBeforeRemoval_ = DaysBeforeRemoval;
        name_ = Constants.MEAL_PLAN;
    }


    public int getDaysBeforeRemoval(){
        return DaysBeforeRemoval_;
    }

    public void setDaysBeforeRemoval(int days){
        DaysBeforeRemoval_ = days;
    }

    public int addDay(LocalDate date){
        MealDay mealDay = new MealDay(date);
        int i;
        for (i = 0; i < items_.size() && date.isAfter(((MealDay)items_.get(i)).getDate()); i++);
        items_.add(i, mealDay);
        return i;
    }

    public void removeDay(LocalDate date){
        int i = 0;
        while (i < items_.size() && ((MealDay)items_.get(i)).getDate().isEqual(date)) i++;
        if (i < items_.size()) items_.remove(i);
    }

    public void addMeal(String meal, LocalDate date, Meal type){
        int index = getMealDayByDate(date);
        if (index == Constants.NO_MEAL_DAY_FOUND){
            index = addDay(date);
        }
        ((MealDay)items_.get(index)).addMeal(meal, type);
    }

    public boolean removeMeal(LocalDate date, Meal type){
        int index = getMealDayByDate(date);
        if (index == Constants.NO_MEAL_DAY_FOUND) return false;
        ((MealDay)items_.get(index)).remove_meal(type);
        return true;
    }

    public void clean(){
        LocalDate threshold = LocalDate.now().minusDays(DaysBeforeRemoval_);
        while (!items_.isEmpty() && ((MealDay)items_.get(0)).getDate().isBefore(threshold)){
            items_.remove(0);
        }
    }

    private int getMealDayByDate(LocalDate date){
        int found_index = Constants.NO_MEAL_DAY_FOUND;
        int start_index = 0;
        int end_index = items_.size() - 1;

        if (!items_.isEmpty() && !date.isBefore(((MealDay)items_.get(start_index)).getDate()) && !date.isAfter(((MealDay)items_.get(end_index)).getDate())){
            boolean flag = false;
            while (!flag){
                int middle_index = (end_index - start_index) / 2;
                flag = middle_index == end_index || middle_index == start_index;
                if (date.isEqual(((MealDay)items_.get(middle_index)).getDate())){
                    flag = true;
                    found_index = middle_index;
                } else if (!date.isBefore(((MealDay)items_.get(middle_index)).getDate())){
                    end_index = middle_index;
                } else {
                    start_index = middle_index;
                }
            }
        }
        return found_index;
    }


}
