package com.malakezzat.foodplanner.model.data;

public class Ingredient {
    String ingredient;
    String measure;
    String imageUrl;

    public Ingredient(){
    }

    public Ingredient(String ingredient,String measure){
        this.ingredient = ingredient;
        this.measure = measure;
        this.imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String ingredient) {
        this.imageUrl = "www.themealdb.com/images/ingredients/" + ingredient + ".png";
    }
}
