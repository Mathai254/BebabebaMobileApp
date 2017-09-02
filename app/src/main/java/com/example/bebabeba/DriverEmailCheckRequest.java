package com.example.bebabeba;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 8/15/2017.
 */

public class DriverEmailCheckRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://192.168.201.1/Bebabeba/android/emailCheck.php";
    private Map<String, String> params;

    public DriverEmailCheckRequest(String email,Response.Listener<String>listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
    }
    public Map<String, String> getParams() {
        return params;
    }
}
