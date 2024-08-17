package com.malakezzat.foodplanner.view.mainfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.ProductLocalDataSource;
import com.malakezzat.foodplanner.model.local.ProductLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.FavPresenter;
import com.malakezzat.foodplanner.presenter.interview.IFavPresenter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IFavView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnFavListener;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements IFavView, OnFavListener {

    List<MealDB> mealDBS;
    Context context;
    RecyclerView recyclerView;
    FavAdapter recyclerAdapter;
    LinearLayoutManager layoutManager;
    IFavPresenter iFavPresenter;
    public FavoriteFragment() {
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        mealDBS = new ArrayList<>();
        recyclerView = view.findViewById(R.id.fav_recycler_view);
        layoutManager = new LinearLayoutManager(context);
        iFavPresenter = new FavPresenter(this,new ProductLocalDataSourceImpl(AppDatabase.getInstance(context)));
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new FavAdapter(context,mealDBS,this);
        recyclerView.setAdapter(recyclerAdapter);

        iFavPresenter.getStoredMeals().observe(getViewLifecycleOwner(), MealDB -> {
            recyclerAdapter = new FavAdapter(context, MealDB, this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerAdapter);
        });


    }

    @Override
    public void getStoredMeals(List<MealDB> meals) {
        recyclerAdapter.setMeals(meals);
    }

    @Override
    public void onClickRemoveMeal(MealDB mealDB) {
        iFavPresenter.removeFromFav(mealDB);
    }
}