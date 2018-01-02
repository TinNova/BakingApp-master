package com.example.tin.bakingapp.NetworkUtils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkConnection {

    private static final String TAG = NetworkConnection.class.getSimpleName();
    // The BAKING_APP_URL
    private static final String BAKING_APP_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    /** OkHttp Library Used To Generate A JSONArray From The BAKING_APP_URL
     * The librart is usefull as it automatically handles the most common network connection errors */
    public static JSONArray getRecipeData() {

        // First, Create An OkHttpClient named "client"
        OkHttpClient client = new OkHttpClient();

        // Second, Create a Request using the BAKING_APP_URL and name it "requestBakingAppData@
        Request requestBakingAppData = new Request.Builder()
                .url(BAKING_APP_URL)
                .build();

        try {
            // Third, Create a Response to capture the response from the network by passing in the
            // request we created above call "requestBakingAppData" and execute it. Name it "response"
            Response response = client.newCall(requestBakingAppData).execute();

            // If a successful response is returned save it body of the response as a String within
            // a JSONArray
            return new JSONArray(response.body().string());

            // If there are complications, catch them and print them to the log
        } catch (IOException | JSONException e) {
            e.printStackTrace();

            Log.e(TAG, "EXCEPTION IS " + e);
        }
        return null;
    }
}
