package com.example.bebabeba;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PassengerWelcomeActivity extends AppCompatActivity {

    public String name, email, phone_no;
    private String Url = "http://192.168.201.1/Bebabeba/android/checkApproval.php";
    public boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_welcome);

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        final TextView tvEmailAddress = (TextView) findViewById(R.id.tvLocality);
        final TextView tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        final Button bProceed = (Button) findViewById(R.id.bProceed);
        final Button bUpdate = (Button) findViewById(R.id.bUpdate);
        final Button bViewBookings = (Button) findViewById(R.id.bViewBookings);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone_no = intent.getStringExtra("phone_no");

        String message = name+" welcome to Bebabeba";
        welcomeMessage.setText(message);
        tvEmailAddress.setText(email);
        tvPhoneNumber.setText(phone_no);

        bProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String approved = jsonObject.getString("approved");

                                    if(approved.equals("true"))
                                    {
                                        Intent proceedIntent = new Intent(PassengerWelcomeActivity.this, PassengerUserAreaActivity.class);
                                        proceedIntent.putExtra("name", name);
                                        proceedIntent.putExtra("email", email);
                                        proceedIntent.putExtra("phone_no", phone_no);
                                        PassengerWelcomeActivity.this.startActivity(proceedIntent);
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PassengerWelcomeActivity.this);
                                        builder.setMessage("Kindly wait for approval from the Administrator")
                                                .setNegativeButton("OK", null)
                                                .create()
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        return params;
                    }
                };
                MySingleton.getmInstance(PassengerWelcomeActivity.this).addToRequestque(stringRequest);

            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(PassengerWelcomeActivity.this, PassengerEditActivity.class);
                editIntent.putExtra("name", name);
                editIntent.putExtra("email", email);
                editIntent.putExtra("phone_no", phone_no);
                PassengerWelcomeActivity.this.startActivity(editIntent);
            }
        });

        bViewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingsIntent = new Intent(PassengerWelcomeActivity.this, PassengerViewBookings.class);
                bookingsIntent.putExtra("passengerEmail", email);
                bookingsIntent.putExtra("name", name);
                bookingsIntent.putExtra("phone_no", phone_no);
                PassengerWelcomeActivity.this.startActivity(bookingsIntent);
            }
        });
    }
}
