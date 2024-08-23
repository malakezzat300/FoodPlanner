package com.malakezzat.foodplanner.view.mainfragments.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.view.MainActivity;
import com.malakezzat.foodplanner.view.MealTypeDialog;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<Meal> mealList;
    private OnMealClickListener onMealClickListener;
    private Context context;
    private int source;
    public static final int HOME_FRAGMENT = 0;
    public static final int SEARCH_FRAGMENT = 1;
    FirebaseUser user;
    int mealClicked;
    public CarouselAdapter(Context context, List<Meal> mealList, OnMealClickListener onMealClickListener, int source) {
        this.context = context;
        this.mealList = mealList;
        this.onMealClickListener = onMealClickListener;
        this.source = source;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new View(context);
        if(source == HOME_FRAGMENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_meal, parent, false);
        } else if(source == SEARCH_FRAGMENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row, parent, false);
        }
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.favButton.setVisibility(View.VISIBLE);
        holder.cardTitle.setText(mealList.get(position).strMeal);
        Glide.with(context).load(mealList.get(position).strMealThumb)
                .apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.new_logo3)
                .into(holder.cardImage);

        if(mealList.get(position).isFav){
            //holder.isFav = true;
            holder.favButton.setImageResource(R.drawable.favorite_red);
        } else {
            //holder.isFav = false;
            holder.favButton.setImageResource(R.drawable.favorite_border);
        }

        holder.favButton.setOnClickListener(v -> {
            if(user != null && user.isAnonymous()){
                Toast.makeText(context, holder.itemView.getContext().getString(R.string.fav_guest), Toast.LENGTH_SHORT).show();
            } else {
                if (mealList.get(position).isFav) {
                    holder.favButton.setImageResource(R.drawable.favorite_border);
                    onMealClickListener.removeFromFav(mealList.get(position));
                    mealList.get(position).isFav = false;
                } else {
                    holder.favButton.setImageResource(R.drawable.favorite_red);
                    onMealClickListener.addToFav(mealList.get(position));
                    mealList.get(position).isFav = true;
                }
            }
        });

        if(holder.meal != null) {
            holder.meal.setOnClickListener(v -> {
                mealClicked = position;
                if(source == HOME_FRAGMENT) {
                    MainActivity.mode = 2;
                    onMealClickListener.showMealDetails(mealList.get(position));
                } else if(source == SEARCH_FRAGMENT){
                    onMealClickListener.getMealById(mealList.get(position).idMeal);
                }
            });
        }

        holder.weekPlanButton.setOnClickListener(v -> {
            if(user != null && user.isAnonymous()){
                Toast.makeText(context, holder.itemView.getContext().getString(R.string.week_guest), Toast.LENGTH_SHORT).show();
            } else {
                Calendar today = Calendar.getInstance();
                Calendar maxDate = Calendar.getInstance();
                maxDate.add(Calendar.DAY_OF_YEAR, 7);
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, monthOfYear, dayOfMonth);
                                String dayOfWeek = new SimpleDateFormat("EEEE", Locale.US).format(selectedDate.getTime());
                                String formattedDate = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year;
                                MealTypeDialog.showMealSelectionDialog(context, selectedItem -> {
                                mealList.get(position).date = formattedDate;
                                mealList.get(position).day = dayOfWeek;
                                mealList.get(position).dateAndTime = dayOfWeek + " " + formattedDate;
                                mealList.get(position).mealType = selectedItem;
                                onMealClickListener.addToWeekPlan(mealList.get(position));
                                });
                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage,favButton,weekPlanButton;
        TextView cardTitle;
        boolean isFav;
        CardView meal;
        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            favButton = itemView.findViewById(R.id.fav_button);
            meal = itemView.findViewById(R.id.meal_material);
            weekPlanButton = itemView.findViewById(R.id.week_plan_button);
            isFav = false;
        }
    }

    public void updateData(boolean newData) {
        mealList.get(mealClicked).isFav = newData;
        notifyDataSetChanged();
    }
}