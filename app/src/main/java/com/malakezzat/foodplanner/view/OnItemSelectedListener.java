package com.malakezzat.foodplanner.view;

import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Meal;

public interface OnItemSelectedListener {
    void onItemSelected(Category category);
    void onItemSelected(Meal meal);
}
