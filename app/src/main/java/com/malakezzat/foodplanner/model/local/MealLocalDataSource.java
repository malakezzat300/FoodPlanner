package com.malakezzat.foodplanner.model.local;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface MealLocalDataSource {
 void insertMeal(MealDB mealDB);
 void deleteMeal(MealDB mealDB);
 LiveData<List<MealDB>> getAllStoredMeals();
 MealDB getFavMealDB(int id);
 void backupFavMeals();
 void restoreFavMeals();
 void insertWeekMeal(MealDBWeek mealWeekDB);
 void deleteWeekMeal(MealDBWeek mealWeekDB);
 LiveData<List<MealDBWeek>> getWeekPlanMeals();
 MealDBWeek getMealDBWeek(int id);
 void backupWeekPlan();
 void restoreWeekPlan();
}
