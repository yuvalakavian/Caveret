package com.yuvalmetal.caveret.Fragments;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.R;

import org.json.JSONObject;

/**
 * Created by yuvalmetal on 10/11/2017.
 */

public class DeleteEmployeeDialog extends DialogFragment {
    private Employee mEmployee;

    public DeleteEmployeeDialog(Employee item){
        mEmployee = item;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_item_dialog_message)
                .setPositiveButton(R.string.delete_item_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        preformDeleteEmployee();
                    }
                })
                .setNegativeButton(R.string.delete_item_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void preformDeleteEmployee() {
        ApiResources.getInstance(getActivity()).addToRequestQueue(getDeleteEmployeeReq());
    }


    public Request getDeleteEmployeeReq() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.DELETE, ApiResources.getInstance(getActivity()).getApiUrlWithPath("employees/"+ mEmployee.getId()+"/"), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
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
}
