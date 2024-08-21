package com.malakezzat.foodplanner.view.mainfragments.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSourceImpl;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.HomePresenter;
import com.malakezzat.foodplanner.presenter.interview.IHomePresenter;
import com.malakezzat.foodplanner.view.mainfragments.MealDetailsFragment;
import com.malakezzat.foodplanner.view.mainfragments.adapters.CarouselAdapter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
    ImageView favButton,weekPlanButton,mealImage;
    Meal mealOfDay;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String MealOfDayId;
    CardView mealOfDayButton;
    FirebaseUser user;
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
        mealOfDayButton = view.findViewById(R.id.meal_material2);
        mealImage = view.findViewById(R.id.cardImage);
        mealCountry = view.findViewById(R.id.cardCountry);
        mealTitle = view.findViewById(R.id.cardTitle);
        favButton = view.findViewById(R.id.fav_button);
        weekPlanButton = view.findViewById(R.id.week_plan_button);
        context = view.getContext();
        mealList = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        iHomePresenter = new HomePresenter(this, new MealRemoteDataSourceImpl(), new MealLocalDataSourceImpl(AppDatabase.getInstance(context)));
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        long savedTime = sharedPreferences.getLong(KEY_SAVED_TIME, 0);
        MealOfDayId = sharedPreferences.getString(MEAL_OF_DAY_ID, "");
        if (savedTime == 0) {
            saveCurrentTimeToPreferences();
        }

        if (savedInstanceState != null) {
            mealList = savedInstanceState.getParcelableArrayList(MEALLIST);
            mealOfDay = savedInstanceState.getParcelable(MEAL_OF_DAY);
            updateMealUI(mealOfDay);
        } else {
            iHomePresenter.getMeals();
            getMealOfDay(savedTime, MealOfDayId);
        }
        recyclerView = view.findViewById(R.id.maskableFrameLayout);

        layoutManager = new CarouselLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CarouselAdapter(context, mealList, this, CarouselAdapter.HOME_FRAGMENT);
        recyclerView.setAdapter(adapter);

        snapHelper = new CarouselSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        mealOfDayButton.setOnClickListener(v -> {
            MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(mealOfDay, this);
            mealDetailsFragment.show(getParentFragmentManager(), mealDetailsFragment.getTag());
        });

        favButton.setOnClickListener(v -> {
                if(user != null && user.isAnonymous()){
                    Toast.makeText(context, getString(R.string.fav_guest), Toast.LENGTH_SHORT).show();
                } else {
                    if (isFav) {
                        favButton.setImageResource(R.drawable.favorite_border);
                        this.removeFromFav(mealOfDay);
                        isFav = false;
                    } else {
                        favButton.setImageResource(R.drawable.favorite_red);
                        this.addToFav(mealOfDay);
                        isFav = true;
                    }
                }
        });

        weekPlanButton.setOnClickListener(v -> {
            if(user != null && user.isAnonymous()){
                Toast.makeText(context, getString(R.string.week_guest), Toast.LENGTH_SHORT).show();
            } else {
                Calendar today = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();
                maxDate.add(Calendar.DAY_OF_YEAR, 7);
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, monthOfYear, dayOfMonth);
                                String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.getTime());
                                String formattedDate = dayOfWeek + " " + String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year;
                                mealOfDay.dateAndTime = formattedDate;
                                iHomePresenter.addToWeekPlan(mealOfDay);
                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                datePickerDialog.show();
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
        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMealDetails(Meal meal) {
        MealDetailsFragment mealDetailsFragment = new MealDetailsFragment(meal,this);
        mealDetailsFragment.show(getParentFragmentManager(),mealDetailsFragment.getTag());
    }

    @Override
    public void addToFav(Meal meal) {
        iHomePresenter.addToFav(meal);
    }

    @Override
    public void removeFromFav(Meal meal) {
        iHomePresenter.removeFromFav(meal);
    }

    @Override
    public void addToFav(String Id, int modeSave) {

    }

    @Override
    public void removeFromFav(String Id, int modeSave) {

    }

    @Override
    public void addToWeekPlan(Meal meal) {
        iHomePresenter.addToWeekPlan(meal);
    }

    @Override
    public void addToWeekPlan(String id, int saveMode) {

    }

    @Override
    public void getMealById(String Id) {
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
            iHomePresenter.getMealById(id);
        } else {
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