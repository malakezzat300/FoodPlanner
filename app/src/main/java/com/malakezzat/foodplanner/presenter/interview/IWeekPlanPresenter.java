package com.malakezzat.foodplanner.presenter.interview;

import androidx.lifecycle.LiveData;

import com.malakezzat.foodplanner.model.local.fav.MealDB;
import com.malakezzat.foodplanner.model.local.week.MealDBWeek;

import java.util.List;

public interface IWeekPlanPresenter {

    LiveData<List<MealDBWeek>> getWeekPlanMeals();
    void removeFromWeekPlan(MealDB mealDB);

}
