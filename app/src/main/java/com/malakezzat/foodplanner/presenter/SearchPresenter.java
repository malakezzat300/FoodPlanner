package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.presenter.interview.ISearchPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter implements NetworkCallBack, ISearchPresenter {

    private static final String TAG = "SearchPresenter";
    ISearchView iSearchView;
    ProductRemoteDataSource productRemoteDataSource;
    List<Meal> mealList;
    public SearchPresenter(ISearchView iSearchView, ProductRemoteDataSource productRemoteDataSource) {
        mealList = new ArrayList<>();
        this.iSearchView = iSearchView;
        this.productRemoteDataSource = productRemoteDataSource;

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
    public void onSuccessResult(List<? extends Data> listOfItems) {
        Log.i(TAG, "onSuccessResult: " + listOfItems.get(0));
        if(listOfItems.get(0) instanceof Meal){
            if(((Meal) listOfItems.get(0)).strMeal != null){
                iSearchView.getMealList((List<Meal>) listOfItems);
            } else if(((Meal) listOfItems.get(0)).strArea != null){
                iSearchView.getCountryList((List<Meal>) listOfItems);
            } else if(((Meal) listOfItems.get(0)).strIngredient != null){
                iSearchView.getIngredientList((List<Meal>) listOfItems);
            } else if(((Meal) listOfItems.get(0)).strCategory != null){
                iSearchView.getCategoryList((List<Meal>) listOfItems);
            }
        } else if(listOfItems.get(0) instanceof Category){
            //TODO categories
        }

    }

    @Override
    public void onFailureResult(String msg) {
        iSearchView.getError(msg);
    }


}
