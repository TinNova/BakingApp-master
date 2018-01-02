package com.example.tin.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.tin.bakingapp.Activities.MainActivity;
import com.example.tin.bakingapp.Models.TheIngredients;
import com.example.tin.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeServiceFactory(this.getApplicationContext());
    }

    private class RecipeServiceFactory implements RemoteViewsService.RemoteViewsFactory {

        private final Context context;
        private ArrayList<TheIngredients> mTheIngredients;

        RecipeServiceFactory(Context context) {
            this.context = context;
            mTheIngredients = new ArrayList<>();
            mTheIngredients.add(new TheIngredients(1, "ml", "milk"));
        }


        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String theIngredientsJson = preferences.getString(MainActivity.SHARED_PREFERENCES_KEY, "");
            if (!theIngredientsJson.equals("")) {
                Gson gson = new Gson();
                mTheIngredients = gson.fromJson(theIngredientsJson, new TypeToken<ArrayList<TheIngredients>>() {
                }.getType());
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mTheIngredients != null) {
                return mTheIngredients.size();
            } else return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_item);
            rv.setTextViewText(R.id.widget_ingredient_tv, String.valueOf(mTheIngredients.get(i).getIngredient()));
            rv.setTextViewText(R.id.widget_measure_tv, String.valueOf(mTheIngredients.get(i).getMeasure()));
            rv.setTextViewText(R.id.widget_quantity_tv, String.valueOf(mTheIngredients.get(i).getQuantity()));
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
