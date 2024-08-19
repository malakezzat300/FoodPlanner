package com.malakezzat.foodplanner.view.mainfragments.interpresenter;

import com.malakezzat.foodplanner.model.local.fav.MealDB;

import java.util.List;

public interface IFavView {
    void getStoredMeals(List<MealDB> meals);
}
