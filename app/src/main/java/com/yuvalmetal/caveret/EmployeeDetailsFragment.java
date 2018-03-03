package com.yuvalmetal.caveret;


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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.ApiObjects.Permission;
import com.yuvalmetal.caveret.ApiObjects.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public abstract class EmployeeDetailsFragment extends Fragment{
    protected EditText mFirstName;
    protected EditText mLastName;
    protected EditText mUserName;
    protected EditText mPassword;
    protected EditText mPhoneNumber;

    protected Spinner mUserRoleSpinner;
    protected Spinner mUserPermissionSpinner;

    protected Button mButton;

    protected ArrayList<Role> mRolesList;
    protected ArrayList<Permission> mPermissionsList;

    protected ArrayAdapter mRoleAdapter;
    protected ArrayAdapter mPermissionAdapter;

    protected Role mSelectedRole;
    protected Permission mSelectedPermission;

    public EmployeeDetailsFragment() {
    }

    /*
    public static EmployeeDetailsFragment newInstance(); {

        EmployeeDetailsFragment fragment = new EmployeeDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLists();
        initSpinnersAdapters();

        receiveRoles();
        receivePermissions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.employee_detalis, container, false);

        initView(rootView);
        attachSpinnerAdapters();
        setViewsListeners();
        return rootView;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void receivePermissions() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ApiResources.getInstance(getContext()).getApiUrlWithPath("permissions/"), null, new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {
                        setPermissionList(response);
                        updatePermissionSpinner();
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

    public void setPermissionList(JSONArray response) {
        mPermissionsList = new ArrayList<Permission>();
        for (int i = 0; i < response.length(); i++) {
            JsonParser parser = new JsonParser();
            JsonElement mJson = null;
            try {
                mJson = parser.parse(response.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();

            Permission item = gson.fromJson(mJson, Permission.class);

            mPermissionsList.add(item);
        }
    }

    private void updatePermissionSpinner() {
        Permission defaultPermission = new Permission();
        defaultPermission.Name = "בחר הרשאה";
        mPermissionAdapter.clear();
        mPermissionAdapter.add(defaultPermission);
        mPermissionAdapter.addAll(mPermissionsList);
        mPermissionAdapter.notifyDataSetChanged();

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void receiveRoles() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ApiResources.getInstance(getContext()).getApiUrlWithPath("roles/"), null, new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray response) {
                        setRolesList(response);
                        updateRolesSpinner();
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


    public void setRolesList(JSONArray response) {
        mRolesList = new ArrayList<Role>();
        for (int i = 0; i < response.length(); i++) {
            JsonParser parser = new JsonParser();
            JsonElement mJson = null;
            try {
                mJson = parser.parse(response.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();

            Role item = gson.fromJson(mJson, Role.class);

            mRolesList.add(item);
        }
    }

    private void updateRolesSpinner() {
        Role defaultRole = new Role();
        defaultRole.Name = "בחר תפקיד";
        mRoleAdapter.clear();
        mRoleAdapter.add(defaultRole);
        mRoleAdapter.addAll(mRolesList);
        mRoleAdapter.notifyDataSetChanged();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void initSpinnersAdapters() {
        mRoleAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mRolesList);
        mPermissionAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mPermissionsList);
    }

    private void initLists() {
        mRolesList = new ArrayList<Role>();
        mPermissionsList = new ArrayList<Permission>();
    }



    private void setViewsListeners() {
        setSpinnersListeners();
        setButtonListeners();
    }

    private void setSpinnersListeners() {
        mUserRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedRole = (Role) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mUserPermissionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPermission = (Permission) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setButtonListeners() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preformAction();
            }
        });

    }

    private void preformAction(){
        if(!validate()){
            Snackbar.make(getView(),"אירעה שגיאה במילוי הפרטים אנא נסה שנית",Snackbar.LENGTH_SHORT);
            return;
        }

        ApiResources.getInstance(getContext()).addToRequestQueue(getActionRequest());
    }

    protected abstract Request<Object> getActionRequest();


    private void attachSpinnerAdapters() {
        mUserRoleSpinner.setAdapter(mRoleAdapter);
        mUserRoleSpinner.setSelection(1);
        mUserPermissionSpinner.setAdapter(mPermissionAdapter);
        mUserRoleSpinner.setSelection(1);
    }

    private void initView(View rootView) {
        mFirstName = (EditText) rootView.findViewById(R.id.employee_details_first_name);
        mLastName = (EditText) rootView.findViewById(R.id.employee_details_last_name);
        mPhoneNumber = (EditText) rootView.findViewById(R.id.employee_details_phone_number);
        mUserName = (EditText) rootView.findViewById(R.id.employee_details_employee_username);
        mPassword = (EditText) rootView.findViewById(R.id.employee_details_password);

        mUserRoleSpinner = (Spinner)rootView.findViewById(R.id.employee_details_role_spinner);
        mUserPermissionSpinner = (Spinner)rootView.findViewById(R.id.employee_details_permission_spinner);

        mButton = (Button)rootView.findViewById(R.id.employee_details_send_button);

        setButtonLabel();

        mFirstName.requestFocus();
    }

    protected abstract void setButtonLabel();


    protected boolean validate() {
        boolean valid = true;


        if(mFirstName.getText().toString().isEmpty()){
            mFirstName.setError("נא מלא שם פרטי");
            valid = false;
        } else{
            mFirstName.setError(null);
        }

        if(mLastName.getText().toString().isEmpty()){
            mLastName.setError("נא מלא שם משפחה");
            valid = false;
        } else{
            mLastName.setError(null);
        }

        if(mUserName.getText().toString().isEmpty()){
            mUserName.setError("נא מלא שם משתמש");
            valid = false;
        } else{
            mUserName.setError(null);
        }

        if(mPassword.getText().toString().isEmpty()){
            mPassword.setError("נא מלא סיסמא");
            valid = false;
        } else{
            mPassword.setError(null);
        }

        if(mPhoneNumber.getText().toString().isEmpty()){
            mPhoneNumber.setError("נא מלא מספר טלפון");
            valid = false;
        } else{
            mPhoneNumber.setError(null);
        }

        return valid;
    }

    protected abstract JSONObject buildEmployeeJson();


}
