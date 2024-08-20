package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.presenter.interview.ISearchPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements NetworkCallBack, ISearchPresenter {

    private static final String TAG = "SearchPresenter";
    ISearchView iSearchView;
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    List<Meal> mealList;
    public SearchPresenter(ISearchView iSearchView, MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource) {
        mealList = new ArrayList<>();
        this.iSearchView = iSearchView;
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;

    }

    public SearchPresenter(ISearchView iSearchView, MealLocalDataSource mealLocalDataSource) {
        mealList = new ArrayList<>();
        this.iSearchView = iSearchView;
        this.mealLocalDataSource = mealLocalDataSource;

    }

    @Override
    public void getCountryList() {
        mealRemoteDataSource.getCountriesList(this);
    }

    @Override
    public void getIngredientList() {
        mealRemoteDataSource.getIngredientsList(this);
    }

    @Override
    public void getCategoryList() {
        mealRemoteDataSource.getCategoriesList(this);
    }

    @Override
    public void searchByCountry(String country) {
        mealRemoteDataSource.filterByCountry(country,this);
    }

    @Override
    public void searchByIngredient(String ingredient) {
        mealRemoteDataSource.filterByIngredient(ingredient,this);
    }

    @Override
    public void searchByCategory(String category) {
        mealRemoteDataSource.filterByCategory(category,this);
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
    public void getMealById(String id) {
        mealRemoteDataSource.searchById(Integer.parseInt(id),this,0);
    }

    @Override
    public void getMealById(String id, int saveMode) {
        mealRemoteDataSource.searchById(Integer.parseInt(id),this,saveMode);
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
