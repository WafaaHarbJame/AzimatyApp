package com.azimaty.azimatyapp.Model;

public class PsersonRating {
    private String username;
    private int favorite;
    private String rating_text;
    private String rating_date;
    private  String use_image;
    private int ratingnumber;

    public PsersonRating(String username, int favorite, String rating_text, String rating_date, String use_image, int ratingnumber) {
        this.username = username;
        this.favorite = favorite;
        this.rating_text = rating_text;
        this.rating_date = rating_date;
        this.use_image = use_image;
        this.ratingnumber = ratingnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getRating_text() {
        return rating_text;
    }

    public void setRating_text(String rating_text) {
        this.rating_text = rating_text;
    }

    public String getRating_date() {
        return rating_date;
    }

    public void setRating_date(String rating_date) {
        this.rating_date = rating_date;
    }

    public String getUse_image() {
        return use_image;
    }

    public void setUse_image(String use_image) {
        this.use_image = use_image;
    }

    public int getRatingnumber() {
        return ratingnumber;
    }

    public void setRatingnumber(int ratingnumber) {
        this.ratingnumber = ratingnumber;
    }
}
