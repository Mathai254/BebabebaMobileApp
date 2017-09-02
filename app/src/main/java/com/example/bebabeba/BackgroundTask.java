package com.example.bebabeba;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samuel Mathai on 4/23/2017.
 */

public class BackgroundTask {
    Context context;
    ArrayList<Contact> arrayList = new ArrayList<>();
    Contact contact = null;
    String json_url = "http://192.168.201.1/Bebabeba/android/locationinfo.php";
    public DatabaseHelper myDb;

    public BackgroundTask(Context context)
    {
        this.context = context;
    }

    //public ArrayList<Contact> getList(final String v_type, String locality) throws JSONException {
    public void getList(final String v_type, final String locality) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        //JSONObject jsonObject1 = new JSONObject();
        jsonObject.put("v_type", v_type);
        jsonObject.put("locality", locality);
        jsonArray.put(jsonObject);
        //jsonArray.put(jsonObject1);
        Log.d("v_type", v_type);
        Log.d("Response", jsonArray.toString());
        myDb = new DatabaseHelper(context);
        myDb.clearSqlLite();

        //Toast.makeText(context, jsonArray.toString(), Toast.LENGTH_LONG).show();
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url,jsonArray, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                int count = 0;
                //int length = response.length();

                //Toast.makeText(context, " "+length,Toast.LENGTH_LONG).show();

                myDb.clearSqlLite();

                while (count < response.length()) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(count);
                        //Toast.makeText(context, jsonObject.get("driver_id").toString(),Toast.LENGTH_LONG).show();

                        //sql lite

                        boolean isInserted = myDb.insertData(jsonObject.get("name").toString(), jsonObject.get("phone_no").toString(), jsonObject.get("Locality").toString());
                        if (isInserted) {
                            Toast.makeText(context, "Data inserted to sqlite", Toast.LENGTH_LONG).show();
                        }
                        else {
                            //Toast.makeText(context, "Data not Insertedto sqlite", Toast.LENGTH_LONG).show();
                        }

/*                        contact = new Contact(jsonObject.getString("driver_id"), jsonObject.getString("latitude"), jsonObject.getString("longitude"));
                        //Toast.makeText(context, contact.toString(),Toast.LENGTH_LONG).show();
                        arrayList.add(contact);
                        Log.d("Size", "Int"+arrayList.size());*/
                        count++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("Size AFter while loop", "Int"+arrayList.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }

        });
        MySingleton.getmInstance(context).addToRequestque(jsonArrayRequest);
        //Log.d("End Method", "Int"+arrayList.size());
        //Log.d("New After End", contact.getLatitude());
        //return arrayList;
    }

}
