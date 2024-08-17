package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.ProductLocalDataSource;
import com.malakezzat.foodplanner.presenter.interview.IFavPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IFavView;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class FavPresenter implements IFavPresenter {
    private static final String TAG = "FavPresenter";
    IFavView iFavView;
    ProductLocalDataSource productLocalDataSource;
    List<Meal> mealList;
    public FavPresenter(IFavView iFavView, ProductLocalDataSource productLocalDataSource) {
        mealList = new ArrayList<>();
        this.iFavView = iFavView;
        this.productLocalDataSource = productLocalDataSource;
    }

    @Override
    public LiveData<List<MealDB>> getStoredMeals() {
        Log.i(TAG, "getStoredMeals: ");
        return productLocalDataSource.getAllStoredMeals();
    }


    @Override
    public void removeFromFav(MealDB mealDB) {
        productLocalDataSource.deleteMeal(mealDB);
    }


}
