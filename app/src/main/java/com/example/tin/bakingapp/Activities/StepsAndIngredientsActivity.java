package com.example.tin.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.tin.bakingapp.Fragments.DetailFragment;
import com.example.tin.bakingapp.Fragments.StepsFragment;
import com.example.tin.bakingapp.Models.TheIngredients;
import com.example.tin.bakingapp.Models.TheSteps;
import com.example.tin.bakingapp.R;

import java.util.ArrayList;

public class StepsAndIngredientsActivity extends AppCompatActivity {

    private static final String TAG = StepsAndIngredientsActivity.class.getSimpleName();

    public static final String INGREDIENTS_BUNDLED = "argsForIngredients";
    public static final String STEPS_BUNDLED = "argsForSteps";
    public static final String TWO_PANE = "argsForTwoPain";

    // mTwoPan tells us if the device has a minimum width of 600dp
    private boolean mTwoPane;

    private ArrayList<TheSteps> mTheSteps;
    private ArrayList<TheIngredients> mTheIngredients;

    private StepsFragment mStepsFragment;
    DetailFragment mDetailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_and_detail);

        // If the activity_steps_and_detail layout contains the container "detail_container" it means
        // the minimum screen width is 600dp, therefore we are in twoPane mode
        if (findViewById(R.id.detail_container) !=null){

            // if true, inflate the StepsFragment & the DetailFragment into this activity
            mTwoPane = true;

        } else {

            // if false, only inflate the StepsFragment
            mTwoPane = false;
        }

        Intent intentThatStartedThisActivity = getIntent();

        // Here we've taken the Extra containing the the "TheRecipe" Model and put it in the variable "mTheRecipe"
        mTheSteps = intentThatStartedThisActivity.getParcelableArrayListExtra("steps");
        mTheIngredients = intentThatStartedThisActivity.getParcelableArrayListExtra("ingredients");

        Log.v(TAG, "The Ingredients inside mTheIngredients: " + mTheIngredients);
        Log.v(TAG, "The Steps inside mTheSteps:" + mTheSteps);

        // Only create a new fragment if there is no previously saved state!
        // If there savedInstanceState is NOT null then do nothing as the Fragment is already inflated
        if (savedInstanceState == null) {

            inflateStepsFragment();
        }

    }


    // Ingredients & Steps Data Goes Into TheStepsFragment
    private void inflateStepsFragment() {
        // Creating a Bundle to hold the ingredientsList & stepsList
        Bundle argsForIngredientsSteps = new Bundle();
        argsForIngredientsSteps.putParcelableArrayList(INGREDIENTS_BUNDLED, mTheIngredients);
        argsForIngredientsSteps.putParcelableArrayList(STEPS_BUNDLED, mTheSteps);
        argsForIngredientsSteps.putBoolean(TWO_PANE, mTwoPane);

        // If we have successfully passed the selected TheIngredient to this activity, begin the
        // transaction to start the StepsFragment
        if (mTheIngredients != null) {

            // Initialise the StepsFragment
            mStepsFragment = new StepsFragment();
            // Placing the Bundle Arguments into the stepsFragment
            mStepsFragment.setArguments(argsForIngredientsSteps);

            //fragmentManager = getSupportFragmentManager();
            // Start inflate the StepsFragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.steps_container, mStepsFragment)
                    .commit();
        } else {
            Toast.makeText(this, "Unable To Launch Fragment Transaction", Toast.LENGTH_SHORT).show();
        }
    }


//    // Ingredients & Steps Data Goes Into TheStepsFragment
//    private void inflateStepsAndDetailFragment() {
//
//        inflateStepsFragment();
//
//        // Initialise the StepsFragment
//        mDetailFragment = new DetailFragment();
//        // Placing the Bundle Arguments into the stepsFragment
//        //mDetailFragment.setArguments(argsForDetailFragment);
//
//        // Start inflate the StepsFragment
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.detail_container, mDetailFragment)
//                .commit();
//    }
}

