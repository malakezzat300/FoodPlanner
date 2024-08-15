package com.malakezzat.foodplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.malakezzat.foodplanner.R;

public class TestActivity extends AppCompatActivity {

    TextView textview;
    Button button;
    private static final String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textview = findViewById(R.id.textView);
        button = findViewById(R.id.button2);

        Intent intent = getIntent();
        String userName = intent.getStringExtra(USER);
        textview.setText(userName);
        button.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "you have logged out", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(TestActivity.this,MainActivity.class);
            startActivity(intent1);
        });

    }
}