package com.azimaty.azimatyapp.Model;

public class Items_image_service {
    private  int id;
    private int favooirte;
    private  String image;

    public Items_image_service(int id, int favooirte, String image) {
        this.id = id;
        this.favooirte = favooirte;
        this.image = image;
    }

    public Items_image_service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavooirte() {
        return favooirte;
    }

    public void setFavooirte(int favooirte) {
        this.favooirte = favooirte;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
