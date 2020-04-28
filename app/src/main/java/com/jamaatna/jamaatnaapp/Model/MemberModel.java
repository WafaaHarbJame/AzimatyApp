package com.jamaatna.jamaatnaapp.Model;

public class MemberModel {
//    @SerializedName("token")
//    @Expose
    public  String ApiToken;
    public int id;
    public String name;
    public  String phone;
    public  String photo;
    public  boolean status;

    public MemberModel(String apiToken, int id, String name, String phone, String photo, boolean status) {
        ApiToken = apiToken;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
        this.status = status;
    }
}
