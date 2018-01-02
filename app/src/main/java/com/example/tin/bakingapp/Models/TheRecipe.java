package com.example.tin.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TheRecipe implements Parcelable {

    private int id;
    private String name;
    private ArrayList<TheIngredients> ingredients;
    private ArrayList<TheSteps> steps;
    private int servings;
    private String image;

    public TheRecipe(int id, String name, ArrayList<TheIngredients> ingredients, ArrayList<TheSteps> steps, int servings, String image) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;

    }



    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeInt(id);
        out.writeString(name);
        out.writeTypedList(ingredients);
        out.writeTypedList(steps);
        out.writeInt(servings);
        out.writeString(image);
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private TheRecipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        //in.readList(ingredients, (com.example.tin.bakingapp.Models.TheIngredients.class.getClassLoader()));
        ingredients = in.createTypedArrayList(TheIngredients.CREATOR);
        //in.readList(steps, (com.example.tin.bakingapp.Models.TheSteps.class.getClassLoader()));
        steps = in.createTypedArrayList(TheSteps.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<TheRecipe> CREATOR = new Creator<TheRecipe>() {
        @Override
        public TheRecipe createFromParcel(Parcel in) {
            return new TheRecipe(in);
        }

        @Override
        public TheRecipe[] newArray(int size) {
            return new TheRecipe[size];
        }
    };

    /** GETTERS & SETTERS */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<TheIngredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<TheIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<TheSteps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<TheSteps> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

//    int id;
//    String name;
//    ArrayList ingredients;
//    ArrayList steps;
//    int servings;
//    String image;
//
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public ArrayList<TheIngredients> getIngredients() {
//        return ingredients;
//    }
//
//    public ArrayList<TheSteps> getSteps() {
//        return steps;
//    }
//
//    public int getServings() {
//        return servings;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    // empty constructor needed by the Parceler library
//    public TheRecipe() {
//    }
//
//    public TheRecipe(int id, String name, ArrayList ingredients, ArrayList steps, int servings, String image) {
//        this.id = id;
//        this.name = name;
//        this.ingredients = ingredients;
//        this.steps = steps;
//        this.servings = servings;
//        this.image = image;
//    }
//
//}