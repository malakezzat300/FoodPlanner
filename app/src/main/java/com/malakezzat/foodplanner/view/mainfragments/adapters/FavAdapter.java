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
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private List<MealDB> meals;
    private OnMealClickListener onMealClickListener;
    private Context context;

    public FavAdapter(Context context , List<MealDB> meals , OnMealClickListener onMealClickListener) {
        this.context = context;
        this.meals = meals;
        this.onMealClickListener = onMealClickListener;
    }


    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {

        holder.cardTitle.setText(meals.get(position).strMeal);
        Glide.with(context).load(meals.get(position).strMealThumb)
                .apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.new_logo3)
                .into(holder.cardImage);
        holder.favButton.setImageResource(R.drawable.close);
        holder.favButton.setOnClickListener(v->{
            Toast.makeText(context, meals.get(position).strMeal + " is removed", Toast.LENGTH_SHORT).show();
            onMealClickListener.removeFromFav(meals.get(position).toMeal());
        });
        holder.meal.setOnClickListener(v->{
            onMealClickListener.showMealDetails(meals.get(position).toMeal());
        });

        holder.weekPlanButton.setOnClickListener(v -> {
            Calendar today = Calendar.getInstance();
            Calendar maxDate = Calendar.getInstance();
            maxDate.add(Calendar.DAY_OF_YEAR, 7);
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(year, monthOfYear, dayOfMonth);
                            String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(selectedDate.getTime());
                            String formattedDate = dayOfWeek + " " + String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year;
                            meals.get(position).dateAndTime = formattedDate;
                            onMealClickListener.addToWeekPlan(meals.get(position).toMeal());
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            datePickerDialog.show();

        });

    }

    public void setMeals(List<MealDB> meals){
        this.meals = meals;
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage,favButton,weekPlanButton;
        TextView cardTitle;
        CardView meal;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            favButton = itemView.findViewById(R.id.fav_button);
            meal = itemView.findViewById(R.id.meal_material);
            weekPlanButton = itemView.findViewById(R.id.week_plan_button);
        }
    }

}
