package com.yuvalmetal.caveret.Fragments;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.RecyclerViewAdapters.RecyclerViewAdapter;
import com.yuvalmetal.caveret.RecyclerViewAdapters.SearchResultsItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class ItemList extends Fragment{
    private String mApiPath;
    ArrayList<Item> _items;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ItemList(RecyclerViewAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _items = new ArrayList<Item>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.item_list_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.shopping_cart_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }

    public void set_items(JSONArray response ){

        _items = new ArrayList<Item>();
        for(int i = 0; i < response.length();i++){
            JsonParser parser = new JsonParser();
            JsonElement mJson = null;
            try {
                mJson = parser.parse(response.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();

            Item item = gson.fromJson(mJson, Item.class);

            _items.add(item);
        }
    }

    public void executeSearchQuery(String query){

        Uri.Builder urlBuilder = new Uri.Builder();

        urlBuilder.scheme("http")
                .encodedAuthority(ApiResources.getInstance(getActivity()).getServerIpWithPort())
                .appendPath("api")
                .appendPath("items")
                .appendPath("byvalue")
                .appendQueryParameter("value",query);

        String finalUrl = urlBuilder.toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, finalUrl, null, new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {
                        set_items(response);
                        mAdapter.reset();
                        mAdapter.addAll(_items);
                        Log.d("Response", response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "wut de fuk");

                    }
                });


        // Add the request to the RequestQueue.
        ApiResources.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);

    }

}












    /*

    public void buildItemList(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ApiResources.getInstance(getActivity()).getApiUrlWithPath("items/"), null, new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {
                        setmItems(response);
                        mAdapter.reset();
                        mAdapter.addAll(mItems);
                        Log.d("Response", response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response", error.getLocalizedMessage().toString());

                    }
                });


        // Add the request to the RequestQueue.
        ApiResources.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);

    }
*/
