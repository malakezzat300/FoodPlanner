package com.malakezzat.foodplanner.view.mainfragments.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSourceImpl;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.SearchPresenter;
import com.malakezzat.foodplanner.presenter.interview.ISearchPresenter;
import com.malakezzat.foodplanner.view.mainfragments.MealDetailsFragment;
import com.malakezzat.foodplanner.view.mainfragments.adapters.SearchAdapter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements ISearchView, OnMealClickListener {

    private static final String TAG = "SearchFragment";
    private static final String SELECTED_RADIO_BUTTON = "radioButton";
    private static final String SELECTED_SPINNER_ITEM = "spinnerItem";
    private static final String SELECTED_SPINNER_ITEM_ID = "spinnerItemId";
    int spinnerItemId;
    String spinnerItemString;
    RadioGroup radioGroup;
    RadioButton countryRadioButton, ingredientRadioButton,categoryRadioButton;
    Spinner itemSpinner;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    List<Meal> items;
    List<Meal> meals;
    List<Meal> mealsByName;
    List<String> countryItemList;
    List<String> ingredientItemList;
    List<String> categoryItemList;
    Context context;
    ISearchPresenter iSearchPresenter;
    ArrayAdapter<String> adapter;
    int selectedRadioButton;
    LinearLayoutManager layoutManager;
    SearchAdapter recyclerAdapter;
    RecyclerView recyclerView;
    String dateTime;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup =view.findViewById(R.id.radioGroup);
        countryRadioButton = view.findViewById(R.id.country_radio_button);
        ingredientRadioButton = view.findViewById(R.id.ingredient_radio_button);
        categoryRadioButton = view.findViewById(R.id.category_radio_button);
        itemSpinner = view.findViewById(R.id.spinner);
        textInputLayout = view.findViewById(R.id.textInputLayoutSearch);
        autoCompleteTextView= view.findViewById(R.id.auto_complete_edit_text);
        context = view.getContext();
        iSearchPresenter = new SearchPresenter(this
                ,new MealRemoteDataSourceImpl()
                , new MealLocalDataSourceImpl(AppDatabase.getInstance(context)));
        countryItemList = new ArrayList<>();
        ingredientItemList = new ArrayList<>();
        categoryItemList = new ArrayList<>();
        meals = new ArrayList<>();
        mealsByName = new ArrayList<>();
        dateTime = "Today 1-1-2000";


        recyclerView = view.findViewById(R.id.search_recycler_view);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAdapter = new SearchAdapter(context,meals,SearchFragment.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        if(savedInstanceState != null){
            selectedRadioButton = savedInstanceState.getInt(SELECTED_RADIO_BUTTON);
            spinnerItemId = savedInstanceState.getInt(SELECTED_SPINNER_ITEM_ID);
            if (selectedRadioButton == R.id.country_radio_button) {
                countryRadioButton.setChecked(true);
            } else if (selectedRadioButton == R.id.ingredient_radio_button) {
                ingredientRadioButton.setChecked(true);
            } else if (selectedRadioButton == R.id.category_radio_button) {
                categoryRadioButton.setChecked(true);
            }
            updateSpinnerItems(selectedRadioButton);
            spinnerItemString = savedInstanceState.getString(SELECTED_SPINNER_ITEM);
            if (selectedRadioButton == R.id.country_radio_button) {
                iSearchPresenter.searchByCountry(spinnerItemString);
            } else if (selectedRadioButton == R.id.ingredient_radio_button) {
                iSearchPresenter.searchByIngredient(spinnerItemString);
            } else if (selectedRadioButton == R.id.category_radio_button) {
                iSearchPresenter.searchByCategory(spinnerItemString);
            }
        } else {
            selectedRadioButton = R.id.country_radio_button;
            countryRadioButton.setChecked(true);
            updateSpinnerItems(selectedRadioButton);
            spinnerItemString = "American";
            iSearchPresenter.searchByCountry(spinnerItemString);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            selectedRadioButton = checkedId;
            spinnerItemId = 0;
            updateSpinnerItems(selectedRadioButton);
            if (selectedRadioButton == R.id.country_radio_button) {
                iSearchPresenter.searchByCountry("American");
            } else if (selectedRadioButton == R.id.ingredient_radio_button) {
                iSearchPresenter.searchByIngredient("Chicken");
            } else if (selectedRadioButton == R.id.category_radio_button) {
                iSearchPresenter.searchByCategory("Beef");
            }
        });

        autoCompleteTextView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = autoCompleteTextView.getText().toString().toLowerCase();
                mealsByName.clear();
                if (!meals.isEmpty()) {
                    for(Meal meal : meals){
                        if(meal.strMeal.toLowerCase().contains(query)){
                               mealsByName.add(meal);
                        }
                    }
                    recyclerAdapter = new SearchAdapter(context,mealsByName,SearchFragment.this);
                    recyclerView.setAdapter(recyclerAdapter);

                }
                autoCompleteTextView.clearFocus();
                InputMethodManager in = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                return true; // Indicate that you've handled the action
            }
            autoCompleteTextView.clearFocus();
            InputMethodManager in = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
            return false; // Let other listeners handle it
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need to implement this method for your use case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                mealsByName.clear();
                if (!meals.isEmpty()) {
                    for (Meal meal : meals) {
                        if (meal.strMeal.toLowerCase().contains(query)) {
                            mealsByName.add(meal);
                        }
                    }

                    // Update the adapter with new filtered meals
                    recyclerAdapter = new SearchAdapter(context, mealsByName, SearchFragment.this);
                    recyclerView.setAdapter(recyclerAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need to implement this method for your use case
            }
        });

        itemSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerItemString = itemSpinner.getItemAtPosition(position).toString();
                            spinnerItemId = position;
                            if (selectedRadioButton == R.id.country_radio_button) {
                                iSearchPresenter.searchByCountry(spinnerItemString);
                            } else if (selectedRadioButton == R.id.ingredient_radio_button) {
                                iSearchPresenter.searchByIngredient(spinnerItemString);
                            } else if (selectedRadioButton == R.id.category_radio_button) {
                                iSearchPresenter.searchByCategory(spinnerItemString);
                            }
                            Log.i("Spinner", "Item clicked: " + spinnerItemString);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Handle the case where nothing is selected
                        }
                    });
                }
                return false;
            }
        });



    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_RADIO_BUTTON,selectedRadioButton);
        outState.putString(SELECTED_SPINNER_ITEM,spinnerItemString);
        outState.putInt(SELECTED_SPINNER_ITEM_ID,spinnerItemId);
    }

    private void updateSpinnerItems(int checkedId) {
        items = new ArrayList<>();

        if (checkedId == R.id.country_radio_button) {
            iSearchPresenter.getCountryList();
        } else if (checkedId == R.id.ingredient_radio_button) {
            iSearchPresenter.getIngredientList();
        } else if (checkedId == R.id.category_radio_button) {
            iSearchPresenter.getCategoryList();
        }

    }

    @Override
    public void getCountryList(List<Meal> countryList) {
        items = countryList;
        for(Meal m : items){
            Log.i(TAG, "getCountryList: " + m.strArea);
            countryItemList.add(m.strArea);
        }
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, countryItemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);
        itemSpinner.setSelection(spinnerItemId);
    }

    @Override
    public void getIngredientList(List<Meal> ingredientList) {
        items = ingredientList;
        for(Meal m : items){
            ingredientItemList.add(m.strIngredient);
        }
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, ingredientItemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);
        itemSpinner.setSelection(spinnerItemId);
    }

    @Override
    public void getCategoryList(List<Meal> categoryList) {
        items = categoryList;
        for(Meal m : items){
            categoryItemList.add(m.strCategory);
        }
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categoryItemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);
        itemSpinner.setSelection(spinnerItemId);
    }

    @Override
    public void getMealList(List<Meal> mealList) {
        meals = mealList;
        recyclerAdapter = new SearchAdapter(context,meals,SearchFragment.this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void getError(String msg) {
        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMealById(Meal meal, int modeSave) {
        if(modeSave == 1){
            iSearchPresenter.addToFav(meal);
        } else if (modeSave == 2){
            iSearchPresenter.removeFromFav(meal);
        } else if (modeSave == 3){
            meal.dateAndTime = dateTime;
            iSearchPresenter.addToWeekPlan(meal);
        } else {
            MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(meal,this);
            mealDetailsFragment.show(getParentFragmentManager(),mealDetailsFragment.getTag());
        }

    }

    @Override
    public void showMealDetails(Meal meal) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(meal,this);
        mealDetailsFragment.show(getParentFragmentManager(),mealDetailsFragment.getTag());
    }

    @Override
    public void addToFav(Meal meal) {

    }

    @Override
    public void removeFromFav(Meal meal) {

    }

    @Override
    public void addToFav(String Id, int saveMode) {
        iSearchPresenter.getMealById(Id,saveMode);
    }

    @Override
    public void removeFromFav(String Id, int saveMode) {
        iSearchPresenter.getMealById(Id,saveMode);
    }

    @Override
    public void addToWeekPlan(Meal meal) {
        dateTime = meal.dateAndTime;
    }

    @Override
    public void addToWeekPlan(String id, int saveMode) {
        iSearchPresenter.getMealById(id,saveMode);
    }

    @Override
    public void getMealById(String Id) {
        iSearchPresenter.getMealById(Id);
    }

    public void updateData(Boolean data) {
        Log.i("FavTest", "updateData: " + data);
        recyclerAdapter.updateData(data);
        Log.d("TargetFragment", "Received data: search " + data);
    }


}