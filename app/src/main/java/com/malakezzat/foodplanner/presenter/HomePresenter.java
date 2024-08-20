package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.presenter.interview.IHomePresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements NetworkCallBack,IHomePresenter {

    private static final String TAG = "HomePresenter";
    IHomeView iHomeView;
    MealRemoteDataSource mealRemoteDataSource;
    List<Meal> mealList;
    MealLocalDataSource mealLocalDataSource;
    public HomePresenter(IHomeView iHomeView, MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource) {
        mealList = new ArrayList<>();
        this.iHomeView = iHomeView;
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;

    }

    public HomePresenter(IHomeView iHomeView, MealLocalDataSource mealLocalDataSource) {
        mealList = new ArrayList<>();
        this.iHomeView = iHomeView;
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public void getMeals() {
        mealRemoteDataSource.getMultipleRandomMeals(10,this);
    }

    @Override
    public void getMeal() {
        mealRemoteDataSource.getRandomMeal(this);
    }

    @Override
    public void getMealById(String id) {
        mealRemoteDataSource.searchById(Integer.parseInt(id),this,0);
    }

    @Override
    public void addToFav(Meal meal) {
        mealLocalDataSource.insertMeal(meal.toMealDB());
    }

    @Override
    public void removeFromFav(Meal meal) {
        mealLocalDataSource.deleteMeal(meal.toMealDB());
    }

    @Override
    public void addToWeekPlan(Meal meal) {
        mealLocalDataSource.insertWeekMeal(meal.toMealDBWeek());
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems) {
        Log.i(TAG, "onSuccessResult: " + listOfItems.get(0));
        if(listOfItems.get(0) instanceof Meal){
            if(listOfItems.size() > 1){
                iHomeView.getMeals((List<Meal>) listOfItems);
            } else {
                iHomeView.getMeal((List<Meal>) listOfItems);
            }
        }
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems, int saveMode) {
        iHomeView.getMeal((List<Meal>) listOfItems);
    }

    @Override
    public void onFailureResult(String msg) {
        iHomeView.getError(msg);
    }
}
