package com.malakezzat.foodplanner.view.mainfragments.interpresenter;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.local.MealDB;

import java.util.List;

public interface IFavView {
    void getStoredMeals(List<MealDB> meals);
}
