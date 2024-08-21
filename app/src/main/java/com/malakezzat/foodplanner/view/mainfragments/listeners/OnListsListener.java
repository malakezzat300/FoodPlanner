package com.malakezzat.foodplanner.view.mainfragments.listeners;

import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Meal;

public interface OnListsListener {
    void onListItemClicked(Category category);
    void onListItemClicked(Meal meal);
}
