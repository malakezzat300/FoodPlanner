package com.malakezzat.foodplanner.model.local.week;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductLocalDataSourceWeekImpl implements ProductLocalDataSourceWeek {

    private static final String TAG = "ProductLocalDataSourceImpl";
    AppDatabaseWeek appDatabase;
    MealWeekDao mealDao;
    public ProductLocalDataSourceWeekImpl(AppDatabaseWeek appDatabaseWeek) {
        this.appDatabase = appDatabaseWeek;
        mealDao = appDatabaseWeek.getMealWeekDAO();
    }

    @Override
    public void insertWeekMeal(MealDBWeek mealWeekDB) {
        new Thread(() -> {
            mealDao.insert(mealWeekDB);
        }).start();
    }

    @Override
    public void deleteWeekMeal(MealDBWeek mealWeekDB) {
        new Thread(() -> {
            mealDao.delete(mealWeekDB);
        }).start();
    }

    @Override
    public LiveData<List<MealDBWeek>> getWeekPlanMeals() {
        return mealDao.getAll();
    }

    @Override
    public MealDBWeek getMealDB(int id) {
        return mealDao.loadById(id);
    }
}
