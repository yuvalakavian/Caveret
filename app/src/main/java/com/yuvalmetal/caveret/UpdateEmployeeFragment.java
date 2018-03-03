package com.yuvalmetal.caveret;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateEmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateEmployeeFragment extends EmployeeDetailsFragment {
    private Employee mEmployee;

    public UpdateEmployeeFragment(Employee employee) {
        // Required empty public constructor
        mEmployee = employee;
    }


    @Override
    protected Request<Object> getActionRequest() {
        return getUpdateEmployeeReq();
    }

    public Request getUpdateEmployeeReq() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, ApiResources.getInstance(getActivity()).getApiUrlWithPath("employees/"+ mEmployee.getId()+"/"), buildEmployeeJson(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        Toast.makeText(getContext(),"פרטי עובד התעדכנו בהצלחה",Toast.LENGTH_SHORT).show();
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
    protected void setButtonLabel() {
        mButton.setText("עדכן פרטי עובד");
    }

    @Override
    protected JSONObject buildEmployeeJson() {

            Employee employee = new Employee(mEmployee.getId(),mFirstName.getText().toString(),mLastName.getText().toString(),Long.parseLong(mPhoneNumber.getText().toString()),
                    mSelectedPermission,mSelectedRole,mUserName.getText().toString(),mPassword.getText().toString());
            JSONObject obj = null;
            Gson gson = new Gson();

            try {
                obj = new JSONObject(gson.toJson(employee));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("TEST",obj.toString());
            return obj;

    }

    public static UpdateEmployeeFragment newInstance(Employee employee) {
        UpdateEmployeeFragment fragment = new UpdateEmployeeFragment(employee);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void setViewValuesByEmployee(Employee employee){
        mFirstName.setText(employee.FirstName);
        mLastName.setText(employee.LastName);
        mPhoneNumber.setText(String.valueOf(employee.PhoneNumber));
        mUserName.setText(employee.UserName);
        mPassword.setText(employee.Password);
        Log.d("TEST",employee.EmployeeRole.getId()+ " " +employee.EmployeePermission.getId());
        mUserRoleSpinner.setSelection(employee.EmployeeRole.getId() + 1);
        mUserPermissionSpinner.setSelection(employee.EmployeePermission.getId() + 1);
        mSelectedRole = employee.EmployeeRole;
        mSelectedPermission = employee.EmployeePermission;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View superRootView = super.onCreateView(inflater, container, savedInstanceState);
        setViewValuesByEmployee(mEmployee);
        return superRootView;
    }
}
