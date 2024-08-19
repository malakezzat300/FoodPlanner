package com.malakezzat.foodplanner.view.mainfragments.interpresenter;

import com.malakezzat.foodplanner.model.data.Meal;

import java.util.List;

public interface ISearchView {
    void getCountryList(List<Meal> countryList);
    void getIngredientList(List<Meal> ingredientList);
    void getCategoryList(List<Meal> categoryList);
    void getMealList(List<Meal> mealList);
    void getError(String msg);
    void getMealById(Meal meal,int modeSave);
}
