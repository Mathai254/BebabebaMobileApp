package com.example.bebabeba;

import android.content.Intent;
import android.net.Uri;
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

public class PassengerDisplayBooking extends AppCompatActivity {

    String queryUrl = "http://192.168.201.1/Bebabeba/android/queryForBooking.php";
    public String source, destination, booking_date, booking_time, price, driverName, driverPhoneNo, booking_id, passengerName, passengerEmail, passengerPhoneNo, intentBooking_id, payableAmount, remainingBalance;
    public TextView tvSource, tvDestination, tvBookingDate, tvBookingTime, tvPrice, tvDriverName, tvDriverPhoneNo;
    public Button bDialDriver, bPassengerPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_display_booking);

        Intent intent = getIntent();
        String driverEmail = intent.getStringExtra("dEmail");
        passengerEmail = intent.getStringExtra("passengerEmail");
        passengerName = intent.getStringExtra("passengerName");
        passengerPhoneNo = intent.getStringExtra("passengerPhoneNo");
        intentBooking_id = intent.getStringExtra("booking_id");
        payableAmount = intent.getStringExtra("payableAmount");
        String intentBooking_date = intent.getStringExtra("booking_date");
        String intentBooking_time = intent.getStringExtra("booking_time");

        tvSource = (TextView)findViewById(R.id.tvSource);
        tvDestination = (TextView)findViewById(R.id.tvDestination);
        tvBookingDate = (TextView)findViewById(R.id.tvBookingDate);
        tvBookingTime = (TextView)findViewById(R.id.tvTime);
        tvPrice = (TextView)findViewById(R.id.tvPrice);
        tvDriverName = (TextView)findViewById(R.id.tvDriverName);
        tvDriverPhoneNo = (TextView)findViewById(R.id.tvDriverPhoneNo);
        bDialDriver = (Button)findViewById(R.id.bDialDriver);
        bPassengerPayment = (Button)findViewById(R.id.bPassengerPayment);

        passengerQueryDb(driverEmail, passengerEmail, intentBooking_date, intentBooking_time);

        bPassengerPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent paymentIntent = new Intent(PassengerDisplayBooking.this, PassengerPaymentActivity.class);
                paymentIntent.putExtra("booking_id", booking_id);
                paymentIntent.putExtra("totalAmount", price);
                paymentIntent.putExtra("driverPhoneNo", driverPhoneNo);
                paymentIntent.putExtra("driverName", driverName);
                paymentIntent.putExtra("passengerName", passengerName);
                paymentIntent.putExtra("passengerEmail", passengerEmail);
                paymentIntent.putExtra("passengerPhoneNo", passengerPhoneNo);
                paymentIntent.putExtra("remainingBalance", remainingBalance);

                PassengerDisplayBooking.this.startActivity(paymentIntent);
            }
        });

        bDialDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri intentUri = Uri.parse(driverPhoneNo);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(intentUri);
            }
        });


    }

    public void passengerQueryDb(final String driverEmail, final String passengerEmail, final String intentBooking_date, final String intentBooking_time)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, queryUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            booking_id = jsonObject.getString("booking_id");
                            source = jsonObject.getString("source");
                            //Toast.makeText(PassengerDisplayBooking.this, source, Toast.LENGTH_LONG).show();
                            destination = jsonObject.getString("destination");
                            booking_date = jsonObject.getString("booking_date");
                            String[] strings1 = booking_date.split(":");
                            booking_date = strings1[0]+"/"+strings1[1]+"/"+strings1[2];
                            booking_time = jsonObject.getString("booking_time");
                            remainingBalance = jsonObject.getString("remainingBalance");
                            String[] strings = booking_time.split(":");
                            int i = Integer.parseInt(strings[1]);
                            if(i<10)
                            {
                                booking_time = strings[0]+":0"+strings[1];
                            }
                            price = jsonObject.getString("price");
                            driverName = jsonObject.getString("name");
                            driverPhoneNo = jsonObject.getString("phone_no");



                            tvSource.setText("From "+ source);
                            tvDestination.setText("To " +destination);
                            tvBookingDate.setText("Booking Date " + booking_date);
                            tvBookingTime.setText("Booking time "+ booking_time);
                            tvPrice.setText("Price Ksh" + price);
                            tvDriverName.setText("Driver Name: " + driverName);
                            tvDriverPhoneNo.setText("Phone Number: " + driverPhoneNo);

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
                params.put("driverEmail", driverEmail);
                params.put("passengerEmail", passengerEmail);
                params.put("booking_date", intentBooking_date);
                params.put("booking_time", intentBooking_time);
                params.put("intentBooking_id", intentBooking_id);
                params.put("payableAmount", payableAmount);

                return params;
            }
        };
        MySingleton.getmInstance(PassengerDisplayBooking.this).addToRequestque(stringRequest);
    }
}
