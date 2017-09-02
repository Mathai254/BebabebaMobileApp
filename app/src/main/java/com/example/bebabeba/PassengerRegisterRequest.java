package com.example.bebabeba;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 1/21/2017.
 */

public class PassengerRegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://192.168.201.1/Bebabeba/android/PassengerRegister.php";
    private Map<String, String> params;

    public PassengerRegisterRequest(String name, String phone_no, String email, String password, String status, Response.Listener<String>listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("phone_no", phone_no);
        params.put("email", email);
        params.put("password", password);
        params.put("status", status);
    }
    public Map<String, String> getParams() {
        return params;
    }
}
