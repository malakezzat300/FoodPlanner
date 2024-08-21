package com.malakezzat.foodplanner.view;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.malakezzat.foodplanner.view.mainfragments.fragments.FavoriteFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.HomeFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.ListsFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.SearchFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.WeekPlanFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    FirebaseUser user;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(user != null && user.isAnonymous()){
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new SearchFragment();
                case 2:
                    return new ListsFragment();
                default:
                    return new HomeFragment();
            }
        } else {
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
    }

    @Override
    public int getCount() {
        if(user != null && user.isAnonymous()){
            return 3;
        } else {
            return 5;
        }
    }
}
