package com.jamaatna.jamaatnaapp.Model;

public class Catogories {
    private  int catogory_id;
    private  String catogory_nam;

    public Catogories(int catogory_id, String catogory_nam) {
        this.catogory_id = catogory_id;
        this.catogory_nam = catogory_nam;
    }

    public Catogories() {
    }

    public int getCatogory_id() {
        return catogory_id;
    }

    public void setCatogory_id(int catogory_id) {
        this.catogory_id = catogory_id;
    }

    public String getCatogory_nam() {
        return catogory_nam;
    }

    public void setCatogory_nam(String catogory_nam) {
        this.catogory_nam = catogory_nam;
    }
}
