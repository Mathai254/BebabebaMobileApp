package com.example.bebabeba;

import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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

public class RateDriver extends AppCompatActivity {

    public Button bSubmitDriverRating;
    public RatingBar driverRB;
    public TextView tvRateDriver;
    String booking_id;


    String submitUrl = "http://192.168.201.1/Bebabeba/android/submitDriverRating.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_driver);

        Intent intent = getIntent();
        booking_id = intent.getStringExtra("booking_id");
        //Toast.makeText(RateDriver.this, booking_id, Toast.LENGTH_LONG).show();

        listenerForRatingBar();
        onButtonClickedListener();

    }

    public void listenerForRatingBar()
    {
        driverRB = (RatingBar)findViewById(R.id.driverRatingBar);
        tvRateDriver = (TextView)findViewById(R.id.tvDriverRating);

        driverRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvRateDriver.setText("Driver Rating "+ String.valueOf(rating));
            }
        });
    }

    public void onButtonClickedListener()
    {
        driverRB = (RatingBar)findViewById(R.id.driverRatingBar);
        tvRateDriver = (TextView)findViewById(R.id.tvDriverRating);
        bSubmitDriverRating = (Button)findViewById(R.id.bSubmitRating);

        bSubmitDriverRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rating = String.valueOf(driverRB.getRating());
                submitRating(rating, booking_id);
            }
        });
    }

    public void submitRating (final String rating, final String booking_id)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, submitUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {

                            //Toast.makeText(RateDriver.this, response.toString(), Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");


                            if(success)
                            {
                                Toast.makeText(RateDriver.this, "Rating submitted!", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(RateDriver.this, "Rating not saved!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e)
                        {
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
                params.put("rating", rating);
                params.put("booking_id", booking_id);

                return params;
            }
        };
        MySingleton.getmInstance(RateDriver.this).addToRequestque(stringRequest);
    }
}
