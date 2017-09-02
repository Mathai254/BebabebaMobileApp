package com.example.bebabeba;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 7/23/2017.
 */

public class PassengerLocality extends StringRequest {
    private static final String LOCATION_SUBMIT_URL = "http://192.168.201.1/Bebabeba/android/LocalitySubmit.php";
    private Map<String, String> params;

    public PassengerLocality(String locality, Response.Listener<String>listener) {
        super(Request.Method.POST, LOCATION_SUBMIT_URL, listener, null);
        params = new HashMap<>();
        params.put("locality", locality);

    }
    public Map<String, String> getParams() {
        return params;
    }
}
