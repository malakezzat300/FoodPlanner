package com.malakezzat.foodplanner.view.transforms;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        float pageLeft = page.getLeft();
        page.setTranslationX(-position * pageWidth);
        page.setAlpha(1 - Math.abs(position));
    }
}