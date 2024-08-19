package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.fav.ProductLocalDataSource;
import com.malakezzat.foodplanner.model.local.week.ProductLocalDataSourceWeek;
import com.malakezzat.foodplanner.presenter.interview.ISearchPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements NetworkCallBack, ISearchPresenter {

    private static final String TAG = "SearchPresenter";
    ISearchView iSearchView;
    ProductRemoteDataSource productRemoteDataSource;
    ProductLocalDataSource productLocalDataSource;
    ProductLocalDataSourceWeek productLocalDataSourceWeek;
    List<Meal> mealList;
    public SearchPresenter(ISearchView iSearchView, ProductRemoteDataSource productRemoteDataSource, ProductLocalDataSource productLocalDataSource) {
        mealList = new ArrayList<>();
        this.iSearchView = iSearchView;
        this.productRemoteDataSource = productRemoteDataSource;
        this.productLocalDataSource = productLocalDataSource;

    }

    public SearchPresenter(ISearchView iSearchView, ProductLocalDataSourceWeek productLocalDataSourceWeek) {
        mealList = new ArrayList<>();
        this.iSearchView = iSearchView;
        this.productLocalDataSourceWeek = productLocalDataSourceWeek;

    }

    @Override
    public void getCountryList() {
        productRemoteDataSource.getCountriesList(this);
    }

    @Override
    public void getIngredientList() {
        productRemoteDataSource.getIngredientsList(this);
    }

    @Override
    public void getCategoryList() {
        productRemoteDataSource.getCategoriesList(this);
    }

    @Override
    public void searchByCountry(String country) {
        productRemoteDataSource.filterByCountry(country,this);
    }

    @Override
    public void searchByIngredient(String ingredient) {
        productRemoteDataSource.filterByIngredient(ingredient,this);
    }

    @Override
    public void searchByCategory(String category) {
        productRemoteDataSource.filterByCategory(category,this);
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
    public void getMealById(String id) {
        productRemoteDataSource.searchById(Integer.parseInt(id),this,0);
    }

    @Override
    public void getMealById(String id, int saveMode) {
        productRemoteDataSource.searchById(Integer.parseInt(id),this,saveMode);
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems) {
        Log.i(TAG, "onSuccessResult: " + listOfItems.get(0));
        if(listOfItems.get(0) instanceof Meal){
            if(listOfItems.size() > 1) {
                if (((Meal) listOfItems.get(0)).strMeal != null) {
                    iSearchView.getMealList((List<Meal>) listOfItems);
                } else if (((Meal) listOfItems.get(0)).strArea != null) {
                    iSearchView.getCountryList((List<Meal>) listOfItems);
                } else if (((Meal) listOfItems.get(0)).strIngredient != null) {
                    iSearchView.getIngredientList((List<Meal>) listOfItems);
                } else if (((Meal) listOfItems.get(0)).strCategory != null) {
                    iSearchView.getCategoryList((List<Meal>) listOfItems);
                }
            } else {
                iSearchView.getMealById((Meal) listOfItems.get(0),0);
            }
        }
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems, int saveMode) {
        iSearchView.getMealById((Meal) listOfItems.get(0),saveMode);

    }

    @Override
    public void onFailureResult(String msg) {
        iSearchView.getError(msg);
    }


}
