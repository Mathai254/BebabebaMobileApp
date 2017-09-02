package com.example.bebabeba;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import static com.example.bebabeba.R.id.tvTime;

public class PassengerBookingActivity extends AppCompatActivity {

    int year_x, month_x, day_x;
    static final int dateDIALOG_ID = 0;
    TextView tvDate, tvTime;
    String date, time, passengerEmail, driverEmail;
    public ProgressBar pb;

    static final int timeDIALOG_ID = 1;
    int hour_x, minute_x;
    public String bookingUrl = "http://192.168.201.1/Bebabeba/android/uploadBooking.php";
    public String destinationPriceUrl = "http://192.168.201.1/Bebabeba/android/validateDestination.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_booking);

        Intent intent = getIntent();
        String driverName = intent.getStringExtra("dName");
        driverEmail = intent.getStringExtra("dEmail");
        final String driverPhoneNo = intent.getStringExtra("dPhone");
        final String driverPrice = intent.getStringExtra("price");
        final String source = intent.getStringExtra("source");
        passengerEmail = intent.getStringExtra("passengerEmail");

        EditText etSource = (EditText) findViewById(R.id.etSource);
        final EditText etDestination = (EditText) findViewById(R.id.etDestination);
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvTime = (TextView)findViewById(R.id.tvTime);

        Button bDate = (Button)findViewById(R.id.bDate);
        Button bTime = (Button)findViewById(R.id.bTime);
        Button bSubmit = (Button)findViewById(R.id.bSubmit);

        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

        etSource.setText(source);

        final java.util.Calendar calendar = java.util.Calendar.getInstance();
        year_x = calendar.get(java.util.Calendar.YEAR);
        month_x = calendar.get(java.util.Calendar.MONTH);
        day_x = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        tvDate.setText(day_x+"/"+month_x+"/"+year_x);

        hour_x = new Time(System.currentTimeMillis()).getHours();
        minute_x = new Time(System.currentTimeMillis()).getMinutes();
        tvTime.setText(hour_x+":"+minute_x);

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOnButtonClick();
            }
        });

        bTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add booking details to db
                //validate date
                //search for the destination from google distance matrix api and return distance or null

                //validate destination and confirm price

                pb.setVisibility(View.VISIBLE);
                final String destination = etDestination.getText().toString();
                date = day_x + ":" + month_x + ":" + year_x;
                time = hour_x + ":" + minute_x;

                destinationPriceCheck(source, destination, driverPrice);

                //add booking to Db




            }
        });
    }

    public void showDialogOnButtonClick()
    {
        showDialog(dateDIALOG_ID);
    }

    public void showTimePickerDialog()
    {
        showDialog(timeDIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id == dateDIALOG_ID)
        {
            return new DatePickerDialog(this, datePickerListener, year_x, month_x, day_x);
        }
        else if (id == timeDIALOG_ID)
        {
            return new TimePickerDialog(this, timePickerListener, hour_x, minute_x, false);
        }
        else
        {
            return null;
        }
    }

    protected TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            hour_x = hourOfDay;
            minute_x = minute;
            if (minute_x < 10)
            {
                tvTime.setText(hour_x+":0"+minute);
            }
            else
            {
                tvTime.setText(hour_x+":"+minute);
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;

            tvDate.setText(day_x+"/"+month_x+"/"+year_x);
        }
    };

    public void addBookingToDb(final String date, final String time, final String source, final String destination, final String totalPrice, final String driverEmail, final String passengerEmail)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, bookingUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");

                            Toast.makeText(PassengerBookingActivity.this, Response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PassengerBookingActivity.this, PassengerViewBookings.class);
                            PassengerBookingActivity.this.startActivity(intent);


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
                params.put("date", date);
                params.put("time", time);
                params.put("source", source);
                params.put("destination", destination);
                params.put("totalPrice", totalPrice);
                params.put("driverEmail", driverEmail);
                params.put("passengerEmail", passengerEmail);

                return params;
            }
        };
        MySingleton.getmInstance(PassengerBookingActivity.this).addToRequestque(stringRequest);
    }


    public void destinationPriceCheck(final String source, final String destination, final String driverPrice)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, destinationPriceUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("true"))
                            {
                                final String totalPrice = jsonObject.getString("totalPrice");

                                pb.setVisibility(View.GONE);

                                AlertDialog.Builder builder = new AlertDialog.Builder(PassengerBookingActivity.this);
                                builder.setTitle("Confirm Booking");
                                builder.setMessage("Confirm booking from " + source + " to " + destination + " for Ksh" + totalPrice);
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        addBookingToDb(date, time, source, destination, totalPrice, driverEmail, passengerEmail);
                                    }
                                });
                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //nothing happens
                                    }
                                });

                                builder.show();

                            }
                            else
                            {
                                String error = jsonObject.getString("error");
                                AlertDialog.Builder builder = new AlertDialog.Builder(PassengerBookingActivity.this);
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
                params.put("source", source);
                params.put("destination", destination);
                params.put("driverPrice", driverPrice);

                return params;
            }
        };
        MySingleton.getmInstance(PassengerBookingActivity.this).addToRequestque(stringRequest);
    }
}
