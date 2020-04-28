package com.jamaatna.jamaatnaapp.Model;

import java.util.List;

public class Item {
    private int service_id;
    private int item_id;
    private int list_id;
    private String Family_name;
    private  String Family_image;
    private  int RatingNumber;
    private  String location;
    private List<SubItem> subItemList;


    public Item() {
    }

    public Item(int service_id, int item_id, int list_id, String family_name, String family_image, int ratingNumber, String location, List<SubItem> subItemList) {
        this.service_id = service_id;
        this.item_id = item_id;
        this.list_id = list_id;
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

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getList_id() {
        return list_id;
    }

    public void setList_id(int list_id) {
        this.list_id = list_id;
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

