package com.malakezzat.foodplanner.view.mainfragments.listeners;

import com.malakezzat.foodplanner.model.data.Meal;

public interface OnMealClickListener {
    void showMealDetails(Meal meal);
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
    void addToFav(String Id,int saveMode);
    void removeFromFav(String Id,int saveMode);
    void addToWeekPlan(Meal meal);
    void addToWeekPlan(String id,int saveMode);
    void getMealById(String Id);
}
