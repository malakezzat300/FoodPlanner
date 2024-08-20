package com.malakezzat.foodplanner.model.Remote;

public interface MealRemoteDataSource {
 void getRandomMeal(NetworkCallBack networkCallBack);
 void getRandomMeals(NetworkCallBack networkCallBack);
 void getMultipleRandomMeals(int numberOfCalls, NetworkCallBack networkCallBack);
 void getCategories(NetworkCallBack networkCallBack);
 void searchByName(String name);
 void searchByFirstChar(String character);
 void searchById(int id,NetworkCallBack networkCallBack,int saveMode);
 void getCategoriesList(NetworkCallBack networkCallBack);
 void getCountriesList(NetworkCallBack networkCallBack);
 void getIngredientsList(NetworkCallBack networkCallBack);
 void filterByIngredient(String ingredient,NetworkCallBack networkCallBack);
 void filterByCategory(String category,NetworkCallBack networkCallBack);
 void filterByCountry(String country,NetworkCallBack networkCallBack);
}
