package com.jamaatna.jamaatnaapp.Model;

public class Catogoriies {
    String cat_name;
    String cat_photo;


    public Catogoriies(String cat_name, String cat_photo) {
        this.cat_name = cat_name;
        this.cat_photo = cat_photo;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_photo() {
        return cat_photo;
    }

    public void setCat_photo(String cat_photo) {
        this.cat_photo = cat_photo;
    }
}
