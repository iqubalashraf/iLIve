package com.ilve.ilive.utiles;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ilve.ilive.ApplicationClass;
import com.ilve.ilive.interfaces.NetworkResponse;

/**
 * Created by ashrafiqubal on 17/02/18.
 */

public class GenralUtiles {
    private static final String IDENTIFIER = "com.ilve.ilive.utiles";
    private static final String KEY_PROBLEMS = "com.ilve.ilive.utiles";
    private static final String KEY_PHSYCAL_EXAMNATIONS = "com.ilve.ilive.utiles";
    private static final String KEY_DIAGNOSIS = "com.ilve.ilive.utiles";
    private static final String KEY_TETS = "com.ilve.ilive.utiles";

    public static void showMessage(String msg) {
        Toast.makeText(ApplicationClass.getParentContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void performNetwrokRequest(Activity activity, String url, final NetworkResponse networkResponse, final int requestCode){
        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        networkResponse.onNetworkResponse(requestCode, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }
}
