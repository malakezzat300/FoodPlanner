package com.malakezzat.foodplanner.view.mainfragments.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.Remote.MealRemoteDataSourceImpl;
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.repository.MealRepositoryImpl;
import com.malakezzat.foodplanner.presenter.ListsPresenter;
import com.malakezzat.foodplanner.presenter.interview.IListsPresenter;
import com.malakezzat.foodplanner.view.OnItemSelectedListener;
import com.malakezzat.foodplanner.view.mainfragments.adapters.ListsAdapter;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IListsView;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnListsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListsFragment extends Fragment implements IListsView , OnListsListener {

    ChipGroup chipGroup;
    Chip categoriesChip,countriesChip;
    RecyclerView recyclerView;
    IListsPresenter iListsPresenter;
    Context context;
    List<Meal> countries;
    List<Category> categories;
    LinearLayoutManager layoutManager;
    ListsAdapter recyclerAdapter;
    ViewPager viewPager;
    FragmentTransaction fragmentTransaction;
    private OnItemSelectedListener listener;

    public ListsFragment() {
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
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chipGroup = view.findViewById(R.id.chipGroup);
        countriesChip = view.findViewById(R.id.countries_list);
        categoriesChip = view.findViewById(R.id.categories_list);
        recyclerView = view.findViewById(R.id.lists_recycler_view);
        context = view.getContext();
        iListsPresenter = new ListsPresenter(this,new MealRepositoryImpl(new MealRemoteDataSourceImpl()));
        countries = new ArrayList<>();
        categories = new ArrayList<>();
        viewPager = requireActivity().findViewById(R.id.viewPager);
        fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAdapter = new ListsAdapter(context,categories,this,fragmentTransaction);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        countriesChip.setOnClickListener(v->{
            iListsPresenter.getCountriesList();
        });

        categoriesChip.setOnClickListener(v->{
            iListsPresenter.getCategoriesList();
        });
        iListsPresenter.getCategoriesList();
    }

    @Override
    public void getCategoriesList(List<Category> categories) {
        this.categories = categories;
        recyclerAdapter = new ListsAdapter(context,categories,this,fragmentTransaction);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void getCountriesList(List<Meal> countries) {
        this.countries = countries;
        recyclerAdapter = new ListsAdapter(context,countries,this,fragmentTransaction);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void getError(String msg) {
        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClicked(Category category) {
        if (listener != null) {
            listener.onItemSelected(category);
        }
    }

    @Override
    public void onListItemClicked(Meal meal) {
        if (listener != null) {
            listener.onItemSelected(meal);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}