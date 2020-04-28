package com.jamaatna.jamaatnaapp.Model;

public class Setting {
    private  String phone;
    private  String email;
    private  String facebook;
    private  String tweeter;
    private  String instagram;
    private  String watsapp;
    private  String about_app;
    private  String privacy_app;

    public Setting(String phone, String email, String facebook, String tweeter, String instagram, String watsapp, String about_app, String privacy_app) {
        this.phone = phone;
        this.email = email;
        this.facebook = facebook;
        this.tweeter = tweeter;
        this.instagram = instagram;
        this.watsapp = watsapp;
        this.about_app = about_app;
        this.privacy_app = privacy_app;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTweeter() {
        return tweeter;
    }

    public void setTweeter(String tweeter) {
        this.tweeter = tweeter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getWatsapp() {
        return watsapp;
    }

    public void setWatsapp(String watsapp) {
        this.watsapp = watsapp;
    }

    public String getAbout_app() {
        return about_app;
    }

    public void setAbout_app(String about_app) {
        this.about_app = about_app;
    }

    public String getPrivacy_app() {
        return privacy_app;
    }

    public void setPrivacy_app(String privacy_app) {
        this.privacy_app = privacy_app;
    }
}
