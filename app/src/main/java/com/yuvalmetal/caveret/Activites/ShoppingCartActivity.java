package com.yuvalmetal.caveret.Activites;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.ApiObjects.ItemQAP;
import com.yuvalmetal.caveret.Fragments.CheckoutFragment;
import com.yuvalmetal.caveret.Fragments.ItemList;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.Fragments.SingleItemFragment;
import com.yuvalmetal.caveret.RecyclerViewAdapters.RecyclerViewAdapter;
import com.yuvalmetal.caveret.RecyclerViewAdapters.SearchResultsItemAdapter;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity implements SingleItemFragment.DataPasser {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private ItemList mItemListFragment;

    private SearchResultsItemAdapter mAdapter;


    private TextView mShopCartIcon;
    public ArrayList<ItemQAP> mSelectedItems;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        setTitle("סל קניות");
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        mAdapter = new SearchResultsItemAdapter(this, new RecyclerViewAdapter.OnViewHolderClick<Item>() {
            @Override
            public void onClick(View view, int position, Item item) {
                SingleItemFragment singleItemFragment = new SingleItemFragment();
                singleItemFragment.setItem(item);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    singleItemFragment.setEnterTransition(new android.transition.Fade());
                }

                mFragmentTransaction = mFragmentManager.beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack

                mFragmentTransaction.replace(R.id.shopping_cart_fragment_container,singleItemFragment);
                mFragmentTransaction.addToBackStack(null);

                // Commit the transaction
                mFragmentTransaction.commit();
            }
        });

        mItemListFragment = new ItemList(mAdapter);

        mFragmentManager = getSupportFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.shopping_cart_fragment_container,mItemListFragment,"itemList");

        mFragmentTransaction.commit();


        mSelectedItems = new ArrayList<ItemQAP>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopping_cart_search_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                mItemListFragment = new ItemList(mAdapter);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    mItemListFragment.setEnterTransition(new android.transition.Fade());
                                }
                    /*
                    for(int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
                        mFragmentManager.popBackStack();
                    }
                    */

                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.shopping_cart_fragment_container,mItemListFragment);
                                mFragmentTransaction.addToBackStack(null);
                                mFragmentTransaction.commit();
                            }
                        }
                    });


        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                    public boolean onQueryTextChange(String newText) {
                        if(!newText.equals("")) {
                          mItemListFragment.executeSearchQuery(newText);
                        }
                        return false;
                    }
                }
        );



        //sets the notification cart icon!!!!!!!

        MenuItem item  =  menu.findItem(R.id.shopping_cart_icon);
        mShopCartIcon = (TextView) ((RelativeLayout)(item.getActionView())).findViewById(R.id.cart_count);
        mShopCartIcon.setVisibility(View.INVISIBLE);

        mShopCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckoutFragment mCheckoutFragment = new CheckoutFragment(mSelectedItems);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mCheckoutFragment.setEnterTransition(new android.transition.Fade());
                }
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.shopping_cart_fragment_container,mCheckoutFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
            }
        });
        return true;
    }




    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void addNewItemQAP(ItemQAP itemQAP) {
        mSelectedItems.add(itemQAP);
    }

    @Override
    public boolean removeItemQAP(Item item) {
        for(ItemQAP itemQAP : mSelectedItems){
            if(itemQAP.getOrderItem().getId() == item.getId()) {
                mSelectedItems.remove(itemQAP);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isItemAdded(Item item) {

        for(ItemQAP itemQAP : mSelectedItems){
            if(itemQAP.getOrderItem().getId() == item.getId())
                return true;
        }
        return false;
    }

    @Override
    public ItemQAP getSelectedItem(Item item) {
        for (ItemQAP itemQAP : mSelectedItems) {
            if (itemQAP.getOrderItem().getId() == item.getId()) {
                return itemQAP;
            }
        }
        return null;
    }

    @Override
    public boolean resetCart() {
        mSelectedItems.removeAll(mSelectedItems);
        updateItemCountNotification();
        return false;
    }

    @Override
    public boolean updateItemCountNotification(){
        if(mSelectedItems.size() > 0){
            mShopCartIcon.setText(Integer.toString(mSelectedItems.size()));
            mShopCartIcon.setVisibility(View.VISIBLE);
            return true;
        }
        mShopCartIcon.setVisibility(View.INVISIBLE);
        return false;


    }
}
