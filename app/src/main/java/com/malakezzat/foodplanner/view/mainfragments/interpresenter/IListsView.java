package com.malakezzat.foodplanner.view.mainfragments.interpresenter;

import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Meal;

import java.util.List;

public interface IListsView {
    void getCategoriesList(List<Category> categories);
    void getCountriesList(List<Meal> countries);
    void getError(String msg);
}
