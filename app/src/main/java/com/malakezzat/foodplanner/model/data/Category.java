package com.malakezzat.foodplanner.model.data;

public class Category implements Data{
    public String idCategory;
    public String strCategory;
    public String strCategoryThumb;
    public String strCategoryDescription;

    @Override
    public String toString() {
        return "Category{" +
                "idCategory='" + idCategory + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strCategoryThumb='" + strCategoryThumb + '\'' +
                ", strCategoryDescription='" + strCategoryDescription + '\'' +
                '}';
    }
}
