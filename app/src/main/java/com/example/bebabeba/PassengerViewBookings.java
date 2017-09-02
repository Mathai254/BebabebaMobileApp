package com.example.bebabeba;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class PassengerViewBookings extends ListActivity {

    String json_url = "http://192.168.201.1/Bebabeba/android/retrieveBookings.php";
    public String[] BOOKINGS, DRIVERSEMAILS, BOOKINGSDATE, BOOKINGSTIME, BOOKINGSID, BOOKINGSPRICE;
    public String passengerEmail, passengerName, passengerPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        passengerEmail = intent.getStringExtra("passengerEmail");
        passengerName = intent.getStringExtra("name");
        passengerPhoneNo = intent.getStringExtra("phone_no");

        final TextView tvBookings = (TextView)findViewById(R.id.tvBookings);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("passengerEmail", passengerEmail);
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
                        BOOKINGSID = new String[response.length()];
                        DRIVERSEMAILS  = new String[response.length()];
                        BOOKINGSDATE = new String[response.length()];
                        BOOKINGSTIME = new String[response.length()];
                        BOOKINGSPRICE = new String[response.length()];

                        try
                        {
                            JSONObject jsonObject1 = null;
                            jsonObject1 = response.getJSONObject(0);

                            if(jsonObject1.getString("drivers_email").equals("null"))
                            {
                                setContentView(R.layout.activity_passenger_view_bookings);
                                tvBookings.setText("No bookings found");
                            }
                            else
                            {
                                while (count < response.length()) {
                                    JSONObject jsonObject2 = response.getJSONObject(count);
                                    StringBuffer buffer = new StringBuffer();
                                    buffer.append(jsonObject2.getString("source") + " " + " to ");
                                    buffer.append(jsonObject2.getString("destination")+" \n");

                                    String booking_date = jsonObject2.getString("booking_date");
                                    String[] strings1 = booking_date.split(":");
                                    booking_date = strings1[0]+"/"+strings1[1]+"/"+strings1[2];
                                    buffer.append(booking_date +" ");

                                    String booking_time = jsonObject2.getString("booking_time");
                                    String[] strings = booking_time.split(":");
                                    int i = Integer.parseInt(strings[1]);
                                    if(i<10)
                                    {
                                        booking_time = strings[0]+"0"+strings[1];
                                    }
                                    buffer.append(booking_time + "hrs");

                                    BOOKINGS[count] = buffer.toString();
                                    BOOKINGSID[count] = jsonObject2.getString("booking_id");
                                    DRIVERSEMAILS[count] = jsonObject2.getString("drivers_email");
                                    BOOKINGSDATE[count] = jsonObject2.getString("booking_date");
                                    BOOKINGSTIME[count] = jsonObject2.getString("booking_time");
                                    BOOKINGSPRICE[count] = jsonObject2.getString("price");
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
                Toast.makeText(PassengerViewBookings.this, error.toString(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getmInstance(PassengerViewBookings.this).addToRequestque(jsonArrayRequest);



    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(PassengerViewBookings.this, PassengerDisplayBooking.class);
        intent.putExtra("dEmail", DRIVERSEMAILS[position]);
        intent.putExtra("booking_id", BOOKINGSID[position]);
        intent.putExtra("booking_date", BOOKINGSDATE[position]);
        intent.putExtra("booking_time", BOOKINGSTIME[position]);
        intent.putExtra("payableAmount", BOOKINGSPRICE[position]);
        intent.putExtra("passengerEmail", passengerEmail);
        intent.putExtra("passengerName", passengerName);
        intent.putExtra("passengerPhoneNo", passengerPhoneNo);

        PassengerViewBookings.this.startActivity(intent);
    }

    public void createList()
    {
        setListAdapter(new ArrayAdapter< String >(this, android.R.layout.simple_list_item_1, BOOKINGS));
        getListView().setTextFilterEnabled(true);

    }
}
