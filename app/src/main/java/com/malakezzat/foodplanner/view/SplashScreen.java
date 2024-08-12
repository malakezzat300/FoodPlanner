package com.malakezzat.foodplanner.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.malakezzat.foodplanner.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(this,MainActivity.class);
        new Thread(()->{
            try {
                Thread.sleep(0);  // TODO make it 2000
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startActivity(intent);
        }).start();

    }
}