package com.malakezzat.foodplanner.view.mainfragments.listeners;

import com.malakezzat.foodplanner.model.data.Meal;

public interface OnHomeListener {
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
}
