package com.malakezzat.foodplanner.presenter;

import static com.malakezzat.foodplanner.presenter.SearchPresenter.checkFavMeals;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.model.repository.MealRepository;
import com.malakezzat.foodplanner.presenter.interview.IHomePresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements NetworkCallBack,IHomePresenter {

    private static final String TAG = "HomePresenter";
    IHomeView iHomeView;
    List<Meal> mealList;
    MealRepository mealRepository;
    List<MealDB> favMeals;
    public HomePresenter(IHomeView iHomeView, MealRepository mealRepository) {
        mealList = new ArrayList<>();
        this.iHomeView = iHomeView;
        this.mealRepository = mealRepository;
        new Thread(()->{
            favMeals = mealRepository.getAllStoredMealsCheck();
        }).start();
    }

    @Override
    public void getMeals() {
        mealRepository.getMultipleRandomMeals(10,this);
    }

    @Override
    public void getMeal() {
        mealRepository.getRandomMeal(this);
    }

    @Override
    public void getMealById(String id) {
        mealRepository.searchById(Integer.parseInt(id),this,0);
    }

    @Override
    public void getMealById(String id, int saveMode) {
        mealRepository.searchById(Integer.parseInt(id),this,saveMode);
    }

    @Override
    public void addToFav(Meal meal) {
        meal.isFav = true;
        mealRepository.insertMeal(meal.toMealDB());
    }

    @Override
    public void removeFromFav(Meal meal) {
        meal.isFav = false;
        mealRepository.deleteMeal(meal.toMealDB());
    }

    @Override
    public void addToWeekPlan(Meal meal) {
        mealRepository.insertWeekMeal(meal.toMealDBWeek());
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems) {

        Log.i(TAG, "onSuccessResult: " + listOfItems.get(0));
        if(listOfItems.get(0) instanceof Meal){
            List<Meal> meals = (List<Meal>) listOfItems;
            meals = checkFavMeals(favMeals,meals);
            if(listOfItems.size() > 1){
                iHomeView.getMeals(meals);
            } else {
                iHomeView.getMeal(meals,0);
            }
        }
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems, int saveMode) {
        List<Meal> meals = (List<Meal>) listOfItems;
        meals = checkFavMeals(favMeals,meals);
        iHomeView.getMeal(meals,saveMode);
    }

    @Override
    public void onFailureResult(String msg) {
        iHomeView.getError(msg);
    }
}
