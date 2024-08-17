package com.malakezzat.foodplanner.view.mainfragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.malakezzat.foodplanner.model.Remote.*;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSource;
import com.malakezzat.foodplanner.model.Remote.ProductRemoteDataSourceImpl;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.data.MealList;
import com.malakezzat.foodplanner.presenter.HomePresenter;
import com.malakezzat.foodplanner.presenter.interview.IHomePresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnHomeListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements IHomeView, OnHomeListener {

    RecyclerView recyclerView;
    List<String> dataList;
    CarouselLayoutManager layoutManager;
    CarouselAdapter adapter;
    CarouselSnapHelper snapHelper;
    List<Meal> mealList;
    Context context;
    IHomePresenter iHomePresenter;
    ProductRemoteDataSource productRemoteDataSource;
    public final static String MEALLIST = "mealList";
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

        context = view.getContext();
        mealList = new ArrayList<>();
        productRemoteDataSource = new ProductRemoteDataSourceImpl();
        iHomePresenter = new HomePresenter(this,productRemoteDataSource);

        if (savedInstanceState != null) {
            mealList = savedInstanceState.getParcelableArrayList(MEALLIST);
        } else {
            iHomePresenter.getMeals();
        }
        recyclerView = view.findViewById(R.id.maskableFrameLayout);

        layoutManager = new CarouselLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CarouselAdapter(context,mealList,this);
        recyclerView.setAdapter(adapter);

        snapHelper = new CarouselSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MEALLIST, new ArrayList<>(mealList));
    }

    @Override
    public void getMeals(List<Meal> mealList) {
        Log.i(TAG, "getMeals: " + mealList.get(0));
        this.mealList = mealList;
        adapter = new CarouselAdapter(context, mealList,this);
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
        iHomePresenter.addToFav(meal);
    }
}