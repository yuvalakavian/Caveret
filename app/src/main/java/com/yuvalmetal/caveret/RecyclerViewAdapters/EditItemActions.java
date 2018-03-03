package com.yuvalmetal.caveret.RecyclerViewAdapters;

/**
 * Created by yuvalmetal on 10/11/2017.
 */

public interface EditItemActions <T>{
    public void switchToUpdateItemFragment(T item);
    public void showDeleteItemDialog(T item);
}
