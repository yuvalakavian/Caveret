package com.yuvalmetal.caveret.Activites;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.ApiObjects.Permission;
import com.yuvalmetal.caveret.ApiObjects.Role;
import com.yuvalmetal.caveret.R;
import com.yuvalmetal.caveret.RecyclerViewAdapters.AdapterDataPasser;

import org.json.JSONException;
import org.json.JSONObject;
/*
public class AddEmployeeActivity extends AppCompatActivity {
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mPhoneNumber;
    private Spinner mUserRole;
    private Button mButton;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("הוסף עובד");
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        setContentView(R.layout.activity_add_employee);

        mFirstName = (EditText)findViewById(R.id.add_employee_first_name);
        mLastName = (EditText)findViewById(R.id.add_employee_last_name);
        mUserName =(EditText)findViewById(R.id.add_employee_username);
        mPassword = (EditText)findViewById(R.id.add_employee_password);
        mPhoneNumber = (EditText)findViewById(R.id.add_employee_phone_number);

        mUserRole = (Spinner)findViewById(R.id.add_employee_role);

        mButton = (Button)findViewById(R.id.add_employee_send_button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

        mFirstName.requestFocus();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mUserRole.setAdapter(adapter);

    }

    public Request getAddUserReq() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, ApiResources.getInstance(this).getApiUrlWithPath("employees/"), buildEmployeeJson(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getBaseContext(),"העובד התווסף בהצלחה",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        return jsObjRequest;
    }

    private JSONObject buildEmployeeJson() {
        Employee employee = new Employee(0,mFirstName.getText().toString(),mLastName.getText().toString(),Long.parseLong(mPhoneNumber.getText().toString()),
                new Permission(2,"Regular"),getRole(),mUserName.getText().toString(),mPassword.getText().toString());

        JSONObject obj = null;
        Gson gson = new Gson();

        try {
            obj = new JSONObject(gson.toJson(employee));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    private void addEmployee() {
        if(!validate()){
            Snackbar.make(getCurrentFocus(),"אירעה שגיאה במילוי הפרטים אנא נסה שנית",Snackbar.LENGTH_SHORT);
            return;
        }

        ApiResources.getInstance(this).addToRequestQueue(getAddUserReq());
    }

    private boolean validate() {
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

    public Role getRole() {
        String roleName = "";
        if(mUserRole.getSelectedItemPosition() == 0){
            roleName = "Cashier";
        }
        if(mUserRole.getSelectedItemPosition() == 1){
            roleName = "Cashier";
        }
        return new Role(mUserRole.getSelectedItemPosition() + 2,roleName);
    }
}

*/