package com.malakezzat.foodplanner.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM MealDB")
    LiveData<List<MealDB>> getAll();

    @Query("SELECT * FROM MealDB WHERE idMeal IN (:mealIds)")
    LiveData<List<MealDB>> loadAllByIds(int[] mealIds);

    @Query("SELECT * FROM MealDB WHERE idMeal IN (:mealId)")
    MealDB loadById(int mealId);

    @Query("SELECT * FROM MealDB WHERE strMeal LIKE :title LIMIT 1")
    MealDB findByName(String title);

    @Insert
    void insertAll(MealDB... meals);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealDB meal);

    @Delete
    void delete(MealDB meal);
}
