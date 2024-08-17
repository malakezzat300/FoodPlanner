package com.malakezzat.foodplanner.presenter.interview;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;

import java.util.List;

public interface IFavPresenter {
    LiveData<List<MealDB>> getStoredMeals();
    void removeFromFav(MealDB mealDB);
}
