package com.malakezzat.foodplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.Remote.Network;

public class TestActivity extends AppCompatActivity {

    TextView textview;
    Button button;
    private static final String USER = "user";
    Network network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textview = findViewById(R.id.textView);
        button = findViewById(R.id.button2);

        network = new Network();

        button.setOnClickListener(v->{

        });

    }
}