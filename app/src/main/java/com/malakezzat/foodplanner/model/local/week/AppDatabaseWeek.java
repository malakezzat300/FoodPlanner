package com.malakezzat.foodplanner.model.local.week;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MealDBWeek.class}, version = 1)
public abstract class AppDatabaseWeek extends RoomDatabase {
    private static AppDatabaseWeek instance = null;
    public abstract MealWeekDao getMealWeekDAO();
    public static synchronized AppDatabaseWeek getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseWeek.class, "week_plan_database")
                    .build();
        }
        return instance;
    }

}
