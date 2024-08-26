package com.malakezzat.foodplanner.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.presenter.interview.IUserPresenter;
import com.malakezzat.foodplanner.view.connectionreceiver.ConnectionListener;
import com.malakezzat.foodplanner.view.connectionreceiver.ConnectionReceiver;
import com.malakezzat.foodplanner.view.mainfragments.UserFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.HomeFragment;
import com.malakezzat.foodplanner.view.mainfragments.fragments.SearchFragment;
import com.malakezzat.foodplanner.view.mainfragments.interpresenter.IHomeView;



public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, ConnectionListener
        ,OnDataPass , SendToMain {

    private static final String TAG = "MainActivity";
    public static final String MEALS_TITLE = "title";
    public static final String DATA_TYPE = "dataType";
    public static final String CONNECTION = "connection";
    public static final String SECOND = "second";
    public static final int COUNTRIES = 1;
    public static final int CATEGORIES = 2;
    private NavController navController;
    private BottomNavigationView navView;
    boolean doubleBackToExitPressedOnce = false;
    TextView helloUserText;
    ImageView userImage;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    private FirebaseAuth auth;
    String username;
    IUserPresenter iUserPresenter;
    boolean isConnected , wasDisconnected = false,isRefreshed = false;
    ConnectionReceiver connectionReceiver;
    ConstraintLayout constraintLayout;
    TextView guestText;
    IHomeView iHomeView;
    public static int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        iHomeView = new HomeFragment();
        connectionReceiver  = new ConnectionReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionReceiver,filter, Context.RECEIVER_NOT_EXPORTED);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork == null) {
            isConnected = false;
        } else if (activeNetwork.isConnected()){
            isConnected = true;
        }
        iHomeView.isConnected(isConnected);
        if(user != null && user.isAnonymous()){
            setContentView(R.layout.activity_main_ano);
            viewPager = findViewById(R.id.viewPager);
            navView = findViewById(R.id.nav_view);
            guestText = findViewById(R.id.guest_text);
            if(!isConnected) {
                viewPager.setVisibility(View.GONE);
                navView.setVisibility(View.GONE);
                guestText.setVisibility(View.VISIBLE);
            } else {
                viewPager.setVisibility(View.VISIBLE);
                navView.setVisibility(View.VISIBLE);
                guestText.setVisibility(View.GONE);
            }
        } else {
            if(isConnected) {
                setContentView(R.layout.activity_main);
                viewPager = findViewById(R.id.viewPager);
                navView = findViewById(R.id.nav_view);
            } else {
                setContentView(R.layout.activity_main_no_network);
                viewPager = findViewById(R.id.viewPager);
                navView = findViewById(R.id.nav_view);
            }
        }
        constraintLayout = findViewById(R.id.main);

        Intent intent = getIntent();
        boolean c = intent.getBooleanExtra(CONNECTION,false);
        boolean s = intent.getBooleanExtra(SECOND,false);
        if(s) {
            if (c) {
                Snackbar.make(constraintLayout, getString(R.string.connection_restored), Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(constraintLayout, getString(R.string.no_connection), Snackbar.LENGTH_LONG).show();
            }
        }

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        helloUserText = findViewById(R.id.hello_user);
        userImage = findViewById(R.id.userImage);
        userImage.setImageResource(R.drawable.account_circle);

        if(user != null) {
            String username = user.getDisplayName();
            String hello;
            if(username != null && !username.isEmpty()){
                hello = getString(R.string.hello) + getFirstWordCapitalized(username);
            } else {
                hello = getString(R.string.hello_guest);
            }
            helloUserText.setText(hello);
            Uri profileImage = user.getPhotoUrl();
            Glide.with(getApplicationContext()).load(profileImage)
                    .apply(new RequestOptions().override(200,200))
                    .placeholder(R.drawable.account_circle)
                    .into(userImage);

            Log.i(TAG, "onCreate: " + user.getUid());

        }


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()
                , FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
                ,isConnected);
        viewPager.setAdapter(viewPagerAdapter);

        // Set up the Bottom Navigation View
        navView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(user != null && user.isAnonymous()){
                if(isConnected) {
                    if (itemId == R.id.navigation_home) {
                        viewPager.setCurrentItem(0);
                        return true;
                    } else if (itemId == R.id.navigation_search) {
                        viewPager.setCurrentItem(1);
                        return true;
                    } else if (itemId == R.id.navigation_lists) {
                        viewPager.setCurrentItem(2);
                        return true;
                    }
                }
            } else {
                if(isConnected) {
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
                    } else if (itemId == R.id.navigation_week_plan) {
                        viewPager.setCurrentItem(4);
                        return true;
                    }
                } else {
                    if (itemId == R.id.navigation_favorite) {
                        viewPager.setCurrentItem(0);
                        return true;
                    } else if (itemId == R.id.navigation_week_plan) {
                        viewPager.setCurrentItem(1);
                        return true;
                    }
                }
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
                if (user != null && user.isAnonymous()) {
                    if (isConnected) {
                        switch (position) {
                            case 0:
                                navView.setSelectedItemId(R.id.navigation_home);
                                break;
                            case 1:
                                navView.setSelectedItemId(R.id.navigation_search);
                                break;
                            case 2:
                                navView.setSelectedItemId(R.id.navigation_lists);
                                break;
                        }
                    }
                } else {
                    if (isConnected) {
                        switch (position) {
                            case 0:
                                navView.setSelectedItemId(R.id.navigation_home);
                                break;
                            case 1:
                                navView.setSelectedItemId(R.id.navigation_search);
                                break;
                            case 2:
                                navView.setSelectedItemId(R.id.navigation_lists);
                                break;
                            case 3:
                                navView.setSelectedItemId(R.id.navigation_favorite);
                                break;
                            case 4:
                                navView.setSelectedItemId(R.id.navigation_week_plan);
                                break;
                        }
                    } else {
                        switch (position) {
                            case 0:
                                navView.setSelectedItemId(R.id.navigation_favorite);
                                break;
                            case 1:
                                navView.setSelectedItemId(R.id.navigation_week_plan);
                                break;
                        }
                    }
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

    public static String getFirstWordCapitalized(String input) {
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

    @Override
    public void onItemSelected(Category category) {
        Intent intent = new Intent(this, MealsActivity.class);
        intent.putExtra(MEALS_TITLE,category.strCategory);
        intent.putExtra(DATA_TYPE,CATEGORIES);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(Meal meal) {
        Intent intent = new Intent(this, MealsActivity.class);
        intent.putExtra(MEALS_TITLE,meal.strArea);
        intent.putExtra(DATA_TYPE,COUNTRIES);
        startActivity(intent);
    }

    @Override
    public void onChangeConnection(Boolean isConnected) {
        // Only refresh UI if there's a change in connection state
        iHomeView.isConnected(isConnected);
        if (this.isConnected != isConnected) {
            this.isConnected = isConnected;
            Log.i(TAG, "onChangeConnection: is connected : " + isConnected);
            refresheUI();
            if (isConnected) {
                if (wasDisconnected) {
                    // Show a message when the connection is restored
                    Snackbar.make(constraintLayout,getString(R.string.connection_restored), Snackbar.LENGTH_LONG).show();
                }
            } else {
                // Show a message when the connection is lost
                Snackbar.make(constraintLayout, getString(R.string.no_connection), Snackbar.LENGTH_LONG).show();
                wasDisconnected = true;

            }
        }
    }

    public void refresheUI(){
        if(!isRefreshed){
            Intent intent = getIntent();
            finish();
            intent.putExtra(CONNECTION,isConnected);
            intent.putExtra(SECOND,true);
            startActivity(intent);
            isRefreshed = true;
            new Handler(Looper.getMainLooper()).postDelayed(() -> isRefreshed = false, 2000); // Reset the refresh flag after a delay
        }
    }




    @Override
    protected void onDestroy() {
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
            connectionReceiver = null;
        }
        super.onDestroy();
    }

    @Override
    public void onDataPass(boolean state) {
        int CurrentFragment = viewPager.getCurrentItem();
        FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) viewPager.getAdapter();
        if (adapter != null) {
            if(CurrentFragment == 0){
                HomeFragment fragment = (HomeFragment) adapter.instantiateItem(viewPager, CurrentFragment);
                if (fragment != null) {
                    fragment.updateData(state,MainActivity.mode);
                }
            } else if(CurrentFragment == 1){
                SearchFragment fragment = (SearchFragment) adapter.instantiateItem(viewPager, CurrentFragment);
                if (fragment != null) {
                    fragment.updateData(state);
                }
            }

        }
    }

    @Override
    public void send(int mode) {
        MainActivity.mode = mode;
    }
}
