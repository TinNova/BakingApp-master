package com.example.tin.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.tin.bakingapp.Adapters.TheRecipeAdapter;
import com.example.tin.bakingapp.Models.TheIngredients;
import com.example.tin.bakingapp.Models.TheRecipe;
import com.example.tin.bakingapp.NetworkUtils.NetworkAsyncTask;
import com.example.tin.bakingapp.NetworkUtils.NetworkAsyncTaskListener;
import com.example.tin.bakingapp.R;
import com.example.tin.bakingapp.Widget.RecipeWidgetProvider;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NetworkAsyncTaskListener, TheRecipeAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String DATA_KEY = "data_key";
    public static final String SHARED_PREFERENCES_KEY = "shared_preferences_key";

    // Needed for the RecyclerView
    // This will be used to attach the RecyclerView to the Adapter
    @BindView(R.id.recyclerView_recipes)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter recipeAdapter;

    // Used to check if the device has internet connection
    private ConnectivityManager connectionManager;
    private NetworkInfo networkInfo;

    // RecipeContent for use outside of the listener
    private ArrayList<TheRecipe> mTheRecipeContent;
    private ArrayList<TheIngredients> mTheIngredients;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /** Setting Up The RecyclerView to Display All of The Recipes In the App */
        // This will improve performance by stating that changes in the content will not change
        // the child layout size in the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        /*
         * A LayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView as well as determining the policy for when to recycle item views that
         * are no longer visible to the user.
         */
        LinearLayoutManager mLinearLayoutManager =
                new LinearLayoutManager(this);
        // Set the mRecyclerView to the layoutManager so it can handle the positioning of the items
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        // If There isn't a savedInstanceState, Download The Data And Build The RecyclerView
        if (savedInstanceState == null) {
            Log.v(TAG, "Data is loading");

            // Checking If The Device Is Connected To The Internet
            connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            // If the connManager and networkInfo is NOT null, launch the AsyncTask
            if (connectionManager != null)
                networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                NetworkAsyncTask getData = new NetworkAsyncTask((NetworkAsyncTaskListener) this);
                getData.execute();

            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//            // Else if the connManager and networkInfo IS null, show a snakeBar informing the user
//            Snackbar snackbar = Snackbar.make(mNavigationView, getString(R.string.check_connection), Snackbar.LENGTH_LONG);
//            View snackBarView = snackbar.getView();
//            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
//            snackbar.show();
            }

            // If there is a savedInstanceState, don't do anything as the data and RecyclerView is there already
        } else {
            // Get TheRecipe list that's saved in savedInstanceState and add it to the variable mTheRecipeContent
            mTheRecipeContent = savedInstanceState.getParcelableArrayList(DATA_KEY);

            // Pass the mTheRecipeContent into the recipeAdapter to create the RecyclerView
            recipeAdapter = new TheRecipeAdapter(mTheRecipeContent, getApplicationContext(), MainActivity.this);
            mRecyclerView.setAdapter(recipeAdapter);

            // Test to see if savedInstanceState is being saved
            Log.v(TAG, "SavedInstanceState Discovered, Don't Reload Data");

        }
    }


    // Listener that returns data from the AsycTask NetworkAsyncTask
    // This method can be used throughout the Class to deliver the returned data
    @Override
    public void returnData(ArrayList<TheRecipe> theRecipeContent) {

        mTheRecipeContent = theRecipeContent;
        connectAdapterToData();

        Log.d(TAG, "List of Recipes: " + theRecipeContent);

    }

    // Code which put the mTheRecipeContent into the Adapter to create the RecyclerView
    private void connectAdapterToData() {
        recipeAdapter = new TheRecipeAdapter(mTheRecipeContent, getApplicationContext(), MainActivity.this);
        mRecyclerView.setAdapter(recipeAdapter);
    }

    /** This Only Works If You Implement: implements TheRecipeAdapter.ListItemClickListener to this Class */
    @Override
    public void onListItemClick(int clickedItemIndex) {

        Log.d(TAG, "List of Recipes in onListItemClick: " + mTheRecipeContent.get(clickedItemIndex));

        Log.v(TAG, "clickedItemIndex" + clickedItemIndex);
        Toast.makeText(this, "ClickItemIndex: " + clickedItemIndex, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, StepsAndIngredientsActivity.class);
        intent.putParcelableArrayListExtra("steps", mTheRecipeContent.get(clickedItemIndex).getSteps());
        intent.putParcelableArrayListExtra("ingredients", mTheRecipeContent.get(clickedItemIndex).getIngredients());

        // Getting Ingredients To Be Passed To The Widget
        mTheIngredients = mTheRecipeContent.get(clickedItemIndex).getIngredients();
        makeWidgetData(mTheIngredients);

        //makeWidgetData(mTheIngredients);
        sendBroadcast();

        //passTheIngredientsToWidgetProvider(quantity, ingredient, measure);
        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Saving theRecipeContent to be reused should the device rotate or return to this activity
        outState.putParcelableArrayList(DATA_KEY, mTheRecipeContent);
        super.onSaveInstanceState(outState);
    }

    // Takes ClickedOn TheIngredients ArrayList And Converts It To Json
    // Then Passes The Json To The RecipeWidgetService, this will display the selected ingredients
    // in the Widget
    private void makeWidgetData(ArrayList<TheIngredients> theIngredients) {
        // Initialise a Gson
        Gson gson = new Gson();
        // Convert theIngredients to a Json using the Gson Library
        String theIngredientsJson = gson.toJson(theIngredients);
        // Initialise SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        // Pass in theIngredients in Json format
        editor.putString(SHARED_PREFERENCES_KEY, theIngredientsJson).apply();

    }

    private void sendBroadcast() {
        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE\"");
        sendBroadcast(intent);
    }
}

//TODO: Tidy up the design in Tab mode
//TODO: Make the video auto change to full screen on app rotation