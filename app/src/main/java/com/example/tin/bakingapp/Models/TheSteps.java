package com.example.tin.bakingapp.Models;

import android.os.Parcelable;

public class TheSteps implements Parcelable{

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public TheSteps(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {

        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private TheSteps(android.os.Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    // In the vast majority of cases you can simply return 0 for this.
    // There are cases where you need to use the constant `CONTENTS_FILE_DESCRIPTOR`
    // But this is out of scope of this tutorial
    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Creator<TheSteps> CREATOR = new Creator<TheSteps>() {
        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public TheSteps createFromParcel(android.os.Parcel in) {
            return new TheSteps(in);
        }
        // We just need to copy this and change the type to match our class.
        @Override
        public TheSteps[] newArray(int size) {
            return new TheSteps[size];
        }
    };


    /** GETTERS & SETTERS */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}

//    int id;
//    String shortDescription;
//    String description;
//    String videoURL;
//    String thumbnailURL;
//
//    public int getId() {
//        return id;
//    }
//
//    public String getShortDescription() {
//        return shortDescription;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getVideoURL() {
//        return videoURL;
//    }
//
//    public String getThumbnailURL() {
//        return thumbnailURL;
//    }
//
//    // empty constructor needed by the Parceler library
//    public TheSteps() {
//
//    }
//
//    public TheSteps(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
//        this.id = id;
//        this.shortDescription = shortDescription;
//        this.description = description;
//        this.videoURL = videoURL;
//        this.thumbnailURL = thumbnailURL;
//    }
//
//}