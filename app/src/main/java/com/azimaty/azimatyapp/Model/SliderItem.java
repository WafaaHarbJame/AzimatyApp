package com.azimaty.azimatyapp.Model;

public class SliderItem {

    private String imageUrl;
    private String desc;


    public SliderItem(String imageUrl, String desc) {
        this.imageUrl = imageUrl;
        this.desc = desc;
    }

    public SliderItem() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}