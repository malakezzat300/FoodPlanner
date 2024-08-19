package com.malakezzat.foodplanner.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.view.mainfragments.fragments.UserFragment;

import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView navView;
    boolean doubleBackToExitPressedOnce = false;
    TextView helloUserText;
    ImageView userImage;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    private FirebaseAuth auth;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        navView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        helloUserText = findViewById(R.id.hello_user);
        userImage = findViewById(R.id.userImage);
        userImage.setImageResource(R.drawable.account_circle);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            String username = user.getDisplayName();
            helloUserText.setText("Hello " + getFirstWordCapitalized(username));
            Uri profileImage = user.getPhotoUrl();
            Glide.with(getApplicationContext()).load(profileImage)
                    .apply(new RequestOptions().override(200,200))
                    .placeholder(R.drawable.account_circle)
                    .into(userImage);
        }

        // Initialize the adapter with FragmentManager and behavior
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        // Set up the Bottom Navigation View
        navView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.navigation_search) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.navigation_lists) {
                viewPager.setCurrentItem(2);
                return true;
            } else if (itemId == R.id.navigation_favorite) {
                viewPager.setCurrentItem(3);
                return true;
            }

            return false;
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Optional: Add logic for when the page is being scrolled.
            }

            @Override
            public void onPageSelected(int position) {
                // Sync the BottomNavigationView with the ViewPager.
                if (position == 0) {
                    navView.setSelectedItemId(R.id.navigation_home);
                } else if (position == 1) {
                    navView.setSelectedItemId(R.id.navigation_search);
                } else if (position == 2) {
                    navView.setSelectedItemId(R.id.navigation_lists);
                } else if (position == 3) {
                    navView.setSelectedItemId(R.id.navigation_favorite);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Optional: Add logic for when the scroll state changes.
            }
        });

        userImage.setOnClickListener(v->{
            UserFragment userFragment = new UserFragment();
            userFragment.show(getSupportFragmentManager(), userFragment.getTag());
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

    public String getFirstWordCapitalized(String input) {
        String[] words = input.split(" ");
        if (words.length > 0) {
            String firstWord = words[0];
            String capitalizedWord = firstWord.substring(0, 1).toUpperCase() + firstWord.substring(1).toLowerCase();
            return capitalizedWord;
        }
        return "";
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                Glide.with(getApplicationContext()).load(user.getPhotoUrl())
                        .apply(new RequestOptions().override(200, 200))
                        .placeholder(R.drawable.account_circle)
                        .into(userImage);
            }
        }
    }
}
