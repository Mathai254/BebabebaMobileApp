package com.example.bebabeba;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SetCharges extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_charges);

        final EditText etPrice = (EditText) findViewById(R.id.etPrice);
        final EditText etLocality = (EditText) findViewById(R.id.etLocality);
        final Button bSubmitPrice = (Button)findViewById(R.id.bSubmitPrice);
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String name = intent.getStringExtra("name");
        final String v_type = intent.getStringExtra("v_type");
        final String l_plate = intent.getStringExtra("l_plate");
        final String phone_no = intent.getStringExtra("phone_no");

        String price = intent.getStringExtra("price");
        String locality = intent.getStringExtra("locality");

        etPrice.setText(price);
        etLocality.setText(locality);


        bSubmitPrice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final String Price = etPrice.getText().toString();
                final String Locality = etLocality.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            boolean validated = jsonResponse.getBoolean("validated");

                            if(validated){
                                if(success){
                                    Toast.makeText(getApplicationContext(), "Info saved successfully!!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SetCharges.this, DriverUserAreaActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    //intent.putExtra("password", password);
                                    intent.putExtra("v_type", v_type);
                                    intent.putExtra("l_plate", l_plate);
                                    intent.putExtra("phone_no", phone_no);
                                    intent.putExtra("Price", Price);
                                    intent.putExtra("Locality", Locality);

                                    SetCharges.this.startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_LONG).show();
                                }

                            }else{
                                String error = jsonResponse.getString("error");
                                AlertDialog.Builder builder = new AlertDialog.Builder(SetCharges.this);
                                builder.setMessage(error)
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                DriverSetPriceRequest driverSetPriceRequest = new DriverSetPriceRequest(Price, Locality, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SetCharges.this);
                queue.add(driverSetPriceRequest);
            }
        });

    }
}
