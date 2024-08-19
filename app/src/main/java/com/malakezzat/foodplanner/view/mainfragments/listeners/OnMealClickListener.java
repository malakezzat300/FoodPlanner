package com.malakezzat.foodplanner.view.mainfragments.listeners;

import com.malakezzat.foodplanner.model.data.Meal;

public interface OnMealClickListener {
    void showMealDetails(Meal meal);
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
    void getMealById(String Id);
}
