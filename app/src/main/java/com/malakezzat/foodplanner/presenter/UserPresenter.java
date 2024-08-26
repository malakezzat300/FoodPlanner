package com.malakezzat.foodplanner.presenter;

import com.malakezzat.foodplanner.model.local.MealLocalDataSource;
import com.malakezzat.foodplanner.model.repository.MealRepository;
import com.malakezzat.foodplanner.presenter.interview.IUserPresenter;

public class UserPresenter implements IUserPresenter {

    MealRepository mealRepository;
    public UserPresenter(MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }

    @Override
    public void backupUserData() {
        mealRepository.backupFavMeals();
        mealRepository.backupWeekPlan();
    }

    @Override
    public void restoreUserData() {
        mealRepository.restoreFavMeals();
        mealRepository.restoreWeekPlan();
    }
}
