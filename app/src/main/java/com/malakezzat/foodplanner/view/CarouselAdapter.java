package com.malakezzat.foodplanner.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malakezzat.foodplanner.R;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private List<String> dataList;

    public CarouselAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_meal, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.cardTitle.setText(dataList.get(position));

        holder.favButton.setOnClickListener(v -> {
            holder.favButton.setImageResource(R.drawable.favorite_red);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage,favButton;
        TextView cardTitle;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            favButton = itemView.findViewById(R.id.fav_button);
        }
    }
}