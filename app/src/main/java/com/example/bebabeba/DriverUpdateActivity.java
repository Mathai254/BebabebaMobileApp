package com.example.bebabeba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    public String vehicle_selected, tempV_type, price, locality, name, v_type, l_plate, phone_no;
    public EditText etName, etEmailAddress, etLicensePlate, etPhoneNumber, etPassword, etPassword2;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehicle_selected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_update);

        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        locality = intent.getStringExtra("locality");
        name = intent.getStringExtra("name");
        final String previousEmail = intent.getStringExtra("email");
        v_type = intent.getStringExtra("v_type");
        l_plate = intent.getStringExtra("l_plate");
        phone_no = intent.getStringExtra("phone_no");

        etName = (EditText) findViewById(R.id.etName);
        etLicensePlate = (EditText) findViewById(R.id.etLicensePlate);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        final Button bUpdate = (Button) findViewById(R.id.bUpdate);

        loadSavedPreferences();

        if(etName.equals("")){
            bUpdate.setEnabled(false);
        }
        if(etLicensePlate.equals("")){
            bUpdate.setEnabled(false);
        }
        if(etPhoneNumber.equals("")){
            bUpdate.setEnabled(false);
        }

        if(etPassword.equals("")){
            bUpdate.setEnabled(false);
        }
        if(etPassword2.equals("")){
            bUpdate.setEnabled(false);
        }


        final Spinner spinner = (Spinner) findViewById(R.id.sVehicleType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.vehicle_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int count = 0;
        String selectedItem = spinner.getItemAtPosition(count).toString();

/*        while (tempV_type != selectedItem ){
            count++;
            selectedItem = spinner.getItemAtPosition(count).toString();
        }*/

        spinner.setSelection(count);

        spinner.setOnItemSelectedListener(this);


        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etName.getText().toString();
                final String v_type = vehicle_selected;
                final String l_plate = etLicensePlate.getText().toString();
                final String phone_no = etPhoneNumber.getText().toString();
                final String currentEmail = etEmailAddress.getText().toString();
                final String password = etPassword.getText().toString();
                final String password2 = etPassword2.getText().toString();
                final String status = "Waiting";


                if(password2.equals(password))
                {
                    //if passwords match
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean matchFound = jsonResponse.getBoolean("matchFound");
                                boolean success = jsonResponse.getBoolean("success");
                                boolean validated = jsonResponse.getBoolean("validated");

                                if (matchFound) {
                                    String error = jsonResponse.getString("error");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DriverUpdateActivity.this);
                                    builder.setMessage(error)
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                } else {
                                    if (validated) {
                                        if (success) {
                                            //Toast.makeText(getApplicationContext(), "Kindly wait for approval by the Adminstrator!!", Toast.LENGTH_LONG).show();
                                            Toast.makeText(getApplicationContext(), "Details submitted successfully", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(DriverUpdateActivity.this, DriverUploadImage.class);
                                            intent.putExtra("email", currentEmail);
                                            intent.putExtra("name", name);
                                            intent.putExtra("v_type", v_type);
                                            intent.putExtra("l_plate", l_plate);
                                            intent.putExtra("phone_no", phone_no);
                                            DriverUpdateActivity.this.startActivity(intent);
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(DriverUpdateActivity.this);
                                            builder.setMessage("Register Failed")
                                                    .setNegativeButton("Retry", null)
                                                    .create()
                                                    .show();
                                        }
                                    } else {
                                        String error = jsonResponse.getString("error");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DriverUpdateActivity.this);
                                        builder.setMessage(error)
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    DriverUpdateRequest updateRequest = new DriverUpdateRequest(name, v_type, l_plate, phone_no, previousEmail, currentEmail, password, status, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(DriverUpdateActivity.this);
                    queue.add(updateRequest);
                }
                else
                {
                    //if passwords don't match
                    AlertDialog.Builder builder = new AlertDialog.Builder(DriverUpdateActivity.this);
                    builder.setMessage("Passwords donot match")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

            }
        });
    }

    //load preferences
    private void loadSavedPreferences() {
        SharedPreferences userDetails = this.getSharedPreferences("driverAllInfo", Context.MODE_PRIVATE);
        String tempName = userDetails.getString("name", "");  // (key, default)
        String tempEmail = userDetails.getString("email", "");
        tempV_type = userDetails.getString("v_type", "");
        String tempL_plate = userDetails.getString("l_plate", "");
        String tempPhoneNumber = userDetails.getString("phone_no","");
        String tempPassword = userDetails.getString("password","");

        etName.setText(tempName);
        etEmailAddress.setText(tempEmail);
        etLicensePlate.setText(tempL_plate);
        etPhoneNumber.setText(tempPhoneNumber);
        etPassword.setText(tempPassword);
        etPassword2.setText(tempPassword);
    }

}
