package com.jamaatna.jamaatnaapp.Model;

public class SubItem {
    private  int id;
    private String subItemTitle;

    public SubItem(String subItemTitle,int id) {
        this.subItemTitle = subItemTitle;
        this.id = id;

    }
    public SubItem(String subItemTitle) {
        this.subItemTitle = subItemTitle;
        this.id = id;

    }

    public SubItem() {
    }


    public String getSubItemTitle() {
        return subItemTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubItemTitle(String subItemTitle) {
        this.subItemTitle = subItemTitle;
    }
}
