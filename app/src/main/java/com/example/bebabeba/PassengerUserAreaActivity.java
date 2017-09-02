package com.example.bebabeba;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PassengerUserAreaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String vehicle_selected;
    public DatabaseHelper myDb;
    public String json_url = "http://192.168.201.1/Bebabeba/android/locationinfo.php";
    public String[] DRIVERS, DRIVERSNAMES, DRIVERSPHONENUMBERS, DRIVERSEMAILS, DRIVERSPRICES;
    public Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_user_area);

        final Context mContext = getApplicationContext();

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        final EditText etLocality = (EditText) findViewById(R.id.etLocality);


        //final TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        final Button bSearch = (Button) findViewById(R.id.bSearch);

        final Spinner spinner = (Spinner) findViewById(R.id.vSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.vehicle_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String email = intent.getStringExtra("email");
        final String phone_no = intent.getStringExtra("phone_no");

        //final String phone_no = intent.getStringExtra("phone_no");

        String message = "PLEASE SELECT YOUR \n"+" "+"\nDESIRED VEHICLE TYPE and \n"+" "+"\nTYPE THE LOCALITY below:";
        welcomeMessage.setText(message);
        //tvEmailAddress.setText(email);

        //tvPhoneNumber.setText(phone_no);

        myDb = new DatabaseHelper(this);
        myDb.clearSqlLite();

        bSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                b = new Bundle();
                final String Locality = etLocality.getText().toString();

                JSONArray jsonArray = new JSONArray();
                final JSONObject jsonObject = new JSONObject();
                try
                {
                    jsonObject.put("v_type", vehicle_selected);
                    jsonObject.put("locality", Locality);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, jsonArray,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                int count = 0;
                                DRIVERS  = new String[response.length()];
                                DRIVERSNAMES  = new String[response.length()];
                                DRIVERSPHONENUMBERS  = new String[response.length()];
                                DRIVERSEMAILS  = new String[response.length()];
                                DRIVERSPRICES = new String[response.length()];

                                JSONObject jsonObject1 = null;
                                try {
                                    jsonObject1 = response.getJSONObject(0);

                                    if(jsonObject1.getString("name").equals("null"))
                                    {
                                        String error = jsonObject1.getString("error");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PassengerUserAreaActivity.this);
                                        builder.setMessage(error)
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                    else
                                    {
                                        while (count < response.length())
                                        {
                                            JSONObject jsonObject2 = response.getJSONObject(count);
                                            StringBuffer buffer = new StringBuffer();
                                            buffer.append(jsonObject2.getString("name")+" "+"Ksh ");
                                            buffer.append(jsonObject2.getString("price")+" "+"per km");

                                            DRIVERS[count] = buffer.toString();
                                            DRIVERSNAMES[count] = jsonObject2.getString("name");
                                            DRIVERSPHONENUMBERS[count] = jsonObject2.getString("phone_no");
                                            DRIVERSEMAILS[count] = jsonObject2.getString("email");
                                            DRIVERSPRICES[count] = jsonObject2.getString("price");

                                            count++;
                                        }
                                        b.putStringArray("drivers", DRIVERS);
                                        b.putStringArray("driversNames", DRIVERSNAMES);
                                        b.putStringArray("driversPhoneNos", DRIVERSPHONENUMBERS);
                                        b.putStringArray("driversEmails", DRIVERSEMAILS);
                                        b.putStringArray("driversPrices", DRIVERSPRICES);
                                        //Intent
                                        Intent intent = new Intent(PassengerUserAreaActivity.this, ListViewActivity.class);
                                        intent.putExtra("v_type", vehicle_selected);
                                        intent.putExtra("locality", Locality);
                                        intent.putExtra("passengerEmail", email);
                                        intent.putExtras(b);
                                        PassengerUserAreaActivity.this.startActivity(intent);
                                        //Toast.makeText(PassengerUserAreaActivity.this, DRIVERS.length +" ", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
                MySingleton.getmInstance(mContext).addToRequestque(jsonArrayRequest);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehicle_selected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
