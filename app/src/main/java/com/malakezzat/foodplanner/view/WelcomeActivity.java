package com.malakezzat.foodplanner.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.model.repository.MealRepositoryImpl;
import com.malakezzat.foodplanner.presenter.UserPresenter;
import com.malakezzat.foodplanner.presenter.interview.IUserPresenter;
import com.malakezzat.foodplanner.view.connectionreceiver.ConnectionListener;
import com.malakezzat.foodplanner.view.connectionreceiver.ConnectionReceiver;

public class WelcomeActivity extends AppCompatActivity implements ConnectionListener {
    private Button signupWithEmail, loginWithEmail,guest;
    private SignInButton googleSignUp;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private static final String USER = "user";
    IUserPresenter iUserPresenter;
    ConstraintLayout constraintLayout;
    ConnectionReceiver connectionReceiver;
    boolean wasDisconnected = false;
    boolean isConnected;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        guest = findViewById(R.id.guestButton);
        signupWithEmail = findViewById(R.id.signupWithEmailButton);
        loginWithEmail = findViewById(R.id.loginWithEmailButton);
        googleSignUp = findViewById(R.id.googleLogInButton);
        constraintLayout = findViewById(R.id.main);
        connectionReceiver  = new ConnectionReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectionReceiver,filter, Context.RECEIVER_NOT_EXPORTED);

        signupWithEmail.setOnClickListener(v->{
            Intent intent = new Intent(this,SignupActivity.class);
            startActivity(intent);
        });

        loginWithEmail.setOnClickListener(v->{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });

        guest.setOnClickListener(v->{
            signInAnonymously();
        });

        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                if (account != null && account.getIdToken() != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                } else {
                    Log.w(TAG, "Google sign in failed: null token");
                    Toast.makeText(WelcomeActivity.this, getString(R.string.google_failed), Toast.LENGTH_SHORT).show();
                }
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(WelcomeActivity.this, getString(R.string.google_failed), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        iUserPresenter = new UserPresenter(new MealRepositoryImpl(new MealLocalDataSourceImpl(AppDatabase.getInstance(getApplicationContext()))));                        updateUI(user);
                        iUserPresenter.restoreUserData();
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(WelcomeActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.putExtra(USER,user.getDisplayName());
            startActivity(intent);
        }
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "signInAnonymously:success");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUI(user);
            } else if(!isConnected){
                Toast.makeText(WelcomeActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
            }
            else {
                Log.w(TAG, "signInAnonymously:failure", task.getException());
                Toast.makeText(WelcomeActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            iUserPresenter = new UserPresenter(new MealRepositoryImpl(new MealLocalDataSourceImpl(AppDatabase.getInstance(getApplicationContext()))));        }
        updateUI(currentUser);
    }

    @Override
    public void onChangeConnection(Boolean isConnected) {
        this.isConnected = isConnected;
        if(isConnected && !wasDisconnected) {

        } else if(isConnected) {
            Snackbar.make(constraintLayout, getString(R.string.connection_restored), Snackbar.LENGTH_LONG).show();
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
        } else {
            Snackbar.make(constraintLayout, getString(R.string.no_connection), Snackbar.LENGTH_LONG).show();
            wasDisconnected = true;
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
}
