package com.malakezzat.foodplanner.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.malakezzat.foodplanner.R;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout emaiInputLayout , passwordInputLayout;
    private TextInputEditText inputUser,inputEmail, inputPassword;
    private MaterialButton btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        inputUser = findViewById(R.id.userEditText);
        emaiInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        btnSignUp = findViewById(R.id.signupButton);
        inputEmail = findViewById(R.id.emailEditText);
        inputPassword = findViewById(R.id.passwordEditText);
        progressBar = findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(v -> {
            String username = inputUser.getText().toString().trim();
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
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignupActivity.this, task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            // Navigate to another activity if needed
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
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

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) { //TODO make it better (for every case)
        if (password.length() < 8) {
            return false;
        }
        boolean hasSymbol = false;
        boolean hasCapitalLetter = false;
        boolean hasSmallLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (isSymbol(c)) {
                hasSymbol = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isUpperCase(c)) {
                hasCapitalLetter = true;
            } else if (Character.isLowerCase(c)) {
                hasSmallLetter = true;
            }
        }
        return hasSymbol && hasDigit && hasCapitalLetter && hasSmallLetter ;
    }

    public static boolean isSymbol(char c) {
        return !Character.isLetterOrDigit(c) && !Character.isWhitespace(c);
    }
}
