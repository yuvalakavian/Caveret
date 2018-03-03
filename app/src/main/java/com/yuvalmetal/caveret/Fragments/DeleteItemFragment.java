package com.yuvalmetal.caveret.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.ApiObjects.ItemsList;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.RecyclerViewAdapters.AdapterDataPasser;
import com.yuvalmetal.caveret.RecyclerViewAdapters.DeleteItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DeleteItemFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private ItemList mItemListFragment;
    private DeleteItemAdapter mAdapter;

    private TextView mSelectedQuantity;
    private Button mDeleteItemButton;


    public ArrayList<Item> mSelectedItems;


    public DeleteItemFragment() {
        // Required empty public constructor
    }


    public static DeleteItemFragment newInstance() {
        DeleteItemFragment fragment = new DeleteItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new DeleteItemAdapter(getContext(), null);

        mItemListFragment = new ItemList(mAdapter);

        mFragmentManager = getChildFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.add(R.id.delete_item_fragment_container,mItemListFragment,"itemList");

        mFragmentTransaction.commit();


        mSelectedItems = new ArrayList<Item>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_delete_item, container, false);

        mSelectedQuantity = (TextView) rootView.findViewById(R.id.delete_item_selected_quantity);

        mDeleteItemButton = (Button) rootView.findViewById(R.id.delete_item_button);

        /*
        mDeleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeDeleteItems();
            }
        });
        */

        return rootView;
    }

    private void updateSelectedQuantityView() {
        mSelectedQuantity.setText(String.valueOf(mSelectedItems.size()));
    }

    private void executeDeleteItems() {
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.POST, ApiResources.getInstance(getContext()).getApiUrlWithPath("items/delete/"), buildDeleteItemRequestJson(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mAdapter.reset();
                        Snackbar.make(getView(),"מחיקת המוצרים התבצעה בהצלחה",Snackbar.LENGTH_SHORT).show();
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
        ApiResources.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
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



}
