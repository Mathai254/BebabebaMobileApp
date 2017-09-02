package com.example.bebabeba;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 4/16/2017.
 */

public class PassengerLocationSubmit extends StringRequest {
    private static final String LOCATION_SUBMIT_URL = "http://192.168.201.1/Bebabeba/android/PassengerLocationSubmit.php";
    private Map<String, String> params;

    public PassengerLocationSubmit(String lat, String lng, String email, Response.Listener<String>listener) {
        super(Request.Method.POST, LOCATION_SUBMIT_URL, listener, null);
        params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("email", email);

    }
    public Map<String, String> getParams() {
        return params;
    }
}
