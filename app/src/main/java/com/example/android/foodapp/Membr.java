package com.example.android.foodapp;

public class Membr  {

    String Title,Type,Image;

    Membr(){

    }

    public Membr(String title, String type, String image) {
        this.Title=title;
        this.Type=type;
        this.Image=image;
    }

    public String getTitle() {
        return Title;
    }

    public String getImage() {
        return Image;
    }

    public String getType() {
        return Type;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setType(String type) {
        Type = type;
    }

}
