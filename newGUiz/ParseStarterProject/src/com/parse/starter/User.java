package com.parse.starter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by weird_000 on 4/4/2015.
 */
public class User {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("bio")
    private String bio;

    @SerializedName("image")
    private String image;

    @SerializedName("tutor")
    private boolean tutor;

    @SerializedName("tutor_availability")
    private boolean tutor_availability;

    @SerializedName("tutor_rating")
    private float tutor_rating;

    @SerializedName("tutor_fields")
    private String tutor_fields;

    @SerializedName("tutor_price")
    private float tutor_price;

    @SerializedName("tutor_location")
    private String tutor_location;

    @SerializedName("longitude")
    private float longitude;

    @SerializedName("latitude")
    private float latitude;

    public User(String email, String password, String name, String bio, String image, boolean tutor, boolean tutor_availability, float tutor_rating, String tutor_fields, float tutor_price, String tutor_location, float longitude, float latitude) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.tutor = tutor;
        this.tutor_availability = tutor_availability;
        this.tutor_rating = tutor_rating;
        this.tutor_fields = tutor_fields;
        this.tutor_price = tutor_price;
        this.tutor_location = tutor_location;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public User(String email, String password, String name, float longitude, float latitude) {
        this(email, password, name, null, null, false, false, 0.0f, null, 0.0f, null, longitude, latitude);
    }
    public User(String email, String password, float longitude, float latitude) {
        this(email, password, null, null, null, false, false, 0.0f, null, 0.0f, null, longitude, latitude);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isTutor() {
        return tutor;
    }

    public void setTutor(boolean tutor) {
        this.tutor = tutor;
    }

    public boolean isTutor_availability() {
        return tutor_availability;
    }

    public void setTutor_availability(boolean tutor_availability) {
        this.tutor_availability = tutor_availability;
    }

    public float getTutor_rating() {
        return tutor_rating;
    }

    public void setTutor_rating(float tutor_rating) {
        this.tutor_rating = tutor_rating;
    }

    public String getTutor_fields() {
        return tutor_fields;
    }

    public void setTutor_fields(String tutor_fields) {
        this.tutor_fields = tutor_fields;
    }

    public float getTutor_price() {
        return tutor_price;
    }

    public void setTutor_price(float tutor_price) {
        this.tutor_price = tutor_price;
    }

    public String getTutor_location() {
        return tutor_location;
    }

    public void setTutor_location(String tutor_location) {
        this.tutor_location = tutor_location;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
