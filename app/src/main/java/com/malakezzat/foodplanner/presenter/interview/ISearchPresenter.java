package com.malakezzat.foodplanner.presenter.interview;

import com.malakezzat.foodplanner.model.data.Meal;

import java.util.List;

public interface ISearchPresenter {
    void getCountryList();
    void getIngredientList();
    void getCategoryList();
    void searchByCountry(String country);
    void searchByIngredient(String ingredient);
    void searchByCategory(String category);
    void addToFav(Meal meal);
    void removeFromFav(Meal meal);
    void addToWeekPlan(Meal meal);
    void getMealById(String id);
    void getMealById(String id,int saveMode);
}
