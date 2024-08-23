package com.malakezzat.foodplanner.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.malakezzat.foodplanner.R;

import java.util.Objects;

public class MealTypeDialog {

    public static void showMealSelectionDialog(Context context, OnItemSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.meal_type_dialog, null);
        dialogView.setBackgroundResource(R.drawable.rounded_dialog_background);
        builder.setView(dialogView);

        Spinner spinnerOptions = dialogView.findViewById(R.id.spinnerOptions);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        String[] items = {"Breakfast", "Lunch", "Dinner"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnConfirm.setOnClickListener(v -> {
            String selectedItem = (String) spinnerOptions.getSelectedItem();
            listener.onItemSelected(selectedItem);
            dialog.dismiss();  // Dismiss the dialog
            Toast.makeText(context, R.string.added_to_week_plan, Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String selectedItem);
    }
}
