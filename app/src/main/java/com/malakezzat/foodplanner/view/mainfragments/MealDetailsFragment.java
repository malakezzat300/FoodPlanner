package com.malakezzat.foodplanner.view.mainfragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.data.Ingredient;
import com.malakezzat.foodplanner.model.data.IngredientList;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.view.mainfragments.adapters.IngredientsAdapter;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MealDetailsFragment extends BottomSheetDialogFragment {

    private static final String TAG = "MealDetailsFragment";
    Meal meal;
    IngredientList ingredientList;
    ImageView mealImage,favButton,weekPlanButton;
    TextView mealTitle,mealCountry, stepsText;
    RecyclerView ingredientsRecyclerView ;
    YouTubePlayerView youTubePlayer;
    ScrollView scrollView;
    OnMealClickListener onMealClickListener;
    IngredientsAdapter ingredientsAdapter;
    GridLayoutManager gridLayoutManager;
    List<Ingredient> ingredients;
    ConstraintLayout youtubePlayerConstraintlayout;
    Boolean isFav = false;
    FirebaseUser user;
    Context context;
    public MealDetailsFragment(Meal meal,OnMealClickListener onMealClickListener) {
        this.meal = meal;
        this.onMealClickListener = onMealClickListener;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        mealImage = view.findViewById(R.id.meal_image);
        favButton = view.findViewById(R.id.fav_button_details);
        weekPlanButton = view.findViewById(R.id.week_plan_button);
        mealTitle = view.findViewById(R.id.meal_title);
        mealCountry = view.findViewById(R.id.meal_country);
        stepsText = view.findViewById(R.id.steps_text);
        youTubePlayer = view.findViewById(R.id.youtube_player_view);
        scrollView = view.findViewById(R.id.scrollView);
        ingredients = new ArrayList<>();
        ingredientList = new IngredientList(meal);
        ingredients = ingredientList.ingredients;
        youtubePlayerConstraintlayout = view.findViewById(R.id.youtube_player_constraintlayout);


        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recycler_view);
        gridLayoutManager = new GridLayoutManager(view.getContext(),2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ingredientsRecyclerView.setLayoutManager(gridLayoutManager);

        ingredientsAdapter = new IngredientsAdapter(view.getContext(),ingredients);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        Glide.with(view.getContext()).load(meal.strMealThumb)
                .apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.new_logo3)
                .into(mealImage);

        weekPlanButton.setOnClickListener(v->{
            if(user != null && user.isAnonymous()){
                Toast.makeText(view.getContext(), getString(R.string.week_guest), Toast.LENGTH_SHORT).show();
            } else {
                Calendar today = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();
                maxDate.add(Calendar.DAY_OF_YEAR, 7);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, monthOfYear, dayOfMonth);
                                String dayOfWeek = new SimpleDateFormat("EEEE", Locale.US).format(selectedDate.getTime());
                                String formattedDate = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year;
                                meal.date = formattedDate;
                                meal.day = dayOfWeek;
                                onMealClickListener.addToWeekPlan(meal);
                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        if(meal.isFav){
            //holder.isFav = true;
            favButton.setImageResource(R.drawable.favorite_red);
        } else {
            //holder.isFav = false;
            favButton.setImageResource(R.drawable.favorite_border);
        }

        favButton.setOnClickListener(v -> {
            if(user != null && user.isAnonymous()){
                Toast.makeText(context, getString(R.string.fav_guest), Toast.LENGTH_SHORT).show();
            } else {
                if (meal.isFav) {
                    favButton.setImageResource(R.drawable.favorite_border);
                    onMealClickListener.removeFromFav(meal);
                    onMealClickListener.removeFromFav(meal.idMeal,2);
                    meal.isFav = false;
                } else {
                    favButton.setImageResource(R.drawable.favorite_red);
                    onMealClickListener.addToFav(meal);
                    onMealClickListener.addToFav(meal.idMeal,1);
                    meal.isFav = true;
                }
            }
        });


        String mealN = getString(R.string.meal_name) + " "+ meal.strMeal;
        String mealC = getString(R.string.meal_country) +" "+ meal.strArea;
        mealTitle.setText(mealN);
        mealCountry.setText(mealC);
        stepsText.setText(meal.strInstructions);
        getLifecycle().addObserver(youTubePlayer);

        youTubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if(meal.strYoutube != null) {
                    String video = extractYouTubeVideoId(meal.strYoutube);
                    if(video.equals("XIMLoLxmTDw")){
                        youtubePlayerConstraintlayout.setVisibility(View.GONE);
                    } else {
                        youTubePlayer.cueVideo(video, 0);
                    }
                }
            }
        });

        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) getDialog();
        if (bottomSheetDialog != null) {
            bottomSheetDialog.setOnShowListener(dialog -> {
                BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
                scrollView.setNestedScrollingEnabled(false);
                bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                            Log.i(TAG, "onStateChanged: " + "STATE_EXPANDED");
                            scrollView.setNestedScrollingEnabled(true);
                            bottomSheetBehavior.setDraggable(false);
                        } else {
                            bottomSheetBehavior.setDraggable(true);
                        }
                    }
                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });
                scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        // Check if the ScrollView is at the top
                        Log.d(TAG, "Scroll Y : " + scrollView.getScrollY());
                        if (scrollView.getScrollY() == 0) {
                            Log.d(TAG, "Reached the top");
                            scrollView.setNestedScrollingEnabled(false);
                            bottomSheetBehavior.setDraggable(true);
                        }
                    }
                });
            });
        }

    }

    public String extractYouTubeVideoId(String url) {
        String videoId = null;

        if (url != null && url.contains("v=")) {
            int startIndex = url.indexOf("v=") + 2;
            int endIndex = url.indexOf("&", startIndex);
            if (endIndex == -1) {
                endIndex = url.length();
            }
            videoId = url.substring(startIndex, endIndex);
        }

        return videoId != null ? videoId : "XIMLoLxmTDw"; // Fallback video ID
    }
}