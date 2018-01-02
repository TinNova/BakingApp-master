package com.example.tin.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tin.bakingapp.Models.TheSteps;
import com.example.tin.bakingapp.R;

import java.util.ArrayList;


public class TheStepsAdapter extends RecyclerView.Adapter<TheStepsAdapter.ViewHolder> {

    private final ArrayList<TheSteps> mStepsList;
    private final Context mContext;
    private final TheStepsClickListener mClickListener;

    // Interface that sends information to the StepsFragment
    // We need to send back to the StepsFragment the position and the step that was clicked on
    // ? Do we actually need the position? Surely only the step matters...If we have the step why do we need the position?
    public interface TheStepsClickListener {
        void onClick(int position, TheSteps theSteps);
    }

    public TheStepsAdapter(ArrayList<TheSteps> stepsList, Context context, TheStepsClickListener listener) {
        this.mStepsList = stepsList;
        this.mContext = context;
        this.mClickListener = listener;

    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new View and inflate the list_item Layout into it
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.steps_list_item, viewGroup, false);
        // Return the view just created
        return new TheStepsAdapter.ViewHolder(v);
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
        // Here we define that we want the viewHolder to hold only the list of the step that we are
        // currently on, i.e .get(position)
        TheSteps theSteps = mStepsList.get(position);

        // Here we are getting the ShortDescription from this current step and setting it to the
        // TextView tvShortDescription
        viewHolder.tvShortDescription.setText(theSteps.getShortDescription());

        // Here we are getting the current step and setting it to the ArrayList mCurrentStep so it
        // can be passed to the onClickListener in order to allow us to pass it back to the
        // StepsFragment where it can be used to launch the DetailFragment
        viewHolder.mCurrentStep = theSteps;
    }



    @Override
    public int getItemCount() {
        return mStepsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView tvShortDescription;
        TheSteps mCurrentStep;

        public ViewHolder(View itemView) {
            super(itemView);

            tvShortDescription = itemView.findViewById(R.id.short_description);
            // Here we are ensuring that the OnClickListener is taking the click of this itemView
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            // Here we are passing in the position and mCurrentStep
            mClickListener.onClick(getAdapterPosition(), mCurrentStep);
        }
    }
}
