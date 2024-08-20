package com.malakezzat.foodplanner.model.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.malakezzat.foodplanner.model.data.Meal;

@Entity
public class MealDB {

    @PrimaryKey
    @NonNull
    public String idMeal;
    public String strMeal;
    public String strDrinkAlternate;
    public String strCategory;
    public String strArea;
    public String strInstructions;
    public String strMealThumb;
    public String strTags;
    public String strYoutube;
    public String strIngredient1;
    public String strIngredient2;
    public String strIngredient3;
    public String strIngredient4;
    public String strIngredient5;
    public String strIngredient6;
    public String strIngredient7;
    public String strIngredient8;
    public String strIngredient9;
    public String strIngredient10;
    public String strIngredient11;
    public String strIngredient12;
    public String strIngredient13;
    public String strIngredient14;
    public String strIngredient15;
    public String strIngredient16;
    public String strIngredient17;
    public String strIngredient18;
    public String strIngredient19;
    public String strIngredient20;
    public String strMeasure1;
    public String strMeasure2;
    public String strMeasure3;
    public String strMeasure4;
    public String strMeasure5;
    public String strMeasure6;
    public String strMeasure7;
    public String strMeasure8;
    public String strMeasure9;
    public String strMeasure10;
    public String strMeasure11;
    public String strMeasure12;
    public String strMeasure13;
    public String strMeasure14;
    public String strMeasure15;
    public String strMeasure16;
    public String strMeasure17;
    public String strMeasure18;
    public String strMeasure19;
    public String strMeasure20;
    public String strSource;
    public String strImageSource;
    public String strCreativeCommonsConfirmed;
    public String dateModified;
    public String dateAndTime;

    public Meal toMeal() {
        Meal meal = new Meal();

        meal.idMeal = this.idMeal;
        meal.strMeal = this.strMeal;
        meal.strDrinkAlternate = this.strDrinkAlternate;
        meal.strCategory = this.strCategory;
        meal.strArea = this.strArea;
        meal.strInstructions = this.strInstructions;
        meal.strMealThumb = this.strMealThumb;
        meal.strTags = this.strTags;
        meal.strYoutube = this.strYoutube;
        meal.strIngredient1 = this.strIngredient1;
        meal.strIngredient2 = this.strIngredient2;
        meal.strIngredient3 = this.strIngredient3;
        meal.strIngredient4 = this.strIngredient4;
        meal.strIngredient5 = this.strIngredient5;
        meal.strIngredient6 = this.strIngredient6;
        meal.strIngredient7 = this.strIngredient7;
        meal.strIngredient8 = this.strIngredient8;
        meal.strIngredient9 = this.strIngredient9;
        meal.strIngredient10 = this.strIngredient10;
        meal.strIngredient11 = this.strIngredient11;
        meal.strIngredient12 = this.strIngredient12;
        meal.strIngredient13 = this.strIngredient13;
        meal.strIngredient14 = this.strIngredient14;
        meal.strIngredient15 = this.strIngredient15;
        meal.strIngredient16 = this.strIngredient16;
        meal.strIngredient17 = this.strIngredient17;
        meal.strIngredient18 = this.strIngredient18;
        meal.strIngredient19 = this.strIngredient19;
        meal.strIngredient20 = this.strIngredient20;
        meal.strMeasure1 = this.strMeasure1;
        meal.strMeasure2 = this.strMeasure2;
        meal.strMeasure3 = this.strMeasure3;
        meal.strMeasure4 = this.strMeasure4;
        meal.strMeasure5 = this.strMeasure5;
        meal.strMeasure6 = this.strMeasure6;
        meal.strMeasure7 = this.strMeasure7;
        meal.strMeasure8 = this.strMeasure8;
        meal.strMeasure9 = this.strMeasure9;
        meal.strMeasure10 = this.strMeasure10;
        meal.strMeasure11 = this.strMeasure11;
        meal.strMeasure12 = this.strMeasure12;
        meal.strMeasure13 = this.strMeasure13;
        meal.strMeasure14 = this.strMeasure14;
        meal.strMeasure15 = this.strMeasure15;
        meal.strMeasure16 = this.strMeasure16;
        meal.strMeasure17 = this.strMeasure17;
        meal.strMeasure18 = this.strMeasure18;
        meal.strMeasure19 = this.strMeasure19;
        meal.strMeasure20 = this.strMeasure20;
        meal.strSource = this.strSource;
        meal.strImageSource = this.strImageSource;
        meal.strCreativeCommonsConfirmed = this.strCreativeCommonsConfirmed;
        meal.dateModified = this.dateModified;
        meal.dateAndTime = this.dateAndTime;

        return meal;
    }
}
