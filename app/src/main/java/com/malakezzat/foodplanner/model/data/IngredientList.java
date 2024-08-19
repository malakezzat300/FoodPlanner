package com.malakezzat.foodplanner.model.data;

import java.util.ArrayList;
import java.util.List;

public class IngredientList {
    public List<Ingredient> ingredients = new ArrayList<>();

    public IngredientList(Meal meal){
        if(!meal.strIngredient1.isEmpty() && !meal.strMeasure1.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient1, meal.strMeasure1));
        }
        if(!meal.strIngredient2.isEmpty() && !meal.strMeasure2.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient2, meal.strMeasure2));
        }
        if(!meal.strIngredient3.isEmpty() && !meal.strMeasure3.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient3, meal.strMeasure3));
        }
        if(!meal.strIngredient4.isEmpty() && !meal.strMeasure4.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient4, meal.strMeasure4));
        }
        if(!meal.strIngredient5.isEmpty() && !meal.strMeasure5.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient5, meal.strMeasure5));
        }
        if(!meal.strIngredient6.isEmpty() && !meal.strMeasure6.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient6, meal.strMeasure6));
        }
        if(!meal.strIngredient7.isEmpty() && !meal.strMeasure7.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient7, meal.strMeasure7));
        }
        if(!meal.strIngredient8.isEmpty() && !meal.strMeasure8.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient8, meal.strMeasure8));
        }
        if(!meal.strIngredient9.isEmpty() && !meal.strMeasure9.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient9, meal.strMeasure9));
        }
        if(!meal.strIngredient10.isEmpty() && !meal.strMeasure10.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient10, meal.strMeasure10));
        }
        if(!meal.strIngredient11.isEmpty() && !meal.strMeasure11.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient11, meal.strMeasure11));
        }
        if(!meal.strIngredient12.isEmpty() && !meal.strMeasure12.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient12, meal.strMeasure12));
        }
        if(!meal.strIngredient13.isEmpty() && !meal.strMeasure13.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient13, meal.strMeasure13));
        }
        if(!meal.strIngredient14.isEmpty() && !meal.strMeasure14.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient14, meal.strMeasure14));
        }
        if(!meal.strIngredient15.isEmpty() && !meal.strMeasure15.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient15, meal.strMeasure15));
        }
        if(!meal.strIngredient16.isEmpty() && !meal.strMeasure16.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient16, meal.strMeasure16));
        }
        if(!meal.strIngredient17.isEmpty() && !meal.strMeasure17.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient17, meal.strMeasure17));
        }
        if(!meal.strIngredient18.isEmpty() && !meal.strMeasure18.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient18, meal.strMeasure18));
        }
        if(!meal.strIngredient19.isEmpty() && !meal.strMeasure19.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient19, meal.strMeasure19));
        }
        if(!meal.strIngredient20.isEmpty() && !meal.strMeasure20.equals(" ")){
            ingredients.add(new Ingredient(meal.strIngredient20, meal.strMeasure20));
        }
    }
}
