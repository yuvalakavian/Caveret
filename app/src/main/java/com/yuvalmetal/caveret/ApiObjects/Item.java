package com.yuvalmetal.caveret.ApiObjects;

import java.io.Serializable;

/**
 * Created by yuvalmetal on 07/10/2017.
 */

public class Item implements Serializable {
    public int Id;
    public String Name;
    public double Price;
    public String ImgUrl;
    public Category ItemCategory;
    public int Quantity;

    public Item(){
        super();
    }

    public Item(int id, String name, double price, String imgUrl, int quantity,Category itemCategory) {
        Id = id;
        Name = name;
        Price = price;
        ImgUrl = imgUrl;
        Quantity = quantity;
        ItemCategory = itemCategory;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return Name;
    }
}
