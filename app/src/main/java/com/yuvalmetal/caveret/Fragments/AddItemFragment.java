package com.yuvalmetal.caveret.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yuvalmetal.caveret.ApiObjects.Category;
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.ApiObjects.Supplier;
import com.yuvalmetal.caveret.ApiObjects.SupplierItem;
import com.yuvalmetal.caveret.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddItemFragment extends Fragment {
    private EditText mName;
    private EditText mPrice;

    private Spinner mCategorySpinner;
    private Spinner mSupplierSpinner;

    private Button mAddItemButton;

    private ArrayList<Category> mCategoryList;
    private ArrayList<Supplier> mSuppliersList;

    private ArrayAdapter mCategoryAdapter;
    private ArrayAdapter mSupplierAdapter;

    private Category mSelectedCategory;
    private Supplier mSelectedSupplier;


    public AddItemFragment() {
        // Required empty public constructor
    }


    public static AddItemFragment newInstance() {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLists();
        initSpinnersAdapters();

        receiveCategories();
        receiveSuppliers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);
        initView(rootView);
        attachSpinnerAdapters();
        setViewsListeners();
        return rootView;
    }

    private void receiveSuppliers() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ApiResources.getInstance(getContext()).getApiUrlWithPath("suppliers/"), null, new Response.Listener<JSONArray>() {

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
        ApiResources.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }

    private void receiveCategories() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ApiResources.getInstance(getContext()).getApiUrlWithPath("categories/"), null, new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {
                        setCategoriesList(response);
                        updateCategorySpinner();
                        Log.d("Response", response.toString());

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response", error.getLocalizedMessage().toString());

                    }
                });


        // Add the request to the RequestQueue.
        ApiResources.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }

    private void setViewsListeners() {
        setSpinnersListeners();
        setButtonListeners();
    }

    private void setButtonListeners() {
        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void addItem() {
        if (!validate()) {
            Snackbar.make(getView(), "אחת מן השדות אינם תקינים אנא נסה שנית יא אח", Snackbar.LENGTH_SHORT);
            return;
        }
        ApiResources.getInstance(getContext()).addToRequestQueue(getAddItemReq());
    }

    private boolean validate() {
        boolean valid = true;

        if (mPrice.getText().toString().isEmpty() || mPrice.getText().toString().equals("0")) {
            mPrice.setError("קבע מחיר תקין");
            valid = false;
        } else {
            mPrice.setError(null);
        }

        return valid;
    }

    private void setSpinnersListeners() {
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCategory = (Category) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSupplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedSupplier = (Supplier) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void attachSpinnerAdapters() {
        mSupplierSpinner.setAdapter(mSupplierAdapter);
        mCategorySpinner.setAdapter(mCategoryAdapter);
    }

    private void initLists() {
        mCategoryList = new ArrayList<Category>();
        mSuppliersList  = new ArrayList<Supplier>();
    }

    private void initSpinnersAdapters() {
        mCategoryAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mCategoryList);
        mSupplierAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mSuppliersList);
    }

    private void initView(View rootView) {
        mName = (EditText)rootView.findViewById(R.id.add_item_name);
        mPrice = (EditText)rootView.findViewById(R.id.add_item_price);

        mCategorySpinner = (Spinner)rootView.findViewById(R.id.add_item_category_spinner);
        mSupplierSpinner = (Spinner)rootView.findViewById(R.id.add_item_supplier_spinner);

        mAddItemButton = (Button)rootView.findViewById(R.id.add_item_button);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void updateCategorySpinner() {
        Category defaultCategory = new Category();
        defaultCategory.Name = "בחר קטגוריה";
        mCategoryAdapter.clear();
        mCategoryAdapter.add(defaultCategory);
        mCategoryAdapter.addAll(mCategoryList);
        mSupplierAdapter.notifyDataSetChanged();
    }

    public void setCategoriesList(JSONArray response) {
        mCategoryList = new ArrayList<Category>();
        for (int i = 0; i < response.length(); i++) {
            JsonParser parser = new JsonParser();
            JsonElement mJson = null;
            try {
                mJson = parser.parse(response.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();

            Category item = gson.fromJson(mJson, Category.class);

            mCategoryList.add(item);
        }
    }
    //////////////////////////////////////////////////////////
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

    public Request getAddItemReq() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, ApiResources.getInstance(getContext()).getApiUrlWithPath("items/additemsupplier"), buildNewItemJson(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Toast.makeText(getContext(),"המוצר התווסף בהצלחה למאגר",Toast.LENGTH_SHORT).show();
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

    private JSONObject buildNewItemJson() {
        Supplier supplier = mSelectedSupplier;
        Item item = new Item();
        item.setName(mName.getText().toString());
        item.setPrice(Double.parseDouble(mPrice.getText().toString()));
        item.ItemCategory = mSelectedCategory;

        SupplierItem addSupplierToItem = new SupplierItem();

        addSupplierToItem.Item = item;
        addSupplierToItem.Supplier = supplier;

        JSONObject obj = null;
        Gson gson = new Gson();


        try {
            obj = new JSONObject(gson.toJson(addSupplierToItem));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON",obj.toString());

        return obj;
    }

}
