package com.yuvalmetal.caveret.ApiObjects;

import java.util.Date;
import java.util.List;

/**
 * Created by yuvalmetal on 29/10/2017.
 */

public class Order {
 //  public int Id;
    public List<ItemQAP> OrderItemsQAP;
    public Date Date;
    public Employee OrderEmployee;
    public double totalPrice;

    public Order(List<ItemQAP> orderItemsQAP, java.util.Date date, Employee orderEmployee, double totalPrice) {
        OrderItemsQAP = orderItemsQAP;
        Date = date;
        OrderEmployee = orderEmployee;
        this.totalPrice = totalPrice;
    }
}
