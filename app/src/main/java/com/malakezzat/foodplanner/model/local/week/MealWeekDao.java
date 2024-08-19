package com.malakezzat.foodplanner.model.local.week;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealWeekDao {
    @Query("SELECT * FROM MealDBWeek")
    LiveData<List<MealDBWeek>> getAll();

    @Query("SELECT * FROM MealDBWeek WHERE idMeal IN (:mealIds)")
    LiveData<List<MealDBWeek>> loadAllByIds(int[] mealIds);

    @Query("SELECT * FROM MealDBWeek WHERE idMeal IN (:mealId)")
    MealDBWeek loadById(int mealId);

    @Query("SELECT * FROM MealDBWeek WHERE strMeal LIKE :title LIMIT 1")
    MealDBWeek findByName(String title);

    @Insert
    void insertAll(com.malakezzat.foodplanner.model.local.week.MealDBWeek... meals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(com.malakezzat.foodplanner.model.local.week.MealDBWeek meal);

    @Delete
    void delete(com.malakezzat.foodplanner.model.local.week.MealDBWeek meal);
}
