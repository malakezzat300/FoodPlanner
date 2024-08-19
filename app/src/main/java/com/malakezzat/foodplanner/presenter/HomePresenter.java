package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.fav.ProductLocalDataSource;
import com.malakezzat.foodplanner.model.local.week.MealDBWeek;
import com.malakezzat.foodplanner.model.local.week.ProductLocalDataSourceWeek;
import com.malakezzat.foodplanner.presenter.interview.IHomePresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements NetworkCallBack,IHomePresenter {

    private static final String TAG = "HomePresenter";
    IHomeView iHomeView;
    ProductRemoteDataSource productRemoteDataSource;
    List<Meal> mealList;
    ProductLocalDataSource productLocalDataSource;
    ProductLocalDataSourceWeek productLocalDataSourceWeek;
    public HomePresenter(IHomeView iHomeView, ProductRemoteDataSource productRemoteDataSource, ProductLocalDataSource productLocalDataSource) {
        mealList = new ArrayList<>();
        this.iHomeView = iHomeView;
        this.productRemoteDataSource = productRemoteDataSource;
        this.productLocalDataSource = productLocalDataSource;

    }

    public HomePresenter(IHomeView iHomeView, ProductLocalDataSourceWeek productLocalDataSourceWeek) {
        mealList = new ArrayList<>();
        this.iHomeView = iHomeView;
        this.productLocalDataSourceWeek = productLocalDataSourceWeek;
    }

    @Override
    public void getMeals() {
        productRemoteDataSource.getMultipleRandomMeals(10,this);
    }

    @Override
    public void getMeal() {
        productRemoteDataSource.getRandomMeal(this);
    }

    @Override
    public void getMealById(String id) {
        productRemoteDataSource.searchById(Integer.parseInt(id),this,0);
    }

    @Override
    public void addToFav(Meal meal) {
        productLocalDataSource.insertMeal(meal.toMealDB());
    }

    @Override
    public void removeFromFav(Meal meal) {
        productLocalDataSource.deleteMeal(meal.toMealDB());
    }

    @Override
    public void addToWeekPlan(Meal meal) {
        productLocalDataSourceWeek.insertWeekMeal(meal.toMealDBWeek());
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

    }

    @Override
    public void onFailureResult(String msg) {
        iHomeView.getError(msg);
    }
}
