package com.malakezzat.foodplanner.presenter.interview;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealDBWeek;

import java.util.List;

public interface IWeekPlanPresenter {

    LiveData<List<MealDBWeek>> getWeekPlanMeals();
    void removeFromWeekPlan(MealDB mealDB);

}
