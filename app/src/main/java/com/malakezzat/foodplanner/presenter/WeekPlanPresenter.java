package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.model.local.MealDBWeek;
import com.malakezzat.foodplanner.presenter.interview.IWeekPlanPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IWeekPlanView;

import java.util.ArrayList;
import java.util.List;

public class WeekPlanPresenter implements IWeekPlanPresenter{
    private static final String TAG = "WeekPlanPresenter";
    IWeekPlanView iWeekPlanView;
    MealLocalDataSource mealLocalDataSource;
    List<Meal> mealList;
    public WeekPlanPresenter(IWeekPlanView iWeekPlanView, MealLocalDataSource mealLocalDataSource) {
        mealList = new ArrayList<>();
        this.iWeekPlanView = iWeekPlanView;
        this.mealLocalDataSource = mealLocalDataSource;
    }


    public LiveData<List<MealDBWeek>> getWeekPlanMeals() {
        Log.i(TAG, "getStoredMeals: ");
        return mealLocalDataSource.getWeekPlanMeals();
    }


    public void removeFromWeekPlan(MealDB mealDB) {
        mealLocalDataSource.deleteWeekMeal(mealDB.toMeal().toMealDBWeek());
    }

}
