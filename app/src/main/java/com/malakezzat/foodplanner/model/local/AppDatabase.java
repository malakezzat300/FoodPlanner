package com.malakezzat.foodplanner.model.local;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.Objects;

@Database(entities = {MealDB.class, MealDBWeek.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;
    public abstract MealDao getMealDAO();
    public abstract MealWeekDao getMealWeekDAO();
    public static synchronized AppDatabase getInstance(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        String uid = user.getUid();
        String dbName = "meal_database_" + uid;
        if (instance == null) {
            if (doesDatabaseExist(context, dbName)) {
                Log.d("AppDatabase", "Database exists for user: " + uid);
            } else {
                Log.d("AppDatabase", "Database does not exist for user: " + uid + ". Creating new one.");
            }
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static boolean doesDatabaseExist (Context context, String dbName){
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

}
