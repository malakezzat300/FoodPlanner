package com.malakezzat.foodplanner.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.malakezzat.foodplanner.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView navView;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // This line connects the BottomNavigationView with the NavController
        NavigationUI.setupWithNavController(navView, navController);

        navView.setOnNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.navigation_search) {
                navController.navigate(R.id.searchFragment);
            } else if (itemId == R.id.navigation_lists) {
                navController.navigate(R.id.listsFragment);
            } else if (itemId == R.id.navigation_favorite) {
                navController.navigate(R.id.favoriteFragment);
            }


            return true;
        });

    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
