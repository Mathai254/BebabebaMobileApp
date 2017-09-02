package com.example.bebabeba;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 1/21/2017.
 */

public class DriverLoginRequest extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "http://192.168.201.1/Bebabeba/android/DriverLogin.php";
    private Map<String, String> params;

    public DriverLoginRequest(String email, String password, Response.Listener<String>listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }
    public Map<String, String> getParams() {
        return params;
    }
}
