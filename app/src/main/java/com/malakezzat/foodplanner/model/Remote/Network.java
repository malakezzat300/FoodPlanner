package com.malakezzat.foodplanner.model.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Network {

    private static final String TAG = "RetrofitRequest";
    Retrofit retrofit;
    public static final String BASR_URL = "https://www.themealdb.com/api/json/v1/1/";
    static NetworkCallBack networkCallBack;

    public Network(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //TODO Clean this code
    /*
    public void getRandomMeal() {
        Log.i(TAG, "getProducts==: ");

        Call<MealList> call = service.getRandomMeal();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(@NonNull Call<MealList> call, @NonNull Response<MealList> response) {
                Log.i(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    //networkCallBack.onSuccessResult(response.body().getProducts());
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());

                }
            }
            @Override
            public void onFailure(@NonNull Call<MealList> call, @NonNull Throwable t) {
                //networkCallBack.onFailureResult(t.toString());
            }
        });
    }



    public void getCategories() {
        Log.i(TAG, "getProducts==: ");

        Call<CategoryList> call = service.getCategories();
        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if (response.isSuccessful()) {
                    //networkCallBack.onSuccessResult(response.body().getProducts());
                    Log.i(TAG, "onResponse: " + response.body().categories.get(0).toString());

                }
            }
            @Override
            public void onFailure(Call<CategoryList> call, Throwable throwable) {

            }
        });
    }

    public void searchByName(String name) {
        Log.i(TAG, "searchByName: " + name);

        Call<MealList> call = service.searchByName(name);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void searchByFirstChar(String character) {
        Log.i(TAG, "searchByFirstChar: " + character);

        Call<MealList> call = service.searchByFirstChar(character);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void searchById(int id) {
        Log.i(TAG, "searchById: " + id);

        Call<MealList> call = service.searchById(id);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void getCategoriesList() {
        Log.i(TAG, "getCategoriesList: ");

        Call<MealList> call = service.getCategoriesList();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void getCountriesList() {
        Log.i(TAG, "getCountriesList: ");

        Call<MealList> call = service.getCountriesList();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void getIngredientsList() {
        Log.i(TAG, "getIngredientsList: ");

        Call<MealList> call = service.getIngredientsList();
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void filterByIngredient(String ingredient) {
        Log.i(TAG, "filterByIngredient: " + ingredient);

        Call<MealList> call = service.filterByIngredient(ingredient);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void filterByCategory(String category) {
        Log.i(TAG, "filterByCategory: " + category);

        Call<MealList> call = service.filterByCategory(category);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }

    public void filterByCountry(String country) {
        Log.i(TAG, "filterByCountry: " + country);

        Call<MealList> call = service.filterByCountry(country);
        call.enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: " + response.body().meals.get(0).toString());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
    }
     */
}
