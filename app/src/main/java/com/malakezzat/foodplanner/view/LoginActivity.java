package com.malakezzat.foodplanner.view;

import static com.malakezzat.foodplanner.view.SignupActivity.EMAIL_MODE;
import static com.malakezzat.foodplanner.view.SignupActivity.PASSWORD_MODE;
import static com.malakezzat.foodplanner.view.SignupActivity.checkValidation;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.UserPresenter;
import com.malakezzat.foodplanner.presenter.interview.IUserPresenter;

public class LoginActivity extends AppCompatActivity implements ConnectionListener {

    private static final String USER = "user";
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextInputEditText inputEmail, inputPassword;
    private MaterialButton btnLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    IUserPresenter iUserPresenter;
    Boolean isConnected;
    ConnectionReceiver connectionReceiver;
    ConnectivityManager cm;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        emailInputLayout = findViewById(R.id.emailLoginInputLayout);
        passwordInputLayout = findViewById(R.id.passwordLoginInputLayout);
        btnLogin = findViewById(R.id.loginButton);
        inputEmail = findViewById(R.id.emailLoginEditText);
        inputPassword = findViewById(R.id.passwordLoginEditText);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> {
            if(isConnected) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                boolean emailV = checkValidation(emailInputLayout, email, EMAIL_MODE);
                boolean passwordV = checkValidation(passwordInputLayout, password, PASSWORD_MODE);

                if (emailV || passwordV) {
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
                                    Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    FirebaseUser currentUser = auth.getCurrentUser();
                                    intent.putExtra(USER, currentUser.getDisplayName());
                                    startActivity(intent);
                                    iUserPresenter = new UserPresenter(new MealLocalDataSourceImpl(AppDatabase.getInstance(getApplicationContext())));
                                    iUserPresenter.restoreUserData();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    // Sign in failed, display a message to the user
                                    Log.w("LoginActivity", "signInWithEmailAndPassword:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, getString(R.string.check_email_password), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(LoginActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
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