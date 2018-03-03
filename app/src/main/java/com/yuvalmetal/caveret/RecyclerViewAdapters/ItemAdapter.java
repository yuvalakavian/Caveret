package com.yuvalmetal.caveret.RecyclerViewAdapters;

import android.annotation.TargetApi;
import android.support.v4.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuvalmetal.caveret.Activites.ShoppingCartActivity;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.Fragments.SingleItemFragment;

import java.util.ArrayList;

/**
 * Created by yuvalmetal on 05/10/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Item> mDataset;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mItemName;
        public TextView mItemPrice;
        public ImageView mItemImage;

        public ViewHolder(View v) {
            super(v);

            mItemName = (TextView) v.findViewById(R.id.itemName);
            mItemPrice = (TextView)v.findViewById(R.id.itemPriceValue);
            mItemImage = (ImageView)v.findViewById(R.id.itemImageView);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemAdapter() {
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
             final Item item = mDataset.get(position);

             holder.itemView.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    SingleItemFragment singleItemFragment = new SingleItemFragment();
                    singleItemFragment.setItem(item);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        singleItemFragment.setEnterTransition(new android.transition.Fade());
                    }

                    ShoppingCartActivity activity = (ShoppingCartActivity) v.getContext();

                    FragmentTransaction transaction = activity.getSupportFragmentManager().
                            beginTransaction();


                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    transaction.replace(R.id.shopping_cart_fragment_container,singleItemFragment);
                  //  transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();
                }
            });

            holder.mItemName.setText(item.getName());
            holder.mItemPrice.setText(item.getPrice()+"");

            new DownloadImageTask(holder.mItemImage)
                    .execute(holder.mItemImage.getContext().getString(R.string.SERVER_URL) + item.getImgUrl());

            holder.mItemImage.setMaxHeight(50);
            holder.mItemImage.setMaxWidth(50);



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset == null){
            return 0;
        }

        return mDataset.size();
    }

    /**
     *
     * @param data
     */
    public void updateList(ArrayList<Item> data) {
        mDataset = data;
        notifyDataSetChanged();
    }
}

