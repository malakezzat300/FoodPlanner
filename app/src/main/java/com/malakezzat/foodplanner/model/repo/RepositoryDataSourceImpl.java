package com.malakezzat.foodplanner.model.repo;

import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSource;
import com.malakezzat.foodplanner.model.local.MealLocalDataSource;

public class RepositoryDataSourceImpl {


    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    public RepositoryDataSourceImpl(MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource){
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }




}
