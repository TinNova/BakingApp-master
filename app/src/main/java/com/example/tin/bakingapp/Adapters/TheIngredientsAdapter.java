package com.example.tin.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tin.bakingapp.Models.TheIngredients;
import com.example.tin.bakingapp.R;

import java.util.ArrayList;


public class TheIngredientsAdapter extends RecyclerView.Adapter<TheIngredientsAdapter.ViewHolder> {

    private final ArrayList<TheIngredients> mIngredientsList;
    private final Context mContext;

    public TheIngredientsAdapter(ArrayList<TheIngredients> ingredientsList, Context context) {
        this.mIngredientsList = ingredientsList;
        this.mContext = context;
    }

    @Override
    public TheIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new View and inflate the list_item Layout into it
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ingredients_list_item, viewGroup, false);
        // Return the view just created
        return new TheIngredientsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TheIngredientsAdapter.ViewHolder viewHolder, int position) {

        TheIngredients theIngredients = mIngredientsList.get(position);

        viewHolder.tvIngredient.setText(theIngredients.getIngredient());
        viewHolder.tvMeasure.setText(theIngredients.getMeasure());
        viewHolder.tvQuantity.setText(String.valueOf(theIngredients.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvIngredient;
        final TextView tvMeasure;
        final TextView tvQuantity;

        public ViewHolder(View itemView) {
            super(itemView);

            tvIngredient = itemView.findViewById(R.id.ingredients_tv);
            tvMeasure = itemView.findViewById(R.id.measure_tv);
            tvQuantity = itemView.findViewById(R.id.quantity_tv);
        }
    }
}
