package com.malakezzat.foodplanner.view.mainfragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnHomeListener;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<Meal> mealList;
    private OnHomeListener onHomeListener;
    private Context context;
    private int source;
    public static final int HOME_FRAGMENT = 0;
    public static final int SEARCH_FRAGMENT = 1;

    public CarouselAdapter(Context context, List<Meal> mealList,int source) {
        this.context = context;
        this.mealList = mealList;
        this.source = source;
    }

    public CarouselAdapter(Context context, List<Meal> mealList, OnHomeListener onHomeListener,int source) {
        this.context = context;
        this.mealList = mealList;
        this.onHomeListener = onHomeListener;
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
        holder.cardTitle.setText(mealList.get(position).strMeal);
        Glide.with(context).load(mealList.get(position).strMealThumb)
                .apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.new_logo3)
                .into(holder.cardImage);
        holder.favButton.setOnClickListener(v -> {
            if(holder.isFav){
                holder.favButton.setImageResource(R.drawable.favorite_border);
                onHomeListener.removeFromFav(mealList.get(position));
                holder.isFav = true;
            } else {
                holder.favButton.setImageResource(R.drawable.favorite_red);
                onHomeListener.addToFav(mealList.get(position));
                holder.isFav = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage,favButton;
        TextView cardTitle;
        boolean isFav;
        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            favButton = itemView.findViewById(R.id.fav_button);
            isFav = false;
        }
    }
}