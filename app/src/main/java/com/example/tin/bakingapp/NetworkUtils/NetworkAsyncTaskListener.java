package com.example.tin.bakingapp.NetworkUtils;

import com.example.tin.bakingapp.Models.TheRecipe;

import java.util.ArrayList;


public interface NetworkAsyncTaskListener {
    void returnData(ArrayList<TheRecipe> theRecipeContent);

}
