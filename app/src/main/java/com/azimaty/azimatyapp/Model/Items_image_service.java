package com.azimaty.azimatyapp.Model;

public class Items_image_service {
    private  int id;
    private int favooirte;
    private  String image;
    private  int List_id;

    public Items_image_service(int id, int favooirte, String image, int list_id) {
        this.id = id;
        this.favooirte = favooirte;
        this.image = image;
        List_id = list_id;
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

    public int getList_id() {
        return List_id;
    }

    public void setList_id(int list_id) {
        List_id = list_id;
    }
}
