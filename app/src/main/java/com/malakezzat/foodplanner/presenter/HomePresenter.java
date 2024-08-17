package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.CategoryList;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.data.MealList;
import com.malakezzat.foodplanner.presenter.interview.IHomePresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements NetworkCallBack,IHomePresenter {

    private static final String TAG = "HomePresenter";
    IHomeView iHomeView;
    ProductRemoteDataSource productRemoteDataSource;
    List<Meal> mealList;
    public HomePresenter(IHomeView iHomeView,ProductRemoteDataSource productRemoteDataSource) {
        mealList = new ArrayList<>();
        this.iHomeView = iHomeView;
        this.productRemoteDataSource = productRemoteDataSource;

    }

    @Override
    public void getMeals() {
        productRemoteDataSource.getMultipleRandomMeals(10,this);
    }

    @Override
    public void addToFav(Meal meal) {

    }

    @Override
    public void removeFromFav(Meal meal) {

    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems) {
        Log.i(TAG, "onSuccessResult: " + listOfItems.get(0));
        if(listOfItems.get(0) instanceof Meal){
            iHomeView.getMeals((List<Meal>) listOfItems);
        } else if(listOfItems.get(0) instanceof Category){
            //TODO categories
        }

    }

    @Override
    public void onFailureResult(String msg) {
        iHomeView.getError(msg);
    }
}
