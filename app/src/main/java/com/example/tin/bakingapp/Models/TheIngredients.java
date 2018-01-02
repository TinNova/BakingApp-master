package com.example.tin.bakingapp.Models;

import android.os.Parcelable;

public class TheIngredients implements Parcelable {

    private int quantity;
    private String measure;
    private String ingredient;

    public TheIngredients(int quantity, String measure, String ingredient) {

        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }


    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private TheIngredients(android.os.Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    // In the vast majority of cases you can simply return 0 for this.
    // There are cases where you need to use the constant `CONTENTS_FILE_DESCRIPTOR`
    // But this is out of scope of this project
    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Creator<TheIngredients> CREATOR = new Creator<TheIngredients>() {
        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public TheIngredients createFromParcel(android.os.Parcel in) {
            return new TheIngredients(in);
        }
        // We just need to copy this and change the type to match our class.
        @Override
        public TheIngredients[] newArray(int size) {
            return new TheIngredients[size];
        }
    };


    /** GETTERS & SETTERS */
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}

//    int quantity;
//    String measure;
//    String ingredient;
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public String getMeasure() {
//        return measure;
//    }
//
//    public String getIngredient() {
//        return ingredient;
//    }
//
//    // empty constructor needed by the Parceler library
//    public TheIngredients() {
//
//    }
//
//    public TheIngredients(int quantity, String measure, String ingredient) {
//        this.quantity = quantity;
//        this.measure = measure;
//        this.ingredient = ingredient;
//    }
//
//
//}