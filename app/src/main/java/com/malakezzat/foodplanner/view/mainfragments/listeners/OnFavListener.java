package com.malakezzat.foodplanner.view.mainfragments.listeners;

import com.malakezzat.foodplanner.model.local.fav.MealDB;

public interface OnFavListener {
    void onMealDetails(MealDB mealDB);
    void onClickRemoveMeal(MealDB mealDB);

}
