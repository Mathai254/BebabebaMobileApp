package com.example.bebabeba;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookingsActivity extends ListActivity {

    String json_url = "http://192.168.201.1/Bebabeba/android/driverRetrieveBookings.php";
    public String[] BOOKINGS, PASSENGERSEMAILS, BOOKINGSDATE, BOOKINGSTIME, BOOKINGID;
    String driverEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =getIntent();
        driverEmail = intent.getStringExtra("driverEmail");


        final TextView tvDriverBookings = (TextView)findViewById(R.id.tvDriverBookings);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("driverEmail", driverEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, jsonArray,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        int count = 0;
                        BOOKINGS  = new String[response.length()];
                        BOOKINGID = new String[response.length()];
                        PASSENGERSEMAILS  = new String[response.length()];
                        BOOKINGSDATE = new String[response.length()];
                        BOOKINGSTIME = new String[response.length()];

                        try
                        {
                            JSONObject jsonObject1 = null;
                            jsonObject1 = response.getJSONObject(0);

                            if(jsonObject1.getString("drivers_email").equals("null"))
                            {
                                setContentView(R.layout.activity_bookings);
                                tvDriverBookings.setText("No bookings found");
                            }
                            else
                            {
                                while (count < response.length()) {
                                    JSONObject jsonObject2 = response.getJSONObject(count);
                                    StringBuffer buffer = new StringBuffer();
                                    buffer.append(jsonObject2.getString("source") + " " + " to ");
                                    buffer.append(jsonObject2.getString("destination")+" \n");

                                    String booking_time = jsonObject2.getString("booking_time");
                                    String[] strings = booking_time.split(":");
                                    int i = Integer.parseInt(strings[1]);
                                    if(i<10)
                                    {
                                        booking_time = strings[0]+":0"+strings[1];
                                    }

                                    String booking_date = jsonObject2.getString("booking_date");
                                    String[] strings1 = booking_date.split(":");
                                    booking_date = strings1[0]+"/"+strings1[1]+"/"+strings1[2];

                                    buffer.append(booking_date +" ");
                                    buffer.append(booking_time +"hrs");

                                    BOOKINGS[count] = buffer.toString();
                                    BOOKINGID[count] = jsonObject2.getString("booking_id");
                                    PASSENGERSEMAILS[count] = jsonObject2.getString("passengers_email");
                                    BOOKINGSDATE[count] = jsonObject2.getString("booking_date");
                                    BOOKINGSTIME[count] = jsonObject2.getString("booking_time");
                                    count++;
                                }
                                createList();
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getmInstance(BookingsActivity.this).addToRequestque(jsonArrayRequest);


    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(BookingsActivity.this, DriverBookingDisplay.class);
        intent.putExtra("booking_id", BOOKINGID[position]);
        intent.putExtra("passengerEmail", PASSENGERSEMAILS[position]);
        intent.putExtra("booking_date", BOOKINGSDATE[position]);
        intent.putExtra("booking_time", BOOKINGSTIME[position]);
        intent.putExtra("driverEmail", driverEmail);
        BookingsActivity.this.startActivity(intent);
    }

    public void createList()
    {
        setListAdapter(new ArrayAdapter< String >(this, android.R.layout.simple_list_item_1, BOOKINGS));
        getListView().setTextFilterEnabled(true);

    }
}
