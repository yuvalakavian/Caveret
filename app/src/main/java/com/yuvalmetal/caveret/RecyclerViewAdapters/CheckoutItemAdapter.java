package com.yuvalmetal.caveret.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuvalmetal.caveret.ApiObjects.ItemQAP;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.R;

/**
 * Created by yuvalmetal on 27/10/2017.
 */

public class CheckoutItemAdapter extends RecyclerViewAdapter<ItemQAP> {

    public CheckoutItemAdapter(Context context, OnViewHolderClick<ItemQAP> listener) {
        super(context, listener);
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checkout_item_qap, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        return v;
    }

    @Override
    protected void bindView(ItemQAP item, RecyclerViewAdapter.ViewHolder viewHolder) {
        if (item != null) {
            TextView mItemName = (TextView) viewHolder.getView(R.id.itemName);
            TextView mItemPrice = (TextView) viewHolder.getView(R.id.itemPriceValue);
            TextView mItemQuantity = (TextView) viewHolder.getView(R.id.itemSelectedQuantityValue);
            ImageView mItemImage = (ImageView) viewHolder.getView(R.id.itemImageView);

            mItemName.setText(item.getOrderItem().getName());
            mItemQuantity.setText(item.getQuantity()+"");
            mItemPrice.setText(item.getPrice()*item.getQuantity()+"");

            new DownloadImageTask(mItemImage).execute(ApiResources.getServerUrl()+item.getOrderItem().getImgUrl());

            mItemImage.setMaxWidth(50);
            mItemImage.setMaxHeight(50);
        }
    }
}

