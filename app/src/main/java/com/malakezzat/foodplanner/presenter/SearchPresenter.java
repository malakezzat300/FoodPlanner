package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.model.repository.MealRepository;
import com.malakezzat.foodplanner.presenter.interview.ISearchPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class SearchPresenter implements NetworkCallBack, ISearchPresenter {

    private static final String TAG = "SearchPresenter";
    ISearchView iSearchView;
    MealRepository mealRepository;
    List<Meal> mealList;
    List<MealDB> favMeals;
    public SearchPresenter(ISearchView iSearchView,MealRepository mealRepository) {
        mealList = new ArrayList<>();
        this.iSearchView = iSearchView;
        this.mealRepository = mealRepository;
        new Thread(()->{
            favMeals = mealRepository.getAllStoredMealsCheck();
        }).start();
    }

    @Override
    public void getCountryList() {
        mealRepository.getCountriesList(this);
    }

    @Override
    public void getIngredientList() {
        mealRepository.getIngredientsList(this);
    }

    @Override
    public void getCategoryList() {
        mealRepository.getCategoriesList(this);
    }

    @Override
    public void searchByCountry(String country) {
        mealRepository.filterByCountry(country,this);
    }

    @Override
    public void searchByIngredient(String ingredient) {
        mealRepository.filterByIngredient(ingredient,this);
    }

    @Override
    public void searchByCategory(String category) {
        mealRepository.filterByCategory(category,this);
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
    public void getMealById(String id) {
        mealRepository.searchById(Integer.parseInt(id),this,0);
    }

    @Override
    public void getMealById(String id, int saveMode) {
        mealRepository.searchById(Integer.parseInt(id),this,saveMode);
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            updateFavMeals();
            latch.countDown();
        }).start();

        try {
            latch.await();  // Wait for the background thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onSuccessResult: " + listOfItems.get(0));
        if(listOfItems.get(0) instanceof Meal){
            List<Meal> meals = (List<Meal>) listOfItems;
            meals = checkFavMeals(favMeals,meals);
            if(listOfItems.size() > 1) {
                if (((Meal) listOfItems.get(0)).strMeal != null) {
                    iSearchView.getMealList(meals);
                } else if (((Meal) listOfItems.get(0)).strArea != null) {
                    iSearchView.getCountryList(meals);
                } else if (((Meal) listOfItems.get(0)).strIngredient != null) {
                    iSearchView.getIngredientList(meals);
                } else if (((Meal) listOfItems.get(0)).strCategory != null) {
                    iSearchView.getCategoryList(meals);
                }
            } else {
                iSearchView.getMealById(meals.get(0),0);
            }
        }
    }

    @Override
    public void onSuccessResult(List<? extends Data> listOfItems, int saveMode) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            updateFavMeals();
            latch.countDown();
        }).start();

        try {
            latch.await();  // Wait for the background thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Meal> meals = (List<Meal>) listOfItems;
        meals = checkFavMeals(favMeals,meals);
        iSearchView.getMealById(meals.get(0),saveMode);

    }

    @Override
    public void onFailureResult(String msg) {
        iSearchView.getError(msg);
    }


    public static List<Meal> checkFavMeals(List<MealDB> favMeals, List<Meal> mealsToCheck) {
        Set<String> favMealIds = new HashSet<>();
        for (MealDB favMeal : favMeals) {
            favMealIds.add(favMeal.idMeal);
        }
        List<Meal> meals = new ArrayList<>();
        for (Meal meal : mealsToCheck) {
            if (favMealIds.contains(meal.idMeal)) {
                meal.isFav = true;
            } else {
                meal.isFav = false;
            }
            meals.add(meal);
        }
        return meals;
    }

    private synchronized void updateFavMeals() {
        favMeals = mealRepository.getAllStoredMealsCheck();
    }
}
