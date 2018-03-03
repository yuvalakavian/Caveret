package com.yuvalmetal.caveret.ApiObjects;

/**
 * Created by yuvalmetal on 27/10/2017.
 */

public class Permission {
    public int Id;
    public String Name;

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

    @Override
    public String toString() {
        return Name;
    }
}
