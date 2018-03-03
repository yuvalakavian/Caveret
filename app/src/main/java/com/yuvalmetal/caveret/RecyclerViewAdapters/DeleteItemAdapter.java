package com.yuvalmetal.caveret.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;

/**
 * Created by yuvalmetal on 29/10/2017.
 */

public class DeleteItemAdapter extends RecyclerViewAdapter<Item> {

    private AdapterDataPasser<Item> deleteItemDataPasser;

    public DeleteItemAdapter(Context context, OnViewHolderClick<Item> listener) {
        super(context, listener);
        deleteItemDataPasser = (AdapterDataPasser<Item>) context;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.delete_item_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        return v;
    }

    @Override
    protected void bindView(final Item item, RecyclerViewAdapter.ViewHolder viewHolder) {
        if (item != null) {
            TextView mItemName = (TextView) viewHolder.getView(R.id.itemName);
            ImageView mItemImage = (ImageView) viewHolder.getView(R.id.itemImageView);
            CheckBox mCheckbox = (CheckBox) viewHolder.getView(R.id.delete_item_checkbox);

            mCheckbox.setChecked(deleteItemDataPasser.isItemAdded(item));

            mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((CheckBox) v).isChecked();
                    if (isChecked) {
                        deleteItemDataPasser.addItem(item);
                    } else {
                        deleteItemDataPasser.removeItem(item);
                    }
                    deleteItemDataPasser.updateSelectedQuantity();
                }
            });

            mItemName.setText(item.getName());

            new DownloadImageTask(mItemImage).execute(ApiResources.getServerUrl()+item.getImgUrl());

            mItemImage.setMaxWidth(50);
            mItemImage.setMaxHeight(50);
        }
    }

    /*
    public interface DeleteItemDataPasser{
        public void addItem(Item item);
        public void removeItem(Item item);
        public void updateDeleteItemSelectedQuantity();
        public boolean isItemAdded(Item item);
    }
    */
}
