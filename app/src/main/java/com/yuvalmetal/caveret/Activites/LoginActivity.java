package com.yuvalmetal.caveret.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yuvalmetal.caveret.ApiObjects.Employee;
import com.yuvalmetal.caveret.ApiObjects.ApiResources;
import com.yuvalmetal.caveret.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText _username;
    EditText _password;
    Button _loginButton;
    ProgressBar _progressBar;
    String _url;
   // RequestQueue _queue;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("התחברות");

        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        _loginButton = (Button) findViewById(R.id.login_button);
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               login();
            }
        });

        _username = (EditText) findViewById(R.id.username);
        _password = (EditText) findViewById(R.id.password);
        _progressBar = (ProgressBar) findViewById(R.id.progressBar);
       // _url = getString(R.string.API_URL)+"employees/login/";
       // _queue = ApiResources.getInstance(this).getRequestQueue();
    }

    /**
     *
     * @param message
     */
    private void onLoginFailed(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
    }

    private void onLoginSuccess(JSONObject result) {

        ApiResources.getInstance(this).setEmployee(createEmployeeFromJSON(result));

        Intent returnIntent = getIntent();
        returnIntent.putExtra("login_user_title", ApiResources.getInstance(this).getmEmployee().getUserName());
        returnIntent.putExtra("login_user_name",ApiResources.getInstance(this).getmEmployee().getFirstName() + "  " + ApiResources.getInstance(this).getmEmployee().getLastName());
        setResult(Activity.RESULT_OK,returnIntent);
        Toast.makeText(getBaseContext(), "ההתחברות התבצעה בהצלחה", Toast.LENGTH_LONG).show();
    }

    private Employee createEmployeeFromJSON(JSONObject result) {
        JsonParser parser = new JsonParser();
        JsonElement mJson = null;
        mJson = parser.parse(result.toString());
        Gson gson = new Gson();
        return gson.fromJson(mJson, Employee.class);
    }

    /**
     *
     * @param status
     * @param message
     */
    public void onLoginFinished(Boolean status, Object message){
        if(status){
            onLoginSuccess((JSONObject) message);
            finish();
        }
        else {
            onLoginFailed((String)message);
        }

        _progressBar.setIndeterminate(false);
        _progressBar.setVisibility(View.GONE);

        _loginButton.setEnabled(true);

    }

    /**
     *
     * @return
     */
    public JSONObject buildLoginRequestJSON(){
        JSONObject requestJson = new JSONObject();

        try {
            requestJson.put("username", _username.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            requestJson.put("password", _password.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestJson;
    }

    /**
     *
     */
    public Request getAuthReq(){
        Log.d("Testing connection string!!!",ApiResources.getInstance(this).getApiUrlWithPath("employees/login/"));
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, ApiResources.getInstance(this).getApiUrlWithPath("employees/login/"), buildLoginRequestJSON(), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        onLoginFinished(true,response);
                        /*
                        ArrayList<String> resultsArray = new ArrayList<String>();
                        try {
                            resultsArray.add(response.getString("UserName"));
                            resultsArray.add(response.getString("FirstName") + " " + response.getString("LastName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        */
                        //onLoginFinished(true,resultsArray);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                        onLoginFinished(false,"משתמש לא קיים ככל הנראה");
                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return jsObjRequest;
    }

    /**
     *
     */
    public void login() {

        if (!validate()) {
            onLoginFailed("משתמש או סיסמא לא תקינים");
            return;
        }

        _loginButton.setEnabled(false);

        _progressBar.setIndeterminate(true);
        _progressBar.setVisibility(View.VISIBLE);

        // TODO: Implement your own authentication logic here.
        // Add the request to the RequestQueue.
        ApiResources.getInstance(this).addToRequestQueue(getAuthReq());
    }


    /**
     *
     * @return
     */
    public boolean validate(){
        boolean valid = true;

        String username = _username.getText().toString();
        String password = _password.getText().toString();

        if(username.isEmpty()){
            _username.setError("שם משתמש לא יכול להיות ריק");
            valid = false;
        } else{
            _username.setError(null);
        }

        if (password.isEmpty()) {
            _password.setError("סיסמא לא יכולה להיות ריקה");
            valid = false;
        } else {
            _password.setError(null);
        }

        return valid;
    }

}
