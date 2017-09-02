package com.example.bebabeba;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PassengerPaymentActivity extends AppCompatActivity {

    public TextView tvAmount, tvBalance, tvPaymentInfo;
    public EditText etPaymentAmount;
    public Button bPay, bRateDriver;
    int year_x, month_x, day_x, hour_x, minute_x;
    String queryUrl = "http://192.168.201.1/Bebabeba/android/paymentSubmit.php";
    String balanceUrl = "http://192.168.201.1/Bebabeba/android/getPendingBalance.php";

    public String payableAmount, booking_id, pendingBalance, passengerName, passengerEmail, passengerPhoneNo, driverPhoneNo, driverName, remainingBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_payment);

        Intent intent = getIntent();
        booking_id = intent.getStringExtra("booking_id");
        passengerName = intent.getStringExtra("passengerName");
        passengerEmail = intent.getStringExtra("passengerEmail");
        passengerPhoneNo = intent.getStringExtra("passengerPhoneNo");
        remainingBalance = intent.getStringExtra("remainingBalance");

        final String booking_id = intent.getStringExtra("booking_id");
        payableAmount = intent.getStringExtra("totalAmount");
        pendingBalance = remainingBalance;
        //getPendingBalance(booking_id, payableAmount);
        driverPhoneNo = intent.getStringExtra("driverPhoneNo");
        driverName = intent.getStringExtra("driverName");



        tvAmount = (TextView)findViewById(R.id.tvAmount);
        tvBalance = (TextView)findViewById(R.id.tvBalance);
        tvPaymentInfo = (TextView)findViewById(R.id.tvPaymentInfo);

        etPaymentAmount = (EditText) findViewById(R.id.etAmount);

        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        year_x = calendar.get(java.util.Calendar.YEAR);
        month_x = calendar.get(java.util.Calendar.MONTH);
        day_x = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        final String paymentDate = day_x+":"+month_x+":"+year_x;

        hour_x = new Time(System.currentTimeMillis()).getHours();
        minute_x = new Time(System.currentTimeMillis()).getMinutes();
        final String paymentTime = hour_x + ":" + minute_x;

        tvAmount.setText("Total Amount: " + payableAmount);
        tvBalance.setText("Pending balance: "+ pendingBalance);
        tvPaymentInfo.setText("Paybill No: 34213 \n\nAccount No: "+ driverPhoneNo + "\n\nAccount Name: "+driverName);

        bPay = (Button)findViewById(R.id.bPay);
        bRateDriver = (Button)findViewById(R.id.bRateDriver);

        bPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountInserted = etPaymentAmount.getText().toString();

                submitPayment(booking_id, paymentDate, paymentTime, payableAmount, amountInserted);
            }
        });

        bRateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassengerPaymentActivity.this, RateDriver.class);
                intent.putExtra("booking_id", booking_id);
                PassengerPaymentActivity.this.startActivity(intent);
            }
        });

    }

    public void submitPayment(final String booking_id, final String paymentDate, final String paymentTime, final String payableAmount, final String amountInserted)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, queryUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success)
                            {
                                Toast.makeText(PassengerPaymentActivity.this, "Payment submitted successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PassengerPaymentActivity.this, PassengerWelcomeActivity.class);
                                intent.putExtra("name", passengerName);
                                intent.putExtra("email", passengerEmail);
                                intent.putExtra("phone_no", passengerPhoneNo);

                                PassengerPaymentActivity.this.startActivity(intent);
                            }
                            else
                            {
                                String error = jsonObject.getString("error");
                                AlertDialog.Builder builder = new AlertDialog.Builder(PassengerPaymentActivity.this);
                                builder.setMessage(error)
                                        .setNegativeButton("Retry", null)
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
                params.put("booking_id", booking_id);
                params.put("paymentDate", paymentDate);
                params.put("paymentTime", paymentTime);
                params.put("payableAmount", payableAmount);
                params.put("amountInserted", amountInserted);

                return params;
            }
        };
        MySingleton.getmInstance(PassengerPaymentActivity.this).addToRequestque(stringRequest);
    }

/*    public void getPendingBalance(final String bookingId, final String payable)
    {
        //Toast.makeText(PassengerPaymentActivity.this, bookingId, Toast.LENGTH_LONG).show();
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, balanceUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(PassengerPaymentActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();

                            boolean priorPayment = jsonObject.getBoolean("priorPayment");

                            if(priorPayment)
                            {
                                pendingBalance = jsonObject.getString("remainingBalance");
                                //Toast.makeText(PassengerPaymentActivity.this, pendingBalance.toString(), Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                //nothing is done
                                pendingBalance = payable;

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PassengerPaymentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bookingId", bookingId);
                params.put("payable", payable);
                return params;
            }
        };
        MySingleton.getmInstance(PassengerPaymentActivity.this).addToRequestque(stringRequest1);
    }*/
}
