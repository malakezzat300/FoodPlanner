package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.presenter.interview.IFavPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IFavView;

import java.util.ArrayList;
import java.util.List;

public class FavPresenter implements IFavPresenter {
    private static final String TAG = "FavPresenter";
    IFavView iFavView;
    MealLocalDataSource mealLocalDataSource;
    List<Meal> mealList;
    public FavPresenter(IFavView iFavView, MealLocalDataSource mealLocalDataSource) {
        mealList = new ArrayList<>();
        this.iFavView = iFavView;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public LiveData<List<MealDB>> getStoredMeals() {
        Log.i(TAG, "getStoredMeals: ");
        return mealLocalDataSource.getAllStoredMeals();
    }

    @Override
    public void addToWeekPlan(MealDB mealDB) {
        mealLocalDataSource.insertWeekMeal(mealDB.toMeal().toMealDBWeek());
    }


    @Override
    public void removeFromFav(MealDB mealDB) {
        mealLocalDataSource.deleteMeal(mealDB);
    }


}
