package com.malakezzat.foodplanner.view;

import static com.malakezzat.foodplanner.view.SignupActivity.EMAIL_MODE;
import static com.malakezzat.foodplanner.view.SignupActivity.PASSWORD_MODE;
import static com.malakezzat.foodplanner.view.SignupActivity.checkValidation;
import static com.malakezzat.foodplanner.view.SignupActivity.isValidEmail;
import static com.malakezzat.foodplanner.view.SignupActivity.isValidPassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextInputEditText inputEmail, inputPassword;
    private MaterialButton btnLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailInputLayout = findViewById(R.id.emailLoginInputLayout);
        passwordInputLayout = findViewById(R.id.passwordLoginInputLayout);
        btnLogin = findViewById(R.id.loginButton);
        inputEmail = findViewById(R.id.emailLoginEditText);
        inputPassword = findViewById(R.id.passwordLoginEditText);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            boolean emailV = checkValidation(emailInputLayout,email,EMAIL_MODE);
            boolean passwordV = checkValidation(passwordInputLayout,password,PASSWORD_MODE);

            if(emailV || passwordV){
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

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                FirebaseUser currentUser = auth.getCurrentUser();
                                intent.putExtra(USER,currentUser.getDisplayName());
                                startActivity(intent);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                // Sign in failed, display a message to the user
                                Log.w("LoginActivity", "signInWithEmailAndPassword:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Check the Email and Password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        inputEmail.setOnFocusChangeListener((v, hasFocus)->{
            if (!hasFocus) {
                String email = inputEmail.getText().toString().trim();
                checkValidation(emailInputLayout,email,EMAIL_MODE);
            }
        });

        inputPassword.setOnFocusChangeListener((v, hasFocus)->{
            if (!hasFocus) {
                String password = inputPassword.getText().toString().trim();
                checkValidation(passwordInputLayout,password,PASSWORD_MODE);

            }
        });

    }



}