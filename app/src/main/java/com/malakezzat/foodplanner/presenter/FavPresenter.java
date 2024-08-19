package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.fav.MealDB;
import com.malakezzat.foodplanner.model.local.fav.ProductLocalDataSource;
import com.malakezzat.foodplanner.model.local.week.ProductLocalDataSourceWeek;
import com.malakezzat.foodplanner.presenter.interview.IFavPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IFavView;

import java.util.ArrayList;
import java.util.List;

public class FavPresenter implements IFavPresenter {
    private static final String TAG = "FavPresenter";
    IFavView iFavView;
    ProductLocalDataSource productLocalDataSource;
    ProductLocalDataSourceWeek productLocalDataSourceWeek;
    List<Meal> mealList;
    public FavPresenter(IFavView iFavView, ProductLocalDataSource productLocalDataSource) {
        mealList = new ArrayList<>();
        this.iFavView = iFavView;
        this.productLocalDataSource = productLocalDataSource;
    }

    public FavPresenter(IFavView iFavView, ProductLocalDataSourceWeek productLocalDataSourceWeek) {
        mealList = new ArrayList<>();
        this.iFavView = iFavView;
        this.productLocalDataSourceWeek = productLocalDataSourceWeek;
    }

    @Override
    public LiveData<List<MealDB>> getStoredMeals() {
        Log.i(TAG, "getStoredMeals: ");
        return productLocalDataSource.getAllStoredMeals();
    }

    @Override
    public void addToWeekPlan(MealDB mealDB) {
        productLocalDataSourceWeek.insertWeekMeal(mealDB.toMeal().toMealDBWeek());
    }


    @Override
    public void removeFromFav(MealDB mealDB) {
        productLocalDataSource.deleteMeal(mealDB);
    }


}
