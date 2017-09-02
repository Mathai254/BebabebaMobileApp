package com.example.bebabeba;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 1/19/2017.
 */

public class DriverRegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://192.168.201.1/Bebabeba/android/DriverRegister.php";
    private Map<String, String> params;

    public DriverRegisterRequest(String name, String v_type, String l_plate, String phone_no, String email, String password, String status, Response.Listener<String>listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("v_type", v_type);
        params.put("l_plate", l_plate);
        params.put("phone_no", phone_no);
        params.put("email", email);
        params.put("password", password);
        params.put("status", status);
    }
    public Map<String, String> getParams() {
        return params;
    }
}
