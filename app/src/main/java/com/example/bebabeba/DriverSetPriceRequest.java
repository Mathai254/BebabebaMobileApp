package com.example.bebabeba;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 5/11/2017.
 */

public class DriverSetPriceRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://192.168.201.1/Bebabeba/android/PriceUpdate.php";
    private Map<String, String> params;


    public DriverSetPriceRequest(String price, String Locality, String email, Response.Listener<String>listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("price", price);
        params.put("Locality", Locality);
        params.put("email", email);
    }
    public Map<String, String> getParams() {
        return params;
    }
}
