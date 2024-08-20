package com.malakezzat.foodplanner.model.local;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MealLocalDataSourceImpl implements MealLocalDataSource {

    private static final String TAG = "MealLocalDataSourceImpl";
    AppDatabase appDatabase;
    MealDao mealDao;
    MealWeekDao mealWeekDao;
    BackupMeals backupMeals;
    public MealLocalDataSourceImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        mealDao = appDatabase.getMealDAO();
        mealWeekDao = appDatabase.getMealWeekDAO();
        backupMeals = new BackupMeals(mealDao,mealWeekDao);
    }

    @Override
    public void insertMeal(MealDB mealDB) {
        new Thread(() -> {
            mealDao.insert(mealDB);
        }).start();
    }

    @Override
    public void deleteMeal(MealDB mealDB) {
        new Thread(() -> {
            mealDao.delete(mealDB);
        }).start();
    }

    @Override
    public LiveData<List<MealDB>> getAllStoredMeals() {
        Log.i(TAG, "getAllStoredMeals: ");
        return mealDao.getAll();
    }

    @Override
    public MealDB getFavMealDB(int id) {
        return mealDao.loadById(id);
    }

    @Override
    public void backupFavMeals() {
        backupMeals.backupDataToFirestore();
    }

    @Override
    public void restoreFavMeals() {
        backupMeals.restoreDataFromFirestore();
    }

    @Override
    public void insertWeekMeal(MealDBWeek mealWeekDB) {
        new Thread(() -> {
            mealWeekDao.insert(mealWeekDB);
        }).start();
    }

    @Override
    public void deleteWeekMeal(MealDBWeek mealWeekDB) {
        new Thread(() -> {
            mealWeekDao.delete(mealWeekDB);
        }).start();
    }

    @Override
    public LiveData<List<MealDBWeek>> getWeekPlanMeals() {
        return mealWeekDao.getAll();
    }

    @Override
    public MealDBWeek getMealDBWeek(int id) {
        return mealWeekDao.loadById(id);
    }

    @Override
    public void backupWeekPlan() {
        backupMeals.backupDataToFirestore();
    }

    @Override
    public void restoreWeekPlan() {
        backupMeals.restoreDataFromFirestore();
    }
}
