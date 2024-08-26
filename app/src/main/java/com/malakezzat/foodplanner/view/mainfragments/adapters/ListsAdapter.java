package com.malakezzat.foodplanner.view.mainfragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.malakezzat.foodplanner.R;
import com.malakezzat.foodplanner.model.data.Category;
import com.malakezzat.foodplanner.model.data.Data;
import com.malakezzat.foodplanner.model.data.Meal;
import com.malakezzat.foodplanner.view.mainfragments.listeners.OnListsListener;

import java.util.ArrayList;
import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> {

    private List<Meal> countries;
    private List<Category> categories;
    private OnListsListener onListsListener;
    private Context context;
    public static final int COUNTRIES = 1;
    public static final int CATEGORIES = 2;
    private int source;

    public ListsAdapter(Context context , List<? extends Data> items
            , OnListsListener onListsListener, FragmentTransaction fragmentTransaction) {
        this.context = context;
        countries = new ArrayList<>();
        categories = new ArrayList<>();
        if(!items.isEmpty()) {
            if (items.get(0) instanceof Meal) {
                countries = (List<Meal>) items;
                source = COUNTRIES;
            } else if (items.get(0) instanceof Category) {
                categories = (List<Category>) items;
                source = CATEGORIES;
            }
        }
        this.onListsListener = onListsListener;
    }


    @NonNull
    @Override
    public ListsAdapter.ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new View(context);
        if(source == COUNTRIES) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_row2, parent, false);
        } else if(source == CATEGORIES){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_row, parent, false);
        }
        return new ListsAdapter.ListsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsAdapter.ListsViewHolder holder, int position) {
        if(source == COUNTRIES) {
            holder.cardTitle.setText(countries.get(position).strArea);
            holder.cardView.setOnClickListener(v->{
                onListsListener.onListItemClicked(countries.get(position));
            });
        } else if(source == CATEGORIES){
            holder.cardTitle.setText(categories.get(position).strCategory);
            Glide.with(context).load(categories.get(position).strCategoryThumb)
                    .apply(new RequestOptions().override(200,200))
                    .placeholder(R.drawable.new_logo3)
                    .into(holder.cardImage);
            holder.cardView.setOnClickListener(v->{
                onListsListener.onListItemClicked(categories.get(position));
            });
        }
    }



    @Override
    public int getItemCount() {
        if(source == COUNTRIES) {
            return countries.size();
        } else if(source == CATEGORIES){
            return categories.size();
        }
        return 0;
    }

    public static class ListsViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage,favButton;
        TextView cardTitle;
        CardView cardView;
        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            cardView = itemView.findViewById(R.id.item_list);
        }
    }

}
