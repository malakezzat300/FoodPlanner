package com.malakezzat.foodplanner.model.local;

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

    @Query("SELECT * FROM MealDBWeek")
    List<MealDBWeek> getAllMealsList();

    @Query("SELECT * FROM MealDBWeek WHERE idMeal IN (:mealIds)")
    LiveData<List<MealDBWeek>> loadAllByIds(int[] mealIds);

    @Query("SELECT * FROM MealDBWeek WHERE idMeal IN (:mealId)")
    MealDBWeek loadById(int mealId);

    @Query("SELECT * FROM MealDBWeek WHERE strMeal LIKE :title LIMIT 1")
    MealDBWeek findByName(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(MealDBWeek... meals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealDBWeek meal);



    @Delete
    void delete(MealDBWeek meal);
}
