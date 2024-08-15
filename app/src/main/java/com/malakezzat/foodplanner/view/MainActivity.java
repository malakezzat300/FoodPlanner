package com.malakezzat.foodplanner.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.malakezzat.foodplanner.R;

public class MainActivity extends AppCompatActivity {
    private Button signupWithEmail, loginWithEmail,guest;
    private SignInButton googleSignUp;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private static final String USER = "user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guest = findViewById(R.id.guestButton);
        signupWithEmail = findViewById(R.id.signupWithEmailButton);
        loginWithEmail = findViewById(R.id.loginWithEmailButton);
        googleSignUp = findViewById(R.id.googleLogInButton);

        signupWithEmail.setOnClickListener(v->{
            Intent intent = new Intent(this,SignupActivity.class);
            startActivity(intent);
        });

        loginWithEmail.setOnClickListener(v->{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });

        guest.setOnClickListener(v->{
            //TODO guest implementation
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
                    // Google Sign In was successful, authenticate with Firebase
                    firebaseAuthWithGoogle(account.getIdToken());
                } else {
                    Log.w(TAG, "Google sign in failed: null token");
                    Toast.makeText(MainActivity.this, "Google sign in failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(MainActivity.this, "Google sign in failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                        updateUI(user);
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, "Signed in as: " + user.getEmail(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra(USER,user.getDisplayName());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Not signed in", Toast.LENGTH_LONG).show(); //TODO remove this
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
}
