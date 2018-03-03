package com.yuvalmetal.caveret.RecyclerViewAdapters;

/**
 * Created by yuvalmetal on 31/10/2017.
 */

public interface AdapterDataPasser<T> {
    public void addItem(T item);
    public boolean removeItem(T item);
    public void updateSelectedQuantity();
    public boolean isItemAdded(T item);
}
