package com.malakezzat.foodplanner.view;

import static android.content.res.Resources.getSystem;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.UserPresenter;
import com.malakezzat.foodplanner.presenter.interview.IUserPresenter;

public class SignupActivity extends AppCompatActivity implements ConnectionListener{

    private TextInputLayout userInputLayout,emailInputLayout, passwordInputLayout;
    private TextInputEditText inputUser,inputEmail, inputPassword;
    private MaterialButton btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    public static final int EMAIL_MODE = 1;
    public static final int PASSWORD_MODE = 2;
    public static final int USER_MODE = 3;
    IUserPresenter iUserPresenter;
    ConnectionReceiver connectionReceiver;
    Boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
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
        userInputLayout = findViewById(R.id.userInputLayout);
        inputUser = findViewById(R.id.userEditText);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        btnSignUp = findViewById(R.id.signupButton);
        inputEmail = findViewById(R.id.emailEditText);
        inputPassword = findViewById(R.id.passwordEditText);
        progressBar = findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(v -> {
            if(isConnected) {
                String username = inputUser.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                boolean usernameV = checkValidation(userInputLayout, username, USER_MODE);
                boolean emailV = checkValidation(emailInputLayout, email, EMAIL_MODE);
                boolean passwordV = checkValidation(passwordInputLayout, password, PASSWORD_MODE);

                if (usernameV || emailV || passwordV) {
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
                                                    Toast.makeText(SignupActivity.this, getString(R.string.signup_successful), Toast.LENGTH_SHORT).show();
                                                    iUserPresenter = new UserPresenter(new MealLocalDataSourceImpl(AppDatabase.getInstance(getApplicationContext())));
                                                    iUserPresenter.restoreUserData();
                                                }
                                            }
                                        });
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign up fails, display a message to the user.
                                Toast.makeText(SignupActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
                Toast.makeText(SignupActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            }
        });

        inputUser.setOnFocusChangeListener((v, hasFocus)->{
            if (!hasFocus) {
                String username = inputUser.getText().toString().trim();
                checkValidation(userInputLayout,username,USER_MODE);
            }
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

    public boolean checkValidation(TextInputLayout InputLayout, String text, int mode){
        if(mode == 1) {
            if (text.isEmpty()) {
                InputLayout.setHelperText(getResources().getString(R.string.email_empty));
                InputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                return true;
            } else if (!isValidEmail(text)) {
                InputLayout.setHelperText(getSystem().getString(R.string.invalid_email));
                InputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                return true;
            } else {
                InputLayout.setHelperText("");
                return false;
            }
        } else if (mode == 2) {
            if (text.isEmpty()) {
                InputLayout.setHelperText(getResources().getString(R.string.password_empty));
                InputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                return true;
            } else if (!isValidPassword(text)) {
                InputLayout.setHelperText(getResources().getString(R.string.password_format));
                InputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                return true;
            } else {
                InputLayout.setHelperText("");
                return false;
            }
        } else if(mode == 3) {
            if (text.isEmpty()) {
                InputLayout.setHelperText(getResources().getString(R.string.username_empty));
                InputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                return true;
            } else if (text.substring(0,1).matches("^[0-9].*")) {
                InputLayout.setHelperText(getResources().getString(R.string.username_start_number));
                InputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                return true;
            } else {
                InputLayout.setHelperText("");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onChangeConnection(Boolean isConnected) {
        this.isConnected = isConnected;
    }

    @Override
    protected void onDestroy() {
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
            connectionReceiver = null;
        }
        super.onDestroy();
    }
}
