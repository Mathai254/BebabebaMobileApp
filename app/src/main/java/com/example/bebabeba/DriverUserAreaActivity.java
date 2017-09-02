package com.example.bebabeba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverUserAreaActivity extends AppCompatActivity {

    public String name, email, v_type, l_plate, phone_no, password, price, locality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_user_area);

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        final TextView tvEmailAddress = (TextView) findViewById(R.id.tvLocality);

        final TextView tvVehicleType = (TextView) findViewById(R.id.tvVehicleType);
        final TextView tvLicensePlate = (TextView) findViewById(R.id.tvLicensePlate);
        final TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        final Button bProceed = (Button) findViewById(R.id.bProceed);
        final Button bUpdate = (Button) findViewById(R.id.bUpdate);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        v_type = intent.getStringExtra("v_type");
        l_plate = intent.getStringExtra("l_plate");
        phone_no = intent.getStringExtra("phone_no");
        password = intent.getStringExtra("password");
        price = intent.getStringExtra("Price");
        locality = intent.getStringExtra("Locality");

        String message = name+" welcome to Bebabeba";
        welcomeMessage.setText(message);
        tvEmailAddress.setText(email);

        tvVehicleType.setText(v_type);
        tvLicensePlate.setText(l_plate);
        tvPhoneNumber.setText(phone_no);

        saveInfo();

        bProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String status = jsonResponse.getString("status");

                            if(success)
                            {
                                if(status.equals("approved"))
                                {
                                    Intent driverPintent = new Intent(DriverUserAreaActivity.this, DriverMapActivity.class);
                                    driverPintent.putExtra("email", email);
                                    DriverUserAreaActivity.this.startActivity(driverPintent);
                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DriverUserAreaActivity.this);
                                    builder.setMessage("Kindly wait for approval from the administrator")
                                            .setNegativeButton("OK", null)
                                            .create()
                                            .show();
                                }
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverUserAreaActivity.this);
                                builder.setMessage("An error occurred")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DriverProceedRequest driverProceedRequest = new DriverProceedRequest(email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DriverUserAreaActivity.this);
                queue.add(driverProceedRequest);
            }
        });

        //edit button listener
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverUpdateintent = new Intent(DriverUserAreaActivity.this, DriverUpdateActivity.class);
                driverUpdateintent.putExtra("price", price);
                driverUpdateintent.putExtra("locality", locality);
                driverUpdateintent.putExtra("name", name);
                driverUpdateintent.putExtra("email", email);
                driverUpdateintent.putExtra("v_type", v_type);
                driverUpdateintent.putExtra("l_plate", l_plate);
                driverUpdateintent.putExtra("phone_no", phone_no);

                //driverPintent.putExtra("email", email);
                DriverUserAreaActivity.this.startActivity(driverUpdateintent);
            }
        });
    }

    //Shared preferences
    public void saveInfo() {
        SharedPreferences sharedpref = getSharedPreferences("driverAllInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("v_type", v_type);
        editor.putString("l_plate", l_plate);
        editor.putString("phone_no", phone_no);
        editor.putString("password", password);

        editor.apply();

        //Toast.makeText(this, "Info saved!", Toast.LENGTH_LONG).show();
    }
}
