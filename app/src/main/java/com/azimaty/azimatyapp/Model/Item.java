package com.azimaty.azimatyapp.Model;

import java.util.List;

public class Item {
    private int service_id;
    private String Family_name;
    private  String Family_image;
    private  int RatingNumber;
    private  String location;
    private List<SubItem> subItemList;


    public Item() {
    }

    public Item(int service_id, String family_name, String family_image, int ratingNumber, String location, List<SubItem> subItemList) {
        this.service_id = service_id;
        Family_name = family_name;
        Family_image = family_image;
        RatingNumber = ratingNumber;
        this.location = location;
        this.subItemList = subItemList;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
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

