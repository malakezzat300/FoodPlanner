package com.malakezzat.foodplanner.view.mainfragments.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.model.local.MealDBWeek;
import com.malakezzat.foodplanner.presenter.WeekPlanPresenter;
import com.malakezzat.foodplanner.presenter.interview.IWeekPlanPresenter;
import com.malakezzat.foodplanner.view.mainfragments.MealDetailsFragment;
import com.malakezzat.foodplanner.view.mainfragments.adapters.WeekPlanAdapter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IWeekPlanView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;

public class WeekPlanFragment extends Fragment implements IWeekPlanView , OnMealClickListener {

    private static final String TAG = "WeekPlanFragment";
    List<MealDBWeek> mealDBS;
    Context context;
    RecyclerView recyclerView;
    WeekPlanAdapter recyclerAdapter;
    LinearLayoutManager layoutManager;
    IWeekPlanPresenter iWeekPlanPresenter;
    public WeekPlanFragment() {
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
        return inflater.inflate(R.layout.fragment_week_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        mealDBS = new ArrayList<>();
        recyclerView = view.findViewById(R.id.week_plan_recycler);
        layoutManager = new LinearLayoutManager(context);
        iWeekPlanPresenter = new WeekPlanPresenter(this,new MealLocalDataSourceImpl(AppDatabase.getInstance(context)));
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new WeekPlanAdapter(context,mealDBS,this);
        recyclerView.setAdapter(recyclerAdapter);

        iWeekPlanPresenter.getWeekPlanMeals().observe(getViewLifecycleOwner(), MealDB -> {
            Log.i(TAG, "onViewCreated: "+ MealDB.get(0).idMeal );
            recyclerAdapter = new WeekPlanAdapter(context, MealDB, this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerAdapter);
        });
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
        iWeekPlanPresenter.removeFromWeekPlan(meal.toMealDB());
    }

    @Override
    public void addToFav(String Id, int modeSave) {

    }

    @Override
    public void removeFromFav(String Id, int modeSave) {

    }

    @Override
    public void addToWeekPlan(Meal meal) {

    }

    @Override
    public void addToWeekPlan(String id, int saveMode) {

    }

    @Override
    public void getMealById(String Id) {

    }
}