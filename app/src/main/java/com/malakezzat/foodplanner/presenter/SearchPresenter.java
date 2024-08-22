package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import com.malakezzat.foodplanner.model.Remote.NetworkCallBack;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSource;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.presenter.interview.ISearchPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchPresenter implements NetworkCallBack, ISearchPresenter {

    private static final String TAG = "SearchPresenter";
    ISearchView iSearchView;
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    List<Meal> mealList;
    List<MealDB> favMeals;
    public SearchPresenter(ISearchView iSearchView, MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource) {
        mealList = new ArrayList<>();
        this.iSearchView = iSearchView;
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
        new Thread(()->{
            favMeals = mealLocalDataSource.getAllStoredMealsCheck();
        }).start();
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
        meal.isFav = true;
        mealLocalDataSource.insertMeal(meal.toMealDB());
    }

    @Override
    public void removeFromFav(Meal meal) {
        meal.isFav = false;
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


//    public static List<Meal> checkFavMeals(List<MealDB> favMeals,List<Meal> mealsToCheck){
//        List<Meal> meals;
//        if(!favMeals.isEmpty()) {
//            meals = new ArrayList<>();
//            for (Meal meal : mealsToCheck) {
//                for (MealDB favMeal : favMeals) {
//                    if (favMeal.idMeal.equals(meal.idMeal)) {
//                        meal.isFav = true;
//                    }
//                }
//                //if(!meals.contains(meal)) {
//                    meals.add(meal);
//                //}
//            }
//        } else {
//            meals = mealsToCheck;
//        }
//        return meals;
//    }

}
