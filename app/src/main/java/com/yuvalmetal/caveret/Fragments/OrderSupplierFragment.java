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
import com.yuvalmetal.caveret.ApiObjects.Item;
import com.yuvalmetal.caveret.ApiObjects.Supplier;
import com.yuvalmetal.caveret.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderSupplierFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderSupplierFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderSupplierFragment extends Fragment {
    private Spinner mSupplierSpinner;
    private Spinner mItemSpinner;
    private EditText mOrderQuantity;
    private Button mUpdateQuantityButton;

    private ArrayList<Supplier> mSuppliersList;
    private ArrayList<Item> mItemList;

    private ArrayAdapter mSupplierAdapter;
    private ArrayAdapter mItemAdapter;

    private Item mSelectedItem;





   // private OnFragmentInteractionListener mListener;

    public OrderSupplierFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OrderSupplierFragment newInstance() {
        OrderSupplierFragment fragment = new OrderSupplierFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSuppliersList = new ArrayList<Supplier>();
        mItemList = new ArrayList<Item>();

        mSelectedItem = null;

        receiveSuppliers();

        mSupplierAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mSuppliersList);
        mItemAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mItemList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order_supplier, container, false);

        mSupplierSpinner = (Spinner) rootView.findViewById(R.id.order_supplier_spinner);
        mItemSpinner = (Spinner) rootView.findViewById(R.id.order_supplier_item);

        mOrderQuantity = (EditText) rootView.findViewById(R.id.order_supplier_quantity_edit_text);

        mUpdateQuantityButton = (Button) rootView.findViewById(R.id.order_supplier_button);

        mItemSpinner.setEnabled(false);

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

        return rootView;
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /*
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    */


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
            Snackbar.make(getView(), "אחת מן השדות אינם תקינים אנא נסה שנית יא אח", Snackbar.LENGTH_SHORT);
            return;
        }
        ApiResources.getInstance(getActivity()).addToRequestQueue(getAddQuantityReq());
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
                (Request.Method.PUT, ApiResources.getInstance(getActivity()).getApiUrlWithPath("items/"+ mSelectedItem.getId()+"/"), buildItemJson(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Toast.makeText(getContext(),"כמות נוספה בהצלחה למוצר",Toast.LENGTH_SHORT).show();
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





