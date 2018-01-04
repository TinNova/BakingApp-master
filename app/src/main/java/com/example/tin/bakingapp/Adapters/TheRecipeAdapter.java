package com.example.tin.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tin.bakingapp.Models.TheRecipe;
import com.example.tin.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TheRecipeAdapter extends RecyclerView.Adapter<TheRecipeAdapter.ViewHolder> {

    private final List<TheRecipe> recipes;
    private final Context context;

    private final ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void onListItemClick (int clickedItemIndex);
    }

    public TheRecipeAdapter(List<TheRecipe> recipes, Context context, ListItemClickListener listener) {
        this.recipes = recipes;
        this.context = context;
        mOnClickListener = listener;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new View and inflate the list_item Layout into it
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipes_list_item, viewGroup, false);

        // Return the View we just created
        return new ViewHolder(v);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position.
     *
     * @param viewHolder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        TheRecipe theRecipe = recipes.get(position);

        viewHolder.tvRecipeName.setText(theRecipe.getName());

        // if theRecipe.getImage is empty, use the pie image from the @Drawable, else us the image in the json
        if (theRecipe.getImage().isEmpty()) {

            Picasso.with(context).load(R.drawable.pie).into(viewHolder.ivRecipeImage);

        } else {

            Picasso.with(context).load(theRecipe.getImage()).into(viewHolder.ivRecipeImage);

        }

    }

    @Override
    public int getItemCount() {
        // This is code provided by the reviewer, the original code, found above would allow a
        // null exception to occur and crash the app if there wasn't an internet connection. This
        // code checks if the recipes are null first, but I don't understand the way it's written
        // and the syntax behind it

        // condition ? passed : failed
        return (recipes != null) ? recipes.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivRecipeImage;
        final TextView tvRecipeName;

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }

        public ViewHolder(View itemView) {
            super(itemView);

            ivRecipeImage = itemView.findViewById(R.id.recipe_thumbNail);
            tvRecipeName = itemView.findViewById(R.id.recipe_title);
            // This code tells the clickListener to listen for click on this ViewHolder
            itemView.setOnClickListener(this);

        }

    }
}