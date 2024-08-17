package com.malakezzat.foodplanner.model.local;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductLocalDataSourceImpl implements ProductLocalDataSource{

    private static final String TAG = "ProductLocalDataSourceImpl";
    AppDatabase appDatabase;
    MealDao mealDao;
    public ProductLocalDataSourceImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        mealDao = appDatabase.getMealDAO();
    }

    @Override
    public void insertMeal(MealDB productModel) {
        new Thread(() -> {
            mealDao.insert(productModel);
        }).start();
    }

    @Override
    public void deleteMeal(MealDB productModel) {
        new Thread(() -> {
            mealDao.delete(productModel);
        }).start();
    }

    @Override
    public LiveData<List<MealDB>> getAllStoredMeals() {
        Log.i(TAG, "getAllStoredMeals: ");
        return mealDao.getAll();
    }

    @Override
    public MealDB getMealDB(int id) {
        return mealDao.loadById(id);
    }
}
