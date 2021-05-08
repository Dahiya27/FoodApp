package com.example.android.foodapp.ui.home;

public class cart {
    private String dishname,price,quantity;

    public cart(){

    }
    public cart(String dishname,String price,String quantity){
        this.dishname=dishname;
        this.price=price;
        this.quantity=quantity;
    }
    public String getDishname(){
        return dishname;
    }
    public String getPrice(){
        return price;
    }
    public String getQuantity(){
        return quantity;
    }
    public void setQuantity(String quantity){
        this.quantity=quantity;
    }
}
