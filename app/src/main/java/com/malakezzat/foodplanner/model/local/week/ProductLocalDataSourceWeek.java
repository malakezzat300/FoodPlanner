package com.malakezzat.foodplanner.model.local.week;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ProductLocalDataSourceWeek {
 void insertWeekMeal(MealDBWeek mealWeekDB);
 void deleteWeekMeal(MealDBWeek mealWeekDB);
 LiveData<List<MealDBWeek>> getWeekPlanMeals();
 MealDBWeek getMealDB(int id);
}
