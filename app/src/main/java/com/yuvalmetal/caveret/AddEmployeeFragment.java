package com.yuvalmetal.caveret;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yuvalmetal on 11/11/2017.
 */

public class AddEmployeeFragment extends EmployeeDetailsFragment {

    public static AddEmployeeFragment newInstance(){

        AddEmployeeFragment fragment = new AddEmployeeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Request<Object> getActionRequest() {
        return getAddUserReq();
    }

    @Override
    protected void setButtonLabel() {
        mButton.setText("הוסף עובד");
    }

    public Request getAddUserReq() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, ApiResources.getInstance(getContext()).getApiUrlWithPath("employees/"), buildEmployeeJson(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(),"העובד התווסף בהצלחה",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                });
        return jsObjRequest;
    }

    @Override
    protected JSONObject buildEmployeeJson() {
        {
            Employee employee = new Employee(0,mFirstName.getText().toString(),mLastName.getText().toString(),Long.parseLong(mPhoneNumber.getText().toString()),
                    mSelectedPermission,mSelectedRole,mUserName.getText().toString(),mPassword.getText().toString());

            JSONObject obj = null;
            Gson gson = new Gson();

            try {
                obj = new JSONObject(gson.toJson(employee));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return obj;
        }
    }


}
