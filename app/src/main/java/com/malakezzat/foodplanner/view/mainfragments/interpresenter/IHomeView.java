package com.malakezzat.foodplanner.view.mainfragments.interpresenter;

import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.data.MealList;

import java.util.List;

public interface IHomeView {
    void getMeals(List<Meal> mealList);
    void getError(String msg);
}
