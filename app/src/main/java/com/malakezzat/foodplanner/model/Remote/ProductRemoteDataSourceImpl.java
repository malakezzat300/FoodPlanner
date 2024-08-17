package com.malakezzat.foodplanner.model.Remote;

import static com.malakezzat.foodplanner.model.Remote.Network.BASR_URL;
import static com.malakezzat.foodplanner.model.Remote.Network.networkCallBack;

import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.malakezzat.foodplanner.model.Remote.Network.*;
import com.malakezzat.foodplanner.model.data.CategoryList;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.data.MealList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductRemoteDataSourceImpl implements ProductRemoteDataSource {

    private static final String TAG = "ProductRemoteDataSourceImpl";
    Retrofit retrofit;
    Request service;

    public ProductRemoteDataSourceImpl(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Request.class);

    }

    @Override
    public void getRandomMeal(NetworkCallBack networkCallBack) {
        Network.networkCallBack = networkCallBack;
        Call<MealList> call = service.getRandomMeal();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(@NonNull Call<MealList> call, @NonNull Response<MealList> response) {
                Log.i(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }
            @Override
            public void onFailure(@NonNull Call<MealList> call, @NonNull Throwable t) {
                networkCallBack.onFailureResult(t.toString());
            }
        });
    }

    @Override
    public void getRandomMeals(NetworkCallBack networkCallBack) {
        Network.networkCallBack = networkCallBack;
        Call<MealList> call = service.getRandomMeal();
            call.enqueue(new Callback<MealList>() {
                @Override
                public void onResponse(@NonNull Call<MealList> call, @NonNull Response<MealList> response) {
                    Log.i(TAG, "onResponse: " + response.code());
                    if (response.isSuccessful()) {
                        networkCallBack.onSuccessResult(response.body().meals);
                        Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                        //TODO correct the implementation
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MealList> call, @NonNull Throwable t) {
                    networkCallBack.onFailureResult(t.toString());
                }
            });
    }

    public void getMultipleRandomMeals(int numberOfCalls, NetworkCallBack networkCallBack) {
        List<Meal> allMeals = new ArrayList<>();

        for (int i = 0; i < numberOfCalls; i++) {
            getRandomMeals(new NetworkCallBack() {
                @Override
                public void onSuccessResult(List<? extends Data> meals) {
                    allMeals.addAll((Collection<? extends Meal>) meals);
                    // Check if we have called the required number of times
                    if (allMeals.size() >= numberOfCalls) {
                        networkCallBack.onSuccessResult(allMeals);
                    }
                }

                @Override
                public void onFailureResult(String error) {
                    // Handle failure case here (optional)
                    networkCallBack.onFailureResult(error);
                }
            });
        }
    }

    @Override
    public void getCategories(NetworkCallBack networkCallBack) {
        Network.networkCallBack = networkCallBack;
        Call<CategoryList> call = service.getCategories();
        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().categories);
                    Log.i(TAG, "onResponse: " + response.body().categories.get(0).toString());
                    //TODO correct the implementation
                }
            }
            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                networkCallBack.onFailureResult(t.toString());
            }
        });
    }

    @Override
    public void searchByName(String name) {
        Call<MealList> call = service.searchByName(name);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void searchByFirstChar(String character) {
        Call<MealList> call = service.searchByFirstChar(character);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void searchById(int id) {
        Call<MealList> call = service.searchById(id);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void getCategoriesList() {
        Call<MealList> call = service.getCategoriesList();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void getCountriesList() {
        Call<MealList> call = service.getCountriesList();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void getIngredientsList() {
        Log.i(TAG, "getIngredientsList: ");

        Call<MealList> call = service.getIngredientsList();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void filterByIngredient(String ingredient) {
        Call<MealList> call = service.filterByIngredient(ingredient);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void filterByCategory(String category) {
        Call<MealList> call = service.filterByCategory(category);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }

    @Override
    public void filterByCountry(String country) {
        Call<MealList> call = service.filterByCountry(country);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    networkCallBack.onSuccessResult(response.body().meals);
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                    //TODO correct the implementation
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.toString());
            }
        });
    }
}
