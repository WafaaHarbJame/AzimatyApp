package com.azimaty.azimatyapp.Model;

import java.util.List;

public class Item {
    private String Family_name;
    private  String Family_image;
    private  int RatingNumber;
    private  String location;
    private List<SubItem> subItemList;


    public Item() {
    }

    public Item(String family_name, String family_image, int ratingNumber, String location, List<SubItem> subItemList) {
        Family_name = family_name;
        Family_image = family_image;
        RatingNumber = ratingNumber;
        this.location = location;
        this.subItemList = subItemList;
    }

    public String getFamily_name() {
        return Family_name;
    }

    public void setFamily_name(String family_name) {
        Family_name = family_name;
    }

    public String getFamily_image() {
        return Family_image;
    }

    public void setFamily_image(String family_image) {
        Family_image = family_image;
    }

    public int getRatingNumber() {
        return RatingNumber;
    }

    public void setRatingNumber(int ratingNumber) {
        RatingNumber = ratingNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<SubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }
}

