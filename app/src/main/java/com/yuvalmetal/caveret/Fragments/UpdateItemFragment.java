package com.yuvalmetal.caveret.Fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.R;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateItemFragment extends Fragment {
    private static final String ARG_ITEM = "item";

    private EditText mName;
    private EditText mPrice;

    private Button mUpdateItemButton;

    private Item mItem;

    public UpdateItemFragment() {
        // Required empty public constructor
    }


    public static UpdateItemFragment newInstance(Item item) {
        UpdateItemFragment fragment = new UpdateItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM,item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = (Item) getArguments().getSerializable(ARG_ITEM);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_update_item, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        mName = (EditText)rootView.findViewById(R.id.update_item_name);
        mPrice = (EditText)rootView.findViewById(R.id.update_item_price);

        mUpdateItemButton = (Button)rootView.findViewById(R.id.update_item_button);

        mName.setText(mItem.getName());

        mPrice.setText(String.valueOf(mItem.getPrice()));

        mUpdateItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preformItemUpdate();
            }
        });
    }


    private void preformItemUpdate() {
        if (!validate()) {
            Snackbar.make(getView(), "אחת מן השדות אינם תקינים אנא נסה שנית יא אח", Snackbar.LENGTH_SHORT);
            return;
        }
        ApiResources.getInstance(getActivity()).addToRequestQueue(getUpdateItemReq());
    }

    private boolean validate() {
        boolean valid = true;

        if(mName.getText().toString().isEmpty()){
            mName.setError("שם מוצר לא תקין");
            valid = false;
        }
        else{
            mName.setError(null);
        }

        if (mPrice.getText().toString().isEmpty() || mPrice.getText().toString().equals("0")) {
            mPrice.setError("מחיר לא תקין");
            valid = false;
        } else {
            mPrice.setError(null);
        }

        return valid;
    }

    public Request getUpdateItemReq() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, ApiResources.getInstance(getActivity()).getApiUrlWithPath("items/"+ mItem.getId()+"/"), buildItemJson(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Toast.makeText(getContext(),"פרטי מוצר התעדכנו בהצלחה",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                        Log.d("ERROR", error.getLocalizedMessage().toString());
                    }
                });
        return jsObjRequest;
    }

    private JSONObject buildItemJson() {
        Item item = mItem;

        item.setName(mName.getText().toString());
        item.setPrice(Double.parseDouble(mPrice.getText().toString()));

        JSONObject obj = null;
        Gson gson = new Gson();

        try {
            obj = new JSONObject(gson.toJson(item));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON",obj.toString());

        return obj;
    }

}
