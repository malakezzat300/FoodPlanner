package com.malakezzat.foodplanner.model.Remote;

import com.malakezzat.foodplanner.model.data.CategoryList;
import com.malakezzat.foodplanner.model.data.MealList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductRemoteDataSource {
 void getRandomMeal();
 void getCategories();
 void searchByName(String name);
 void searchByFirstChar(String character);
 void searchById(int id);
 void getCategoriesList();
 void getCountriesList();
 void getIngredientsList();
 void filterByIngredient(String ingredient);
 void filterByCategory(String category);
 void filterByCountry(String country);
}
