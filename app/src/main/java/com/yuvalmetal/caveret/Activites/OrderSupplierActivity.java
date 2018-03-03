package com.yuvalmetal.caveret.Activites;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.ApiObjects.Supplier;
import com.yuvalmetal.caveret.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderSupplierActivity extends AppCompatActivity {
    private Spinner mSupplierSpinner;
    private Spinner mItemSpinner;
    private EditText mOrderQuantity;
    private Button mUpdateQuantityButton;

    private ArrayList<Supplier> mSuppliersList;
    private ArrayList<Item> mItemList;

    private ArrayAdapter mSupplierAdapter;
    private ArrayAdapter mItemAdapter;

    private Item mSelectedItem;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("הזמן מספק");
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.fragment_order_supplier);

        mSupplierSpinner = (Spinner) findViewById(R.id.order_supplier_spinner);
        mItemSpinner = (Spinner) findViewById(R.id.order_supplier_item);

        mOrderQuantity = (EditText) findViewById(R.id.order_supplier_quantity_edit_text);

        mUpdateQuantityButton = (Button) findViewById(R.id.order_supplier_button);

        mItemSpinner.setEnabled(false);

        mSuppliersList = new ArrayList<Supplier>();
        mItemList = new ArrayList<Item>();

        mSelectedItem = null;

        receiveSuppliers();

        mSupplierAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mSuppliersList);
        mItemAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mItemList);

        mSupplierSpinner.setAdapter(mSupplierAdapter);
        mItemSpinner.setAdapter(mItemAdapter);

        mSupplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItemList = new ArrayList<Item>();
                if(position != 0) {
                    Supplier selectedSupplier = (Supplier) parent.getItemAtPosition(position);
                    mItemList.addAll(selectedSupplier.SupplierItems);
                    mItemSpinner.setEnabled(true);
                }
                else{
                    mItemSpinner.setEnabled(false);
                }
                updateItemSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position != 0) {
                   mSelectedItem = (Item) parent.getItemAtPosition(position);
               }
               else{
                   mSelectedItem = null;
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
        });


        mUpdateQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuantityToItem();
            }
        });


    }

    private void receiveSuppliers() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ApiResources.getInstance(this).getApiUrlWithPath("suppliers/"), null, new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {
                        setSuppliersList(response);
                        updateSuppliersSpinner();
                        Log.d("Response", response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response", error.getLocalizedMessage().toString());

                    }
                });


        // Add the request to the RequestQueue.
        ApiResources.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void updateSuppliersSpinner() {
        Supplier defaultSupplier = new Supplier();
        defaultSupplier.Name = "בחר ספק";
        mSupplierAdapter.clear();
        mSupplierAdapter.add(defaultSupplier);
        mSupplierAdapter.addAll(mSuppliersList);
        mSupplierAdapter.notifyDataSetChanged();
    }

    public void setSuppliersList(JSONArray response) {
        mSuppliersList = new ArrayList<Supplier>();
        for (int i = 0; i < response.length(); i++) {
            JsonParser parser = new JsonParser();
            JsonElement mJson = null;
            try {
                mJson = parser.parse(response.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();

            Supplier item = gson.fromJson(mJson, Supplier.class);

            mSuppliersList.add(item);
        }
    }

    private void updateItemSpinner() {
        Item item = new Item();
        mItemAdapter.clear();
        item.setName("בחר מוצר");
        mItemAdapter.add(item);
        mItemAdapter.addAll(mItemList);
        mItemAdapter.notifyDataSetChanged();
    }

    private void addQuantityToItem() {
        if (!validate()) {
            Snackbar.make(getCurrentFocus(), "אחת מן השדות אינם תקינים אנא נסה שנית יא אח", Snackbar.LENGTH_SHORT);
            return;
        }
        ApiResources.getInstance(this).addToRequestQueue(getAddQuantityReq());
    }

    private boolean validate() {
        boolean valid = true;

        if (mOrderQuantity.getText().toString().isEmpty() || mOrderQuantity.getText().toString().equals("0")) {
            mOrderQuantity.setError("בחר כמות");
            valid = false;
        } else {
            mOrderQuantity.setError(null);
        }

        return valid;
    }

    public Request getAddQuantityReq() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, ApiResources.getInstance(this).getApiUrlWithPath("items/"+ mSelectedItem.getId()+"/"), buildItemJson(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Toast.makeText(getBaseContext(),"כמות נוספה בהצלחה למוצר",Toast.LENGTH_SHORT).show();
                        finish();
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
        Item item = mSelectedItem;
        item.setQuantity(mSelectedItem.getQuantity() + Integer.parseInt(mOrderQuantity.getText().toString()));

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


