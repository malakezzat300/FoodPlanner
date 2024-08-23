package com.malakezzat.foodplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.malakezzat.foodplanner.R;

public class SplashScreen extends AppCompatActivity {
    private static boolean splashLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!splashLoaded) {
            setContentView(R.layout.activity_splash_screen);
            LottieAnimationView lottieAnimationView = findViewById(R.id.lottie_animation_view);
            lottieAnimationView.playAnimation();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
                    finish();
                }
            }, 2900);  // TODO make it 2000 or 2900

            splashLoaded = true;
        } else {
            Intent goToMainActivity = new Intent(SplashScreen.this, WelcomeActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }

    }

}