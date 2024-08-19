package com.malakezzat.foodplanner.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.fav.MealDB;
import com.malakezzat.foodplanner.model.local.fav.ProductLocalDataSource;
import com.malakezzat.foodplanner.model.local.week.MealDBWeek;
import com.malakezzat.foodplanner.model.local.week.ProductLocalDataSourceWeek;
import com.malakezzat.foodplanner.presenter.interview.IWeekPlanPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IWeekPlanView;

import java.util.ArrayList;
import java.util.List;

public class WeekPlanPresenter implements IWeekPlanPresenter{
    private static final String TAG = "WeekPlanPresenter";
    IWeekPlanView iWeekPlanView;
    ProductLocalDataSourceWeek productLocalDataSourceWeek;
    List<Meal> mealList;
    public WeekPlanPresenter(IWeekPlanView iWeekPlanView, ProductLocalDataSourceWeek productLocalDataSourceWeek) {
        mealList = new ArrayList<>();
        this.iWeekPlanView = iWeekPlanView;
        this.productLocalDataSourceWeek = productLocalDataSourceWeek;
    }


    public LiveData<List<MealDBWeek>> getWeekPlanMeals() {
        Log.i(TAG, "getStoredMeals: ");
        return productLocalDataSourceWeek.getWeekPlanMeals();
    }

    @Override


    public void removeFromWeekPlan(MealDB mealDB) {
        productLocalDataSourceWeek.deleteWeekMeal(mealDB.toMeal().toMealDBWeek());
    }

}
