package com.yuvalmetal.caveret.Fragments;


import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.ApiObjects.ItemQAP;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.RecyclerViewAdapters.DownloadImageTask;

public class SingleItemFragment extends Fragment {
    private Item _item;
    private int _selectedQuantity = 0;

    private ImageView _singleItemImageView;
    private TextView _singleItemName;
    private TextView _singleItemPriceValue;
    private TextView _singleItemAvailableQuantityValue;
    private TextView _singleItemSelectedQuantity;

    private Button _singleItemAdd;
    private Button _singleItemRemove;
    private Button _singleItemAddToCart;

    private DataPasser dataPasser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     */
    public SingleItemFragment() {
    }

    /**
     *
     * @param item
     */
    public SingleItemFragment(Item item){
        setItem(item);
        _selectedQuantity = 0;
    }

    public static SingleItemFragment newInstance(Item item) {

        Bundle args = new Bundle();
        SingleItemFragment fragment = new SingleItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void updateViewSelectedQuantity(){
        _singleItemSelectedQuantity.setText(_selectedQuantity+"");
    }

    /**
     *
     * @param item
     */
    public void setItem(Item item){
        _item = item;
    }


    private void initView(final View rootView){
        _singleItemImageView = (ImageView)rootView.findViewById(R.id.single_item_imageview);

        _singleItemName = (TextView)rootView.findViewById(R.id.single_item_name);
        _singleItemPriceValue = (TextView)rootView.findViewById(R.id.single_item_price_value);
        _singleItemAvailableQuantityValue = (TextView)rootView.findViewById(R.id.single_item_available_quantity_value);
        _singleItemSelectedQuantity =(TextView)rootView.findViewById(R.id.single_item_selected_quantity);

        _singleItemAdd = (Button)rootView.findViewById(R.id.single_item_add);
        _singleItemRemove = (Button)rootView.findViewById(R.id.single_item_remove);
        _singleItemAddToCart =(Button)rootView.findViewById(R.id.single_item_add_to_cart);

        new DownloadImageTask(_singleItemImageView)
                .execute(_singleItemImageView.getContext().getString(R.string.SERVER_URL) + _item.getImgUrl());

        _singleItemName.setText(_item.getName());
        _singleItemPriceValue.setText(_item.getPrice()+"");
        _singleItemAvailableQuantityValue.setText(Integer.toString(_item.getQuantity()));

    }

    private void syncViewWithActivity(){
        if(dataPasser.isItemAdded(_item)) {
            ItemQAP temp = dataPasser.getSelectedItem(_item);
            if (temp != null) {
                _singleItemAddToCart.setBackgroundColor(Color.GREEN);
                _singleItemAddToCart.setText("המוצר בסל");
                _selectedQuantity = temp.getQuantity();
                updateViewSelectedQuantity();
            }
        }
    }

    private void initButtonsOnClickListeners(final View rootView){
        _singleItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_selectedQuantity < _item.getQuantity()) {
                    _selectedQuantity++;
                    updateViewSelectedQuantity();
                }
            }
        });

        _singleItemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_selectedQuantity > 0) {
                    _selectedQuantity--;
                    updateViewSelectedQuantity();
                }
            }

        });

        _singleItemAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"הוסף לסל יא מניאק",Toast.LENGTH_SHORT).show();
                if(!dataPasser.isItemAdded(_item)) {
                    buildItemQAP();
                    _singleItemAddToCart.setBackgroundColor(Color.GREEN);
                    _singleItemAddToCart.setText("המוצר בסל");
                    Snackbar.make(rootView,"הוסף לסל",Snackbar.LENGTH_SHORT).show();
                }
                else {
                    //means it successfuly removed
                    if (dataPasser.removeItemQAP(_item)) {
                        Snackbar.make(rootView, "הוסר מהסל", Snackbar.LENGTH_SHORT).show();
                        _singleItemAddToCart.setText("הוסף לסל");
                        _singleItemAddToCart.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                }

                dataPasser.updateItemCountNotification();
            }

            private void buildItemQAP() {
                dataPasser.addNewItemQAP(new ItemQAP(_item,(int)_item.getPrice(),_selectedQuantity));
            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_single_item,container,false);

        initView(rootView);
        initButtonsOnClickListeners(rootView);
        syncViewWithActivity();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataPasser)context;
    }

    public interface DataPasser{
        public void addNewItemQAP(ItemQAP itemQAP);
        public boolean removeItemQAP(Item item);
        public boolean isItemAdded(Item item);
        public ItemQAP getSelectedItem(Item item);
        public boolean resetCart();
        public boolean updateItemCountNotification();

    }
}
