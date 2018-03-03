package com.yuvalmetal.caveret.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.ItemQAP;
import com.yuvalmetal.caveret.ApiObjects.Order;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.RecyclerViewAdapters.CheckoutItemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class CheckoutFragment extends Fragment {
    ArrayList<ItemQAP> mItems;

    private RecyclerView mRecyclerView;
    private CheckoutItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mButton;
    private TextView mTotalPrice;

    private SingleItemFragment.DataPasser dataPasser;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (SingleItemFragment.DataPasser)context;
    }

    public CheckoutFragment(ArrayList<ItemQAP> items){
        mItems = new ArrayList<ItemQAP>();
        // specify an adapter (see also next example)
        mAdapter = new CheckoutItemAdapter(getActivity(),null);
        setmItems(items);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.checkout_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.setAdapter(mAdapter);

        
        mButton = (Button) rootView.findViewById(R.id.checkout_button);
        
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOrder();
            }

            private void executeOrder() {

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, ApiResources.getInstance(getActivity()).getApiUrlWithPath("orders/"), buildOrder(), new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getActivity(),"ההזמנה בוצעה בהצלחה",Toast.LENGTH_SHORT).show();
                                dataPasser.resetCart();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                error.printStackTrace();
                            }
                        });
                ApiResources.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
            }

            private JSONObject buildOrder() {
                Gson gson = new Gson();
                Order order = new Order(mItems,new Date(),ApiResources.getInstance(getActivity()).getmEmployee(),getTotalPrice());
                JSONObject orderJSON = null;

                try {
                    orderJSON = new JSONObject(gson.toJson(order));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("ORDER",orderJSON.toString());
                return orderJSON;
            }
        });

        mTotalPrice = (TextView)rootView.findViewById(R.id.checkout_total_price);
        mTotalPrice.setText(String.valueOf(getTotalPrice()));

        return rootView;
    }

    public void setmItems(ArrayList<ItemQAP> items){
        mItems = items;
        mAdapter.reset();
        mAdapter.addAll(mItems);
    }


    public double getTotalPrice() {
        double mTotalPrice = 0;

        for (ItemQAP i : mItems ){
            mTotalPrice += i.getPrice() * i.getQuantity();
        }

        return mTotalPrice;
    }
}
