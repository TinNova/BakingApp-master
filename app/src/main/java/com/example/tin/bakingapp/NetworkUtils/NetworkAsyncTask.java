package com.example.tin.bakingapp.NetworkUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tin.bakingapp.Models.TheIngredients;
import com.example.tin.bakingapp.Models.TheRecipe;
import com.example.tin.bakingapp.Models.TheSteps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkAsyncTask extends AsyncTask<Void, Void, ArrayList<TheRecipe>> {

    private static final String TAG = NetworkAsyncTask.class.getSimpleName();

    private final NetworkAsyncTaskListener networkAsyncTaskListener;

    public NetworkAsyncTask(NetworkAsyncTaskListener listener) {
        networkAsyncTaskListener = listener;
    }

    @Override
    protected ArrayList<TheRecipe> doInBackground(Void... voids) {
        // Initialise the Model Class TheRecipe and name it "theRecipeContent"
        ArrayList<TheRecipe> theRecipeContent = new ArrayList<>();
        /** Initialise the data we got from the BAKING_APP_URL and name it "recipeJsonArray" */
        JSONArray recipeJsonArray = NetworkConnection.getRecipeData();
        try {
            // Using a For Loop to get each instance of a recipe from the recipeJsonArray
            for (int i = 0; i < recipeJsonArray.length(); i++) {
                // Once an instance of a recipe is collected save it as a JsonObject and name it "currentRecipe"
                JSONObject currentRecipe = recipeJsonArray.getJSONObject(i);

                // Here we are saving each element of the JsonObject "currentRecipe" into the
                // Model Class TheRecipe
                int recipeId = currentRecipe.getInt("id");
                String recipeName = currentRecipe.getString("name");

                /** Handling The Ingredients */
                // The ingredients are an array so save it into a JsonArray and name it "ingredientsArray"
                JSONArray ingredientsArray = currentRecipe.getJSONArray("ingredients");

                // Initialise the Model Class TheIngredients and name it "ingredientsList"
                ArrayList<TheIngredients> ingredientList = new ArrayList<>();
                // Using a For Loop to get each instance of an ingredient from the ingredientsArray
                for (int ii = 0; ii < ingredientsArray.length(); ii++) {
                    // Once an instance of an ingredient is collected save it as a JsonObject and name it "currentIngredient"
                    JSONObject currentIngredient = ingredientsArray.getJSONObject(ii);

                    // Here we are saving each element of the JsonObject "currentIngredient" into the
                    // Model Class TheIngredients
                    int ingQuantity = currentIngredient.getInt("quantity");
                    String ingMeasure = currentIngredient.getString("measure");
                    String ingName = currentIngredient.getString("ingredient");
                    // Saving the current set of ingredients as an ArrayList called "theIngredients"
                    TheIngredients theIngredients = new TheIngredients(ingQuantity, ingMeasure, ingName);
                    // Adding the newly created list of current ingredients "theIngredients" into
                    // the list ingredientList
                    ingredientList.add(theIngredients);
                }

                /** Handling The Steps */
                // The ingredients are also an array, so save it into a JsonArray and name it "stepsArray"
                JSONArray stepsArray = currentRecipe.getJSONArray("steps");

                // Initialise the Model Class TheSteps and name it "stepsList"
                ArrayList<TheSteps> stepsList = new ArrayList<>();

                // Using a For Loop to get each instance of a steps from the stepsArray
                for (int iii = 0; iii < stepsArray.length(); iii++) {

                    // Once an instance of a step is collected save it as a JsonObject and name it "currentStep"
                    JSONObject currentStep = stepsArray.getJSONObject(iii);

                    // Here we are saving each element of the JsonObject "currentStep" into the
                    // Model Class TheSteps
                    int stepId = currentStep.getInt("id");
                    String stepShortDescription = currentStep.getString("shortDescription");
                    String stepLongDescription = currentStep.getString("description");
                    String stepVideoUrl = currentStep.getString("videoURL");
                    String stepTmbNailUrl = currentStep.getString("thumbnailURL");
                    // Saving the current set of ingredients as an ArrayList called "theSteps"
                    TheSteps theSteps = new TheSteps(stepId, stepShortDescription, stepLongDescription, stepVideoUrl, stepTmbNailUrl);
                    // Adding the newly created list of current steps "theSteps" into
                    // the list stepsList
                    stepsList.add(theSteps);
                }

                /** Completing The Recipe */
                // Here we are continuing to add each element of the JsonObject "currentRecipe"
                // into the Model Class TheRecipe because this data came after the Arrays above
                int recipeServings = currentRecipe.getInt("servings");
                String recipeImage = currentRecipe.getString("image");
                // Saving the current recipe including the ingredientList and stepsList as an ArrayList called "theRecipe"
                TheRecipe theRecipe = new TheRecipe(recipeId, recipeName, ingredientList, stepsList, recipeServings, recipeImage);
                // Adding the newly created recipe "theRecipe" into the list theRecipeContent
                theRecipeContent.add(theRecipe);


                Log.v(TAG, "theRecipeContent Json: " + theRecipeContent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Json Exception: " + e);
        }
        return theRecipeContent;
    }

    @Override
    protected void onPostExecute(ArrayList<TheRecipe> theRecipeContent) {
        super.onPostExecute(theRecipeContent);
        networkAsyncTaskListener.returnData(theRecipeContent);
    }

}
