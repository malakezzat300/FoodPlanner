package com.malakezzat.foodplanner.presenter.interview;

import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.data.MealList;

import java.util.List;

public interface IHomePresenter {
    void getMeals();
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
}
