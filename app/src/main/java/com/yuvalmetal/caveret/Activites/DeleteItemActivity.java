package com.yuvalmetal.caveret.Activites;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.ApiObjects.ItemQAP;
import com.yuvalmetal.caveret.ApiObjects.ItemsList;
import com.yuvalmetal.caveret.Fragments.CheckoutFragment;
import com.yuvalmetal.caveret.Fragments.ItemList;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.RecyclerViewAdapters.AdapterDataPasser;
import com.yuvalmetal.caveret.RecyclerViewAdapters.DeleteItemAdapter;
import com.yuvalmetal.caveret.RecyclerViewAdapters.RecyclerViewAdapter;
import com.yuvalmetal.caveret.RecyclerViewAdapters.SearchResultsItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/*
public class DeleteItemActivity extends AppCompatActivity implements AdapterDataPasser<Item> {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private ItemList mItemListFragment;
    private DeleteItemAdapter mAdapter;

    private TextView mSelectedQuantity;
    private Button mDeleteItemButton;


    public ArrayList<Item> mSelectedItems;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("מחק מוצרים");
        setContentView(R.layout.activity_delete_item);

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        mSelectedQuantity = (TextView) findViewById(R.id.delete_item_selected_quantity);

        mDeleteItemButton = (Button) findViewById(R.id.delete_item_button);

        mDeleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeDeleteItems();
            }
        });

        mAdapter = new DeleteItemAdapter(this, null);

        mItemListFragment = new ItemList(mAdapter);

        mFragmentManager = getFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.delete_item_fragment_container,mItemListFragment,"itemList");

        mFragmentTransaction.commit();


        mSelectedItems = new ArrayList<Item>();
    }

    private void updateSelectedQuantityView() {
            mSelectedQuantity.setText(String.valueOf(mSelectedItems.size()));
    }

    private void executeDeleteItems() {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.POST, ApiResources.getInstance(this).getApiUrlWithPath("items/delete/"), buildDeleteItemRequestJson(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mAdapter.reset();
                        Snackbar.make(getCurrentFocus(),"מחיקת המוצרים התבצעה בהצלחה",Snackbar.LENGTH_SHORT).show();
                        Log.d("Response", response.toString());
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","wut de fuk");
                        error.printStackTrace();

                    }
                });


        // Add the request to the RequestQueue.
        ApiResources.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private JSONObject buildDeleteItemRequestJson() {
        JSONObject jsonObj = null;
        Gson gson = new Gson();
        ItemsList itemsList = new ItemsList();
        itemsList.List = mSelectedItems;
        try {
            jsonObj = new JSONObject(gson.toJson(itemsList));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("ASS",jsonObj.toString());
        return jsonObj;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


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
    public void addItem(Item item) {
        if(!isItemAdded(item)) {
            mSelectedItems.add(item);
        }
    }

    @Override
    public boolean removeItem(Item item) {
        for(Item i : mSelectedItems){
            if(i.getId() == item.getId()){
                mSelectedItems.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateSelectedQuantity() {
        updateSelectedQuantityView();
    }

    @Override
    public boolean isItemAdded(Item item) {
        for(Item i : mSelectedItems){
            if(i.getId() == item.getId()){
                return true;
            }
        }
        return false;
    }
}
*/