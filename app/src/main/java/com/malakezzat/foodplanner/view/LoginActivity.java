package com.malakezzat.foodplanner.view;

import static com.malakezzat.foodplanner.view.SignupActivity.isValidEmail;
import static com.malakezzat.foodplanner.view.SignupActivity.isValidPassword;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.malakezzat.foodplanner.R;

public class LoginActivity extends AppCompatActivity {

    private static final String USER = "user";
    private TextInputLayout emaiInputLayout , passwordInputLayout;
    private TextInputEditText inputEmail, inputPassword;
    private MaterialButton btnLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emaiInputLayout = findViewById(R.id.emailLoginInputLayout);
        passwordInputLayout = findViewById(R.id.passwordLoginInputLayout);
        btnLogin = findViewById(R.id.loginButton);
        inputEmail = findViewById(R.id.emailLoginEditText);
        inputPassword = findViewById(R.id.passwordLoginEditText);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (!isValidEmail(email)) {
                return;
            }

            if (!isValidPassword(password)) {
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            // Create user with email and password
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, proceed to main activity or other actions
                                Log.d("LoginActivity", "signInWithEmailAndPassword:success");
                                Toast.makeText(LoginActivity.this, "Login Success.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, TestActivity.class);
                                FirebaseUser currentUser = auth.getCurrentUser();
                                intent.putExtra(USER,currentUser.getDisplayName());
                                startActivity(intent);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                // Sign in failed, display a message to the user
                                Log.w("LoginActivity", "signInWithEmailAndPassword:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        inputEmail.setOnFocusChangeListener((v, hasFocus)->{
            if (!hasFocus) {
                String email = inputEmail.getText().toString().trim();
                if (!isValidEmail(email)) {
                    emaiInputLayout.setHelperText("Invalid email format");
                    emaiInputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                } else {
                    emaiInputLayout.setHelperText("");
                }
            }
        });

        inputPassword.setOnFocusChangeListener((v, hasFocus)->{
            if (!hasFocus) {
                String password = inputPassword.getText().toString().trim();
                if (!isValidPassword(password)) {
                    passwordInputLayout.setHelperText("Password must be at least 8 characters and contain letters and numbers and symbol");
                    passwordInputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                } else {
                    passwordInputLayout.setHelperText("");
                }
            }
        });

    }

}