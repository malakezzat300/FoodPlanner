package com.malakezzat.foodplanner.model.Remote;

import com.malakezzat.foodplanner.model.data.CategoryList;
import com.malakezzat.foodplanner.model.data.MealList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface Request {
    @GET("random.php")
    Call<MealList> getRandomMeal();

    @GET("categories.php")
    Call<CategoryList> getCategories();

    @GET("search.php")
    Call<MealList> searchByName(@Query("s") String name);

    @GET("search.php")
    Call<MealList> searchByFirstChar(@Query("f") String character);

    @GET("lookup.php")
    Call<MealList> searchById(@Query("i") int id);

    @GET("list.php?c=list")
    Call<MealList> getCategoriesList();

    @GET("list.php?a=list")
    Call<MealList> getCountriesList();

    @GET("list.php?i=list")
    Call<MealList> getIngredientsList();

    @GET("filter.php")
    Call<MealList> filterByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealList> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealList> filterByCountry(@Query("a") String country);


}

