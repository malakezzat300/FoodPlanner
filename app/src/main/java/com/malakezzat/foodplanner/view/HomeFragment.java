package com.malakezzat.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.malakezzat.foodplanner.R;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Sample data
        List<String> dataList = Arrays.asList("Card 1", "Card 2", "Card 3", "Card 4", "Card 5");

        // Find RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.maskableFrameLayout);

        // Use CarouselLayoutManager instead of LinearLayoutManager
        CarouselLayoutManager layoutManager = new CarouselLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter
        CarouselAdapter adapter = new CarouselAdapter(dataList);
        recyclerView.setAdapter(adapter);

        // Attach CarouselSnapHelper
        CarouselSnapHelper snapHelper = new CarouselSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}