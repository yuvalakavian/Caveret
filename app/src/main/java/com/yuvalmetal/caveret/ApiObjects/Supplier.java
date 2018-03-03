package com.yuvalmetal.caveret.ApiObjects;

import java.util.List;

/**
 * Created by yuvalmetal on 02/11/2017.
 */

public class Supplier {
    public int Id;
    public String Name;
    public List<Item> SupplierItems;

    @Override
    public String toString() {
        return Name;
    }
}
