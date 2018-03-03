package com.yuvalmetal.caveret.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;

/**
 * Created by yuvalmetal on 09/11/2017.
 */

public class EditItemAdapter extends RecyclerViewAdapter<Item> {
    private EditItemActions<Item> mEditItemActions;

    public EditItemAdapter(Context context,EditItemActions<Item> actionsHolder, OnViewHolderClick<Item> listener) {
        super(context, listener);
        mEditItemActions = actionsHolder;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edit_item_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        return v;
    }

    @Override
    protected void bindView(final Item item, RecyclerViewAdapter.ViewHolder viewHolder) {
        if (item != null) {
            ImageView mItemImage = (ImageView) viewHolder.getView(R.id.edit_item_image);
            TextView mItemName = (TextView) viewHolder.getView(R.id.edit_item_name);
            ImageButton mEditButton = (ImageButton) viewHolder.getView(R.id.edit_item_edit_button);
            ImageButton mDeleteButton = (ImageButton) viewHolder.getView(R.id.edit_item_delete_button);

            mItemName.setText(item.getName());
            new DownloadImageTask(mItemImage).execute(ApiResources.getServerUrl()+item.getImgUrl());

            mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditItemActions.switchToUpdateItemFragment(item);
                }
            });

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditItemActions.showDeleteItemDialog(item);
                }
            });


        }
    }
}
