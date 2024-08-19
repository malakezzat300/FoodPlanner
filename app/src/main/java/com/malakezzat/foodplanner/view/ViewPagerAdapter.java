package com.malakezzat.foodplanner.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.malakezzat.foodplanner.view.mainfragments.fragments.FavoriteFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.HomeFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.ListsFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.SearchFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.WeekPlanFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new ListsFragment();
            case 3:
                return new FavoriteFragment();
            case 4:
                return new WeekPlanFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5; // Number of fragments
    }
}
