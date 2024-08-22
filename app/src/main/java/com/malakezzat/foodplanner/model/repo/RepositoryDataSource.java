package com.malakezzat.foodplanner.model.repo;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealDBWeek;

import java.util.List;

interface RepositoryDataSource {
    void getRandomMeal(NetworkCallBack networkCallBack);
    void getRandomMeals(NetworkCallBack networkCallBack);
    void getMultipleRandomMeals(int numberOfCalls, NetworkCallBack networkCallBack);
    void getCategories(NetworkCallBack networkCallBack);
    void searchByName(String name);
    void searchByFirstChar(String character);
    void searchById(int id,NetworkCallBack networkCallBack,int saveMode);
    void getCategoriesList(NetworkCallBack networkCallBack);
    void getCountriesList(NetworkCallBack networkCallBack);
    void getIngredientsList(NetworkCallBack networkCallBack);
    void filterByIngredient(String ingredient,NetworkCallBack networkCallBack);
    void filterByCategory(String category,NetworkCallBack networkCallBack);
    void filterByCountry(String country,NetworkCallBack networkCallBack);
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
