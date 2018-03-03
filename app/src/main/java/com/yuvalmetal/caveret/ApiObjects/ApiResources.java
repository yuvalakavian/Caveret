package com.yuvalmetal.caveret.ApiObjects;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yuvalmetal.caveret.R;

/**
 * Created by yuvalmetal on 08/10/2017.
 */

public class ApiResources {
    private static ApiResources mInstance;
    private static RequestQueue mRequestQueue;
    private static String mApiUrl;
    private static String mServerUrl;
    private static String mServerIpWithPort;
    private static Context mCtx;
    private static Employee mEmployee;

    private ApiResources(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mApiUrl = mCtx.getString(R.string.API_URL);
        mServerUrl = mCtx.getString(R.string.SERVER_URL);
        mServerIpWithPort = mCtx.getString(R.string.SERVER_IP_WITH_PORT);
        mEmployee = null;
    }

    public static String getApiUrlWithPath(String path) {
        return mApiUrl + path;
    }

    public static String getServerUrl() {
        return mServerUrl;
    }

    public static String getServerIpWithPort() {
        return mServerIpWithPort;
    }

    public static synchronized ApiResources getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApiResources(context);
        }
        return mInstance;
    }

    public Employee getmEmployee(){
        return mEmployee;
    }

    public void setEmployee(Employee employee){
        mEmployee = employee;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _queue = Volley.newRequestQueue(getActivity());
        _apiUrl = getString(R.string.API_URL);
    }
    */
}
