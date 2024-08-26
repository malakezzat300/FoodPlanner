package com.malakezzat.foodplanner.model.repository;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealDBWeek;

import java.util.List;

public interface MealRepository {
    //Local
    void insertMeal(MealDB mealDB);
    void deleteMeal(MealDB mealDB);
    LiveData<List<MealDB>> getAllStoredMeals();
    List<MealDB> getAllStoredMealsCheck();
    void backupFavMeals();
    void restoreFavMeals();
    void insertWeekMeal(MealDBWeek mealWeekDB);
    void deleteWeekMeal(MealDBWeek mealWeekDB);
    LiveData<List<MealDBWeek>> getWeekPlanMeals();
    void backupWeekPlan();
    void restoreWeekPlan();

    //Remote
    void getRandomMeal(NetworkCallBack networkCallBack);
    void getRandomMeals(NetworkCallBack networkCallBack);
    void getMultipleRandomMeals(int numberOfCalls, NetworkCallBack networkCallBack);
    void getCategories(NetworkCallBack networkCallBack);
    void searchById(int id,NetworkCallBack networkCallBack,int saveMode);
    void getCategoriesList(NetworkCallBack networkCallBack);
    void getCountriesList(NetworkCallBack networkCallBack);
    void getIngredientsList(NetworkCallBack networkCallBack);
    void filterByIngredient(String ingredient,NetworkCallBack networkCallBack);
    void filterByCategory(String category,NetworkCallBack networkCallBack);
    void filterByCountry(String country,NetworkCallBack networkCallBack);
}
