package com.malakezzat.foodplanner.view;

import static com.malakezzat.foodplanner.view.MainActivity.CATEGORIES;
import static com.malakezzat.foodplanner.view.MainActivity.COUNTRIES;
import static com.malakezzat.foodplanner.view.MainActivity.DATA_TYPE;
import static com.malakezzat.foodplanner.view.MainActivity.MEALS_TITLE;
import static com.malakezzat.foodplanner.view.MainActivity.getFirstWordCapitalized;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSourceImpl;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.SearchPresenter;
import com.malakezzat.foodplanner.presenter.interview.ISearchPresenter;
import com.malakezzat.foodplanner.view.mainfragments.MealDetailsFragment;
import com.malakezzat.foodplanner.view.mainfragments.adapters.SearchAdapter;
import com.malakezzat.foodplanner.view.mainfragments.fragments.SearchFragment;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.ISearchView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;

public class MealsActivity extends AppCompatActivity implements ISearchView, OnMealClickListener {
    Intent intent;
    TextView mealsTitle;
    private static final String TAG = "MealsActivity";
    ISearchPresenter iSearchPresenter;
    LinearLayoutManager layoutManager;
    SearchAdapter recyclerAdapter;
    RecyclerView recyclerView;
    String dateTime,country,category;
    int dataType;
    private FirebaseAuth auth;
    TextView helloUserText;
    ImageView userImage;
    Context context;
    List<Meal> meals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        intent = getIntent();
        meals = new ArrayList<>();
        context = getApplicationContext();
        mealsTitle = findViewById(R.id.title_meals);
        iSearchPresenter = new SearchPresenter(this
                ,new MealRemoteDataSourceImpl()
                , new MealLocalDataSourceImpl(AppDatabase.getInstance(this)));
        dateTime = "Today 1-1-2000";
        dataType = intent.getIntExtra(DATA_TYPE,0);


        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        helloUserText = findViewById(R.id.hello_user);
        userImage = findViewById(R.id.userImage);
        userImage.setImageResource(R.drawable.account_circle);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            String username = user.getDisplayName();
            String hello;
            if(!username.isEmpty() && username != null){
                hello = getString(R.string.hello) + getFirstWordCapitalized(username);
            } else {
                hello = getString(R.string.hello_guest);
            }
            helloUserText.setText(hello);
            Uri profileImage = user.getPhotoUrl();
            Glide.with(getApplicationContext()).load(profileImage)
                    .apply(new RequestOptions().override(200,200))
                    .placeholder(R.drawable.account_circle)
                    .into(userImage);

            Log.i(TAG, "onCreate: " + user.getUid());
        }


        recyclerView = findViewById(R.id.recycler_view_meals);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAdapter = new SearchAdapter(this,meals, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);


        if(dataType == 0){
            Toast.makeText(this, "Error the item is not category or country", Toast.LENGTH_SHORT).show();
        } else if(dataType == COUNTRIES){
            country = intent.getStringExtra(MEALS_TITLE);
            mealsTitle.setText(getString(R.string.country) + " : " +country);
            iSearchPresenter.searchByCountry(country);
        } else if(dataType == CATEGORIES){
            category = intent.getStringExtra(MEALS_TITLE);
            mealsTitle.setText(getString(R.string.category) + " : " + category);
            iSearchPresenter.searchByCategory(category);
        }



    }

    @Override
    public void getCountryList(List<Meal> countryList) {

    }

    @Override
    public void getIngredientList(List<Meal> ingredientList) {

    }

    @Override
    public void getCategoryList(List<Meal> categoryList) {

    }

    @Override
    public void getMealList(List<Meal> mealList) {
        meals = mealList;
        recyclerAdapter = new SearchAdapter(this,meals,this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void getError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
            mealDetailsFragment.show(getSupportFragmentManager(),mealDetailsFragment.getTag());
        }
    }

    @Override
    public void showMealDetails(Meal meal) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(meal,this);
        mealDetailsFragment.show(getSupportFragmentManager(),mealDetailsFragment.getTag());
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
}