package com.malakezzat.foodplanner.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MealDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;
    public abstract MealDao getMealDAO();
    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "meal_database")
                    .build();
        }
        return instance;
    }

}
