package com.malakezzat.foodplanner.model.repository;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSource;
import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealDBWeek;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;

import java.util.Collections;
import java.util.List;

public class MealRepositoryImpl implements MealRepository {

    MealLocalDataSource mealLocalDataSource;
    MealRemoteDataSource mealRemoteDataSource;

    public MealRepositoryImpl(MealLocalDataSource mealLocalDataSource){
        this.mealLocalDataSource = mealLocalDataSource;
    }

    public MealRepositoryImpl(MealRemoteDataSource mealRemoteDataSource){
        this.mealRemoteDataSource = mealRemoteDataSource;
    }

    public MealRepositoryImpl(MealLocalDataSource mealLocalDataSource,MealRemoteDataSource mealRemoteDataSource){
        this.mealLocalDataSource = mealLocalDataSource;
        this.mealRemoteDataSource = mealRemoteDataSource;
    }

    @Override
    public void insertMeal(MealDB mealDB) {
        mealLocalDataSource.insertMeal(mealDB);
    }

    @Override
    public void deleteMeal(MealDB mealDB) {
        mealLocalDataSource.deleteMeal(mealDB);
    }

    @Override
    public LiveData<List<MealDB>> getAllStoredMeals() {
        return mealLocalDataSource.getAllStoredMeals();
    }

    @Override
    public List<MealDB> getAllStoredMealsCheck() {
        return mealLocalDataSource.getAllStoredMealsCheck();
    }

    @Override
    public void backupFavMeals() {
        mealLocalDataSource.backupFavMeals();
    }

    @Override
    public void restoreFavMeals() {
        mealLocalDataSource.restoreFavMeals();
    }

    @Override
    public void insertWeekMeal(MealDBWeek mealWeekDB) {
        mealLocalDataSource.insertWeekMeal(mealWeekDB);
    }

    @Override
    public void deleteWeekMeal(MealDBWeek mealWeekDB) {
        mealLocalDataSource.deleteWeekMeal(mealWeekDB);
    }

    @Override
    public LiveData<List<MealDBWeek>> getWeekPlanMeals() {
        return mealLocalDataSource.getWeekPlanMeals();
    }

    @Override
    public void backupWeekPlan() {
        mealLocalDataSource.backupWeekPlan();
    }

    @Override
    public void restoreWeekPlan() {
        mealLocalDataSource.restoreWeekPlan();
    }

    @Override
    public void getRandomMeal(NetworkCallBack networkCallBack) {
        mealRemoteDataSource.getRandomMeal(networkCallBack);
    }

    @Override
    public void getRandomMeals(NetworkCallBack networkCallBack) {
        mealRemoteDataSource.getRandomMeals(networkCallBack);
    }

    @Override
    public void getMultipleRandomMeals(int numberOfCalls, NetworkCallBack networkCallBack) {
        mealRemoteDataSource.getMultipleRandomMeals(numberOfCalls,networkCallBack);
    }

    @Override
    public void getCategories(NetworkCallBack networkCallBack) {
        mealRemoteDataSource.getCategories(networkCallBack);
    }

    @Override
    public void searchById(int id, NetworkCallBack networkCallBack, int saveMode) {
        mealRemoteDataSource.searchById(id,networkCallBack,saveMode);
    }

    @Override
    public void getCategoriesList(NetworkCallBack networkCallBack) {
        mealRemoteDataSource.getCountriesList(networkCallBack);
    }

    @Override
    public void getCountriesList(NetworkCallBack networkCallBack) {
        mealRemoteDataSource.getCountriesList(networkCallBack);
    }

    @Override
    public void getIngredientsList(NetworkCallBack networkCallBack) {
        mealRemoteDataSource.getIngredientsList(networkCallBack);
    }

    @Override
    public void filterByIngredient(String ingredient, NetworkCallBack networkCallBack) {
        mealRemoteDataSource.filterByIngredient(ingredient,networkCallBack);
    }

    @Override
    public void filterByCategory(String category, NetworkCallBack networkCallBack) {
        mealRemoteDataSource.filterByCategory(category,networkCallBack);
    }

    @Override
    public void filterByCountry(String country, NetworkCallBack networkCallBack) {
        mealRemoteDataSource.filterByCountry(country,networkCallBack);
    }
}
