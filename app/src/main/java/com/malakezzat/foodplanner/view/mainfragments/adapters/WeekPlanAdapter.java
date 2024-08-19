package com.malakezzat.foodplanner.view.mainfragments.adapters;

import android.content.Context;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.local.week.MealDBWeek;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class WeekPlanAdapter extends RecyclerView.Adapter<WeekPlanAdapter.WeekPlanHolder> {

    private List<MealDBWeek> meals;
    private OnMealClickListener onMealClickListener;
    private Context context;

    public WeekPlanAdapter(Context context, List<MealDBWeek> meals, OnMealClickListener onMealClickListener) {
        this.context = context;
        this.meals = meals;
        this.onMealClickListener = onMealClickListener;
        sortAndFilterMeals();
    }

    private void sortAndFilterMeals() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d-M-yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = calendar.getTime();

        Collections.sort(meals, (m1, m2) -> {
            try {
                Date date1 = sdf.parse(m1.dateAndTime);
                Date date2 = sdf.parse(m2.dateAndTime);
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        calendar.add(Calendar.DAY_OF_YEAR, 8);
        Date endDate = calendar.getTime();

        meals.removeIf(meal -> {
            try {
                Date mealDate = sdf.parse(meal.dateAndTime);
                return mealDate.before(yesterday) || mealDate.after(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return true;
            }
        });
    }

    @NonNull
    @Override
    public WeekPlanAdapter.WeekPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row_week, parent, false);
        return new WeekPlanAdapter.WeekPlanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekPlanAdapter.WeekPlanHolder holder, int position) {
        MealDBWeek currentMeal = meals.get(position);

        holder.cardTitle.setText(currentMeal.strMeal);
        Glide.with(context).load(currentMeal.strMealThumb)
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.new_logo3)
                .into(holder.cardImage);
        holder.favButton.setImageResource(R.drawable.close);
        holder.favButton.setOnClickListener(v -> {
            Toast.makeText(context, currentMeal.strMeal + " is removed", Toast.LENGTH_SHORT).show();
            onMealClickListener.removeFromFav(currentMeal.toMeal());
        });
        holder.meal.setOnClickListener(v -> {
            onMealClickListener.showMealDetails(currentMeal.toMeal());
        });

        // Show date only if it's the first occurrence for the day
        if (position == 0 || !currentMeal.dateAndTime.equals(meals.get(position - 1).dateAndTime)) {
            holder.dateAndTime.setText(currentMeal.dateAndTime);
            holder.dateAndTime.setVisibility(View.VISIBLE);
        } else {
            holder.dateAndTime.setVisibility(View.GONE);
        }
    }

    public void setMeals(List<MealDBWeek> meals) {
        this.meals = meals;
        sortAndFilterMeals();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class WeekPlanHolder extends RecyclerView.ViewHolder {
        ImageView cardImage, favButton;
        TextView cardTitle, dateAndTime;
        ConstraintLayout meal;

        public WeekPlanHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            favButton = itemView.findViewById(R.id.fav_button);
            meal = itemView.findViewById(R.id.meal_material);
            dateAndTime = itemView.findViewById(R.id.date_and_time);
        }
    }
}
