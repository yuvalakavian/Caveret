package com.yuvalmetal.caveret.ApiObjects;

/**
 * Created by yuvalmetal on 14/10/2017.
 */

public class ItemQAP {
   // private int Id;
    private Item OrderItem;
    private int Price;
    private int Quantity;

    public ItemQAP(Item orderItem, int price, int quantity) {
        setOrderItem(orderItem);
        setPrice(price);
        setQuantity(quantity);
    }


    /*
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    */
    public Item getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(Item orderItem) {
        OrderItem = orderItem;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
