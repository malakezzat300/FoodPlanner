package com.malakezzat.foodplanner.model.local;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import java.util.concurrent.Executors;

public class BackupMeals {

    private final FirebaseFirestore firestore;
    private final MealDao myDao;
    private final MealWeekDao myWeekDao;
    private final FirebaseAuth auth;

    public BackupMeals(MealDao myDao, MealWeekDao myWeekDao) {
        firestore = FirebaseFirestore.getInstance();
        this.myDao = myDao;
        this.myWeekDao = myWeekDao;
        auth = FirebaseAuth.getInstance();

    }

    public void backupDataToFirestore() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Backup FavMeals
            CollectionReference collectionRef = firestore.collection("users")
                    .document(userId)
                    .collection("FavMeals");

            collectionRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    WriteBatch batch = firestore.batch();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        batch.delete(document.getReference());
                    }
                    batch.commit().addOnCompleteListener(deleteTask -> {
                        if (deleteTask.isSuccessful()) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                List<MealDB> items = myDao.getAllMealsList();
                                for (MealDB item : items) {
                                    collectionRef.document(item.idMeal).set(item)
                                            .addOnSuccessListener(aVoid -> Log.d("FavMealsBackup", "Data backed up successfully - Meal : " + item.idMeal))
                                            .addOnFailureListener(e -> Log.e("FavMealsBackup", "Error backing up data", e));
                                }
                            });
                        } else {
                            Log.e("FavMealsBackup", "Error deleting old data", deleteTask.getException());
                        }
                    });
                } else {
                    Log.e("FavMealsBackup", "Error getting documents for deletion: ", task.getException());
                }
            });

            // Backup WeekPlan
            CollectionReference collectionRefWeek = firestore.collection("users")
                    .document(userId)
                    .collection("WeekPlan");

            collectionRefWeek.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    WriteBatch batch = firestore.batch();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        batch.delete(document.getReference());
                    }
                    batch.commit().addOnCompleteListener(deleteTask -> {
                        if (deleteTask.isSuccessful()) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                List<MealDBWeek> itemsWeek = myWeekDao.getAllMealsList();
                                for (MealDBWeek item : itemsWeek) {
                                    collectionRefWeek.document(item.idMeal).set(item)
                                            .addOnSuccessListener(aVoid -> Log.d("WeekPlanBackup", "Data backed up successfully - Meal : " + item.idMeal))
                                            .addOnFailureListener(e -> Log.e("WeekPlanBackup", "Error backing up data", e));
                                }
                            });
                        } else {
                            Log.e("WeekPlanBackup", "Error deleting old data", deleteTask.getException());
                        }
                    });
                } else {
                    Log.e("WeekPlanBackup", "Error getting documents for deletion: ", task.getException());
                }
            });
        }
    }
    public void restoreDataFromFirestore() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            Executors.newSingleThreadExecutor().execute(myDao::clearTable);

            CollectionReference collectionRef = firestore.collection("users")
                    .document(userId)
                    .collection("FavMeals");

            collectionRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MealDB item = document.toObject(MealDB.class);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            myDao.insertAll(item);
                        });
                    }
                } else {
                    Log.e("FavMealsRestore", "Error getting documents: ", task.getException());
                }
            });

            Executors.newSingleThreadExecutor().execute(myWeekDao::clearTable);

            CollectionReference collectionRefWeek = firestore.collection("users")
                    .document(userId)
                    .collection("WeekPlan");

            collectionRefWeek.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MealDBWeek item = document.toObject(MealDBWeek.class);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            myWeekDao.insertAll(item);
                        });
                    }
                } else {
                    Log.e("WeekPlanRestore", "Error getting documents: ", task.getException());
                }
            });
        }
    }
}
