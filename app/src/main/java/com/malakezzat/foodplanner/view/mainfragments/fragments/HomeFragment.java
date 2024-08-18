package com.malakezzat.foodplanner.view.mainfragments.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSourceImpl;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.ProductLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.HomePresenter;
import com.malakezzat.foodplanner.presenter.interview.IHomePresenter;
import com.malakezzat.foodplanner.view.mainfragments.adapters.CarouselAdapter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment implements IHomeView, OnMealClickListener {

    RecyclerView recyclerView;
    List<String> dataList;
    CarouselLayoutManager layoutManager;
    CarouselAdapter adapter;
    CarouselSnapHelper snapHelper;
    List<Meal> mealList;
    Context context;
    IHomePresenter iHomePresenter;
    boolean isFav;
    TextView mealTitle,mealCountry;
    ImageView favButton,mealImage;
    Meal mealOfDay;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String MealOfDayId;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_SAVED_TIME = "saved_time";
    public final static String MEALLIST = "mealList";
    public final static String MEAL_OF_DAY = "meal";
    public final static String MEAL_OF_DAY_ID = "mealId";
    private final static String TAG ="HomeFragment";


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealOfDay = new Meal();
        mealImage = view.findViewById(R.id.cardImage);
        mealCountry = view.findViewById(R.id.cardCountry);
        mealTitle = view.findViewById(R.id.cardTitle);
        favButton = view.findViewById(R.id.fav_button);
        context = view.getContext();
        mealList = new ArrayList<>();
        iHomePresenter = new HomePresenter(this,new ProductRemoteDataSourceImpl(),new ProductLocalDataSourceImpl(AppDatabase.getInstance(context)));
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        long savedTime = sharedPreferences.getLong(KEY_SAVED_TIME, 0);
        MealOfDayId = sharedPreferences.getString(MEAL_OF_DAY_ID, "");
        if(savedTime == 0){
            saveCurrentTimeToPreferences();
        }

        if (savedInstanceState != null) {
            mealList = savedInstanceState.getParcelableArrayList(MEALLIST);
            mealOfDay = savedInstanceState.getParcelable(MEAL_OF_DAY);
            updateMealUI(mealOfDay);
        } else {
            iHomePresenter.getMeals();
            getMealOfDay(savedTime,MealOfDayId);
        }
        recyclerView = view.findViewById(R.id.maskableFrameLayout);

        layoutManager = new CarouselLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CarouselAdapter(context,mealList,this,CarouselAdapter.HOME_FRAGMENT);
        recyclerView.setAdapter(adapter);

        snapHelper = new CarouselSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);



        favButton.setOnClickListener(v -> {
            if(isFav){
                favButton.setImageResource(R.drawable.favorite_border);
                this.removeFromFav(mealOfDay);
                isFav = true;
            } else {
                favButton.setImageResource(R.drawable.favorite_red);
                this.addToFav(mealOfDay);
                isFav = false;
            }
        });
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MEALLIST, new ArrayList<>(mealList));
        outState.putParcelable(MEAL_OF_DAY, mealOfDay);
    }

    @Override
    public void getMeal(List<Meal> meal) {
        mealOfDay = meal.get(0);
        Glide.with(context).load(mealOfDay.strMealThumb)
                .apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.new_logo3)
                .into(mealImage);
        String mealArea = getString(R.string.country) + " : "+ mealOfDay.strArea;
        mealTitle.setText(mealOfDay.strMeal);
        mealCountry.setText(mealArea);
        editor = sharedPreferences.edit();
        editor.putString(MEAL_OF_DAY_ID,mealOfDay.idMeal);
        editor.apply();
    }

    @Override
    public void getMeals(List<Meal> mealList) {
        Log.i(TAG, "getMeals: " + mealList.get(0));
        this.mealList = mealList;
        adapter = new CarouselAdapter(context, mealList,this,CarouselAdapter.HOME_FRAGMENT);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getError(String msg) {
        Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addToFav(Meal meal) {
        iHomePresenter.addToFav(meal);
    }

    @Override
    public void removeFromFav(Meal meal) {
        iHomePresenter.removeFromFav(meal);
    }

    private void saveCurrentTimeToPreferences() {
        long currentTime = System.currentTimeMillis();
        editor = sharedPreferences.edit();
        editor.putLong(KEY_SAVED_TIME, currentTime);
        editor.apply();
    }

    private void getMealOfDay(long savedTime,String id) {
        boolean isOld = System.currentTimeMillis() - savedTime < TimeUnit.DAYS.toMillis(1);
        if (isOld && (!id.isEmpty())) {
            Toast.makeText(getContext(), "Old", Toast.LENGTH_SHORT).show();
            iHomePresenter.getMealById(id);
        } else {
            Toast.makeText(getContext(), "New", Toast.LENGTH_SHORT).show();
            iHomePresenter.getMeal();
            saveCurrentTimeToPreferences();
        }
    }
    private void updateMealUI(Meal meal) {
        mealOfDay = meal;
        Glide.with(context).load(mealOfDay.strMealThumb)
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.new_logo3)
                .into(mealImage);
        String mealArea = getString(R.string.country) + " : " + mealOfDay.strArea;
        mealTitle.setText(mealOfDay.strMeal);
        mealCountry.setText(mealArea);
    }
}