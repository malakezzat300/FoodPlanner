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

    @Query("SELECT * FROM MealDB")
    List<MealDB> getAllCheck();

    @Query("SELECT * FROM MealDB")
    List<MealDB> getAllMealsList();

    @Query("SELECT * FROM MealDB WHERE idMeal IN (:mealIds)")
    LiveData<List<MealDB>> loadAllByIds(int[] mealIds);

    @Query("SELECT * FROM MealDB WHERE idMeal IN (:mealId)")
    MealDB loadById(int mealId);

    @Query("SELECT * FROM MealDB WHERE strMeal LIKE :title LIMIT 1")
    MealDB findByName(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(MealDB... meals);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealDB meal);

    @Query("DELETE FROM MealDB")
    void clearTable();

    @Delete
    void delete(MealDB meal);
}
