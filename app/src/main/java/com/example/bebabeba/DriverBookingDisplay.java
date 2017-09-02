package com.example.bebabeba;

import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DriverBookingDisplay extends AppCompatActivity {

    private TextView tvSource, tvDestination, tvBookingDate, tvBookingTime, tvPrice, tvPassengerName, tvPassengerPhoneNo;
    String queryUrl1 = "http://192.168.201.1/Bebabeba/android/queryForDriverBooking.php";
    private String source, destination, booking_date, booking_time, price, driverName, driverPhoneNo, intentBooking_id;
    private Button bDialPassenger, bPaymentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_booking_display);

        Intent intent = getIntent();
        String driverEmail = intent.getStringExtra("driverEmail");
        String passengerEmail = intent.getStringExtra("passengerEmail");
        intentBooking_id = intent.getStringExtra("booking_id");
        String intentBooking_date = intent.getStringExtra("booking_date");
        String intentBooking_time = intent.getStringExtra("booking_time");

        driverQueryDb(passengerEmail, intentBooking_id);



        tvSource = (TextView)findViewById(R.id.tvSource);
        tvDestination = (TextView)findViewById(R.id.tvDestination);
        tvBookingDate = (TextView)findViewById(R.id.tvBookingDate);
        tvBookingTime = (TextView)findViewById(R.id.tvTime);
        tvPrice = (TextView)findViewById(R.id.tvPrice);
        tvPassengerName = (TextView)findViewById(R.id.tvPassengerName);
        tvPassengerPhoneNo = (TextView)findViewById(R.id.tvPassengerPhoneNo);

        tvSource.setText("From "+ source);
        tvDestination.setText("To " +destination);
        tvBookingDate.setText("Booking Date " + booking_date);
        tvBookingTime.setText("Booking time "+ booking_time);
        tvPrice.setText("Price Ksh " + price);
        tvPassengerName.setText("Passenger Name: " + driverName);
        tvPassengerPhoneNo.setText("Phone Number: " + driverPhoneNo);



        bPaymentInfo = (Button) findViewById(R.id.bPaymentInfo);




        bPaymentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DriverBookingDisplay.this, DriverPaymentActivity.class);
                intent.putExtra("intentBooking_id", intentBooking_id);
                DriverBookingDisplay.this.startActivity(intent);
            }
        });
    }



    public void driverQueryDb(final String passengerEmail, final String intentBooking_id)
    {
        //Toast.makeText(DriverBookingDisplay.this, passengerEmail+" "+intentBooking_id, Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, queryUrl1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(DriverBookingDisplay.this, response, Toast.LENGTH_LONG).show();


                            source = jsonObject.getString("source");

/*
                            destination = jsonObject.getString("destination");
                            booking_date = jsonObject.getString("booking_date");

                            booking_time = jsonObject.getString("booking_time");

                            price = jsonObject.getString("price");
                            driverName = jsonObject.getString("name");
                            driverPhoneNo = jsonObject.getString("phone_no");

                            String[] strings = booking_time.split(":");
                            int i = Integer.parseInt(strings[1]);
                            if(i<10)
                            {
                                booking_time = strings[0]+"0"+strings[1];
                            }

                            String[] strings1 = booking_date.split(":");
                            booking_date = strings1[0]+"/"+strings1[1]+"/"+strings1[2];
*/




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
                params.put("booking_id", intentBooking_id);
                params.put("passengerEmail", passengerEmail);

                return params;
            }
        };
        MySingleton.getmInstance(DriverBookingDisplay.this).addToRequestque(stringRequest);
    }

}
