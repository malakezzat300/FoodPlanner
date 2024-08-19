package com.malakezzat.foodplanner.view.mainfragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.view.mainfragments.fragments.MealDetailsFragment;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnMealClickListener;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<Meal> mealList;
    private OnMealClickListener onMealClickListener;
    private Context context;
    private int source;
    public static final int HOME_FRAGMENT = 0;
    public static final int SEARCH_FRAGMENT = 1;

    public CarouselAdapter(Context context, List<Meal> mealList, OnMealClickListener onMealClickListener, int source) {
        this.context = context;
        this.mealList = mealList;
        this.onMealClickListener = onMealClickListener;
        this.source = source;
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
        if(source == HOME_FRAGMENT) {
            holder.favButton.setVisibility(View.VISIBLE);
        } else if(source == SEARCH_FRAGMENT){
            holder.favButton.setVisibility(View.GONE);
        }
        holder.cardTitle.setText(mealList.get(position).strMeal);
        Glide.with(context).load(mealList.get(position).strMealThumb)
                .apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.new_logo3)
                .into(holder.cardImage);
        holder.favButton.setOnClickListener(v -> {
            if(holder.isFav){
                holder.favButton.setImageResource(R.drawable.favorite_border);
                onMealClickListener.removeFromFav(mealList.get(position));
                holder.isFav = false;
            } else {
                holder.favButton.setImageResource(R.drawable.favorite_red);
                onMealClickListener.addToFav(mealList.get(position));
                holder.isFav = true;
            }
        });

        if(holder.meal != null) {
            holder.meal.setOnClickListener(v -> {
                if(source == HOME_FRAGMENT) {
                    onMealClickListener.showMealDetails(mealList.get(position));
                } else if(source == SEARCH_FRAGMENT){
                    onMealClickListener.getMealById(mealList.get(position).idMeal);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage,favButton;
        TextView cardTitle;
        boolean isFav;
        CardView meal;
        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            favButton = itemView.findViewById(R.id.fav_button);
            meal = itemView.findViewById(R.id.meal_material);

            isFav = false;
        }
    }
}