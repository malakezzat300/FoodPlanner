package com.malakezzat.foodplanner.model.local.fav;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ProductLocalDataSource {
 void insertMeal(MealDB mealDB);
 void deleteMeal(MealDB mealDB);
 LiveData<List<MealDB>> getAllStoredMeals();
 MealDB getMealDB(int id);
}
