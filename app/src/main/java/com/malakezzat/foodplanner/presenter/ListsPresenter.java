package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.presenter.interview.IListsPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IListsView;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class ListsPresenter implements NetworkCallBack,IListsPresenter {

    private static final String TAG = "ListsPresenter";
    IListsView iListsView;
    ProductRemoteDataSource productRemoteDataSource;

    public ListsPresenter(IListsView iListsView, ProductRemoteDataSource productRemoteDataSource) {
        this.iListsView = iListsView;
        this.productRemoteDataSource = productRemoteDataSource;

    }

    @Override
    public void getCategoriesList() {
        productRemoteDataSource.getCategories(this);
    }

    @Override
    public void getCountriesList() {
        productRemoteDataSource.getCountriesList(this);
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems) {
        Log.i(TAG, "onSuccessResult: " + listOfItems.get(0));
        if(listOfItems.get(0) instanceof Meal){
            iListsView.getCountriesList((List<Meal>) listOfItems);
        } else if(listOfItems.get(0) instanceof Category){
            iListsView.getCategoriesList((List<Category>) listOfItems);
        }

    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems, int saveMode) {

    }

    @Override
    public void onFailureResult(String msg) {
        iListsView.getError(msg);
    }
}
