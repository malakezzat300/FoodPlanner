package com.malakezzat.foodplanner.view.mainfragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.local.AppDatabase;
import com.malakezzat.foodplanner.model.local.MealLocalDataSourceImpl;
import com.malakezzat.foodplanner.presenter.UserPresenter;
import com.malakezzat.foodplanner.presenter.interview.IUserPresenter;
import com.malakezzat.foodplanner.view.ConnectionListener;
import com.malakezzat.foodplanner.view.ConnectionReceiver;
import com.malakezzat.foodplanner.view.SignupActivity;
import com.malakezzat.foodplanner.view.WelcomeActivity;

public class UserFragment extends BottomSheetDialogFragment  implements ConnectionListener {
    ImageView userImage;
    TextView username,email;
    Button backupButton,signOutButton,updatePictureButton,removePictureButton,deleteAccountButton;
    FirebaseAuth auth;
    IUserPresenter iUserPresenter;
    Context context;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMediaLauncher;
    ConnectionReceiver connectionReceiver;
    boolean isConnected,wasDisconnected = false;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userImage = view.findViewById(R.id.imageView_bottom_fragment);
        username= view.findViewById(R.id.username_bottom_fragment);
        email= view.findViewById(R.id.email_bottom_fragment);
        backupButton = view.findViewById(R.id.backup_button);
        signOutButton = view.findViewById(R.id.sign_out_button);
        updatePictureButton = view.findViewById(R.id.update_image_button);
        removePictureButton = view.findViewById(R.id.remove_image_button);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        context = view.getContext();
        connectionReceiver  = new ConnectionReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        view.getContext().registerReceiver(connectionReceiver,filter, Context.RECEIVER_NOT_EXPORTED);
        ConnectivityManager cm = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork == null) {
            isConnected = false;
        } else if (activeNetwork.isConnected()){
            isConnected = true;
        }

        iUserPresenter = new UserPresenter(new MealLocalDataSourceImpl(AppDatabase.getInstance(context)));

        if(user != null) {
            username.setText(user.getDisplayName());
            email.setText(user.getEmail());
            Uri profileImage = user.getPhotoUrl();
            Glide.with(view.getContext()).load(profileImage)
                    .apply(new RequestOptions().override(200,200))
                    .placeholder(R.drawable.account_circle)
                    .into(userImage);
            if(user.isAnonymous()){
                username.setText(getString(R.string.guest));
                email.setText(user.getEmail());
                backupButton.setVisibility(View.GONE);
            }
        }
        if(!isConnected) {
            backupButton.setVisibility(View.GONE);
        }

        backupButton.setOnClickListener(v->{
            if(isConnected) {
                iUserPresenter.backupUserData();
                Toast.makeText(context, "Backup Successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Backup Failed Please Connect To Internet First!", Toast.LENGTH_SHORT).show();
            }
        });

        signOutButton.setOnClickListener(v->{
            auth.signOut();
            Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();
                        if (user != null) {
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "User profile updated.", Toast.LENGTH_SHORT).show();
                                                Glide.with(view.getContext()).load(user.getPhotoUrl())
                                                        .apply(new RequestOptions().override(200,200))
                                                        .placeholder(R.drawable.account_circle)
                                                        .into(userImage);
                                            } else {
                                                Toast.makeText(getContext(), "Failed to update profile picture.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        updatePictureButton.setOnClickListener(v->{
            pickMediaLauncher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });


        removePictureButton.setOnClickListener(v -> {
            Uri placeholderUri = Uri.parse("android.resource://" + view.getContext().getPackageName() + "/drawable/account_circle");
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(placeholderUri)
                    .build();

            if (user != null) {
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "User profile picture removed.", Toast.LENGTH_SHORT).show();
                                    Glide.with(view.getContext()).load(user.getPhotoUrl())
                                            .apply(new RequestOptions().override(200,200))
                                            .placeholder(R.drawable.account_circle)
                                            .into(userImage);
                                } else {
                                    Toast.makeText(getContext(), "Failed to remove profile picture.", Toast.LENGTH_SHORT).show();
                                }
                        });
            }
        });


    }

    @Override
    public void onChangeConnection(Boolean isConnected) {
        this.isConnected = isConnected;

    }
}