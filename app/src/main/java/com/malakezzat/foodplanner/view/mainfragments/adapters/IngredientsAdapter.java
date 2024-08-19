package com.malakezzat.foodplanner.view.mainfragments.adapters;

import android.content.Context;
import android.util.Log;
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
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Ingredient;
import com.malakezzat.foodplanner.model.data.IngredientList;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnListsListener;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private static final String TAG = "IngredientsAdapter";
    private List<Ingredient> ingredients;
    private Context context;

    public IngredientsAdapter(Context context, List<Ingredient> items) {
        this.context = context;
        ingredients = items;

    }


    @NonNull
    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsAdapter.IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        Glide.with(context).load(ingredients.get(position).getImageUrl())
                .apply(new RequestOptions().override(200,200))
                .placeholder(R.drawable.new_logo3)
                .into(holder.ingredientImage);
        holder.ingredientName.setText(ingredients.get(position).getIngredient());
        holder.ingredientMeasure.setText(ingredients.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientImage;
        TextView ingredientName,ingredientMeasure;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImage = itemView.findViewById(R.id.ingredient_image);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientMeasure = itemView.findViewById(R.id.ingredient_measure);

        }
    }

}
