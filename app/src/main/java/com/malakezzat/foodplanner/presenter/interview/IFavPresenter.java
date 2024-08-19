package com.malakezzat.foodplanner.presenter.interview;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.local.fav.MealDB;

import java.util.List;

public interface IFavPresenter {
    LiveData<List<MealDB>> getStoredMeals();
    void addToWeekPlan(MealDB mealDB);
    void removeFromFav(MealDB mealDB);
}
