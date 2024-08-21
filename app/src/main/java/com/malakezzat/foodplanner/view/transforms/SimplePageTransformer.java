package com.malakezzat.foodplanner.view.transforms;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class SimplePageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        float pageLeft = page.getLeft();

        if (position < -1) { // [-Infinity, -1)
            page.setAlpha(0);
        } else if (position <= 1) { // [-1, 1]
            page.setAlpha(1 - Math.abs(position));
            page.setTranslationX(-position * pageWidth);
        } else { // (1, +Infinity]
            page.setAlpha(0);
        }
    }
}