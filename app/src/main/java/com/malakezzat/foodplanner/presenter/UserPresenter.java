package com.malakezzat.foodplanner.presenter;

import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.presenter.interview.IUserPresenter;

public class UserPresenter implements IUserPresenter {

    MealLocalDataSource mealLocalDataSource;
    public UserPresenter(MealLocalDataSource mealLocalDataSource){
        this.mealLocalDataSource = mealLocalDataSource;
    }

    @Override
    public void backupUserData() {
        mealLocalDataSource.backupFavMeals();
        mealLocalDataSource.backupWeekPlan();
    }

    @Override
    public void restoreUserData() {
        mealLocalDataSource.restoreFavMeals();
        mealLocalDataSource.restoreWeekPlan();
    }
}
