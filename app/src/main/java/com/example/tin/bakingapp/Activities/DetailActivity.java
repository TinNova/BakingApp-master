package com.example.tin.bakingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tin.bakingapp.Fragments.DetailFragment;
import com.example.tin.bakingapp.Models.TheSteps;
import com.example.tin.bakingapp.R;

import java.util.ArrayList;

import static com.example.tin.bakingapp.Fragments.StepsFragment.CURRENT_POSITION;
import static com.example.tin.bakingapp.Fragments.StepsFragment.CURRENT_STEP;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private DetailFragment mDetailFragment;
    private int mPosition;

    private ArrayList<TheSteps> mTheSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Extracting Data from the Intent that started this activity
        Intent intentThatStartedThisActivity = getIntent();

        // Here we've taken the Extra containing the the "TheSteps" Model and put it in the variable mTheSteps
        mTheSteps = intentThatStartedThisActivity.getParcelableArrayListExtra(CURRENT_STEP);
        mPosition = intentThatStartedThisActivity.getIntExtra(CURRENT_POSITION, 0);

        Log.v(TAG, "The Ingredients inside mTheIngredients: " + mTheSteps);
        Log.v(TAG, "The Steps inside mTheSteps:" + mPosition);

        // Only create a new fragment if there is no previously saved state!
        if (savedInstanceState == null) {

            inflateDetailFragment();
        }

    }


    // Ingredients & Steps Data Goes Into TheStepsFragment
    private void inflateDetailFragment() {
        // Creating a Bundle to hold the ingredientsList & stepsList
        Bundle argsForDetailFragment = new Bundle();
        argsForDetailFragment.putParcelableArrayList(CURRENT_STEP, mTheSteps);
        argsForDetailFragment.putInt(CURRENT_POSITION, mPosition);

        // If we have successfully passed the selected TheIngredient to this activity, begin the
        // transaction to start the StepsFragment
        if (mTheSteps != null) {

            // Initialise the StepsFragment
            mDetailFragment = new DetailFragment();
            // Placing the Bundle Arguments into the stepsFragment
            mDetailFragment.setArguments(argsForDetailFragment);

            // Start inflate the StepsFragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, mDetailFragment)
                    .commit();
        } else {
            Toast.makeText(this, "Unable To Launch Fragment Transaction", Toast.LENGTH_SHORT).show();
        }
    }
}
