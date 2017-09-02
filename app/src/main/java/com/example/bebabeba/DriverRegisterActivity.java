package com.example.bebabeba;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String vehicle_selected;
    public int v_position;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehicle_selected = parent.getItemAtPosition(position).toString();
        v_position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etLicensePlate = (EditText) findViewById(R.id.etLicensePlate);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        final EditText etEmailAddress = (EditText) findViewById(R.id.tvLocality);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.etPassword2);
        final Button bRegister = (Button) findViewById(R.id.bUpdate);

        if(etName.equals("")){
            bRegister.setEnabled(false);
        }
        if(etLicensePlate.equals("")){
            bRegister.setEnabled(false);
        }
        if(etPhoneNumber.equals("")){
            bRegister.setEnabled(false);
        }
        if(etEmailAddress.equals("")){
            bRegister.setEnabled(false);
        }
        if(etPassword.equals("")){
            bRegister.setEnabled(false);
        }
        if(etPassword2.equals("")){
            bRegister.setEnabled(false);
        }


        final Spinner spinner = (Spinner) findViewById(R.id.sVehicleType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.vehicle_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);



        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etName.getText().toString();
                final String v_type = vehicle_selected;
                final String l_plate = etLicensePlate.getText().toString();
                final String phone_no = etPhoneNumber.getText().toString();
                final String email = etEmailAddress.getText().toString();
                final String password = etPassword.getText().toString();
                final String password2 = etPassword2.getText().toString();
                final String status = "Waiting";

                Toast.makeText(getApplicationContext(), v_type, Toast.LENGTH_LONG).show();


                if(password2.equals(password))
                {
                    //if passwords match
                    Response.Listener<String> responseListener = new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean matchFound = jsonResponse.getBoolean("matchFound");
                                boolean success = jsonResponse.getBoolean("success");
                                boolean validated = jsonResponse.getBoolean("validated");

                                if(matchFound)
                                {
                                    String error = jsonResponse.getString("error");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DriverRegisterActivity.this);
                                    builder.setMessage(error)
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                                else
                                {
                                    if(validated){
                                        if(success){
                                            //Toast.makeText(getApplicationContext(), "Kindly wait for approval by the Adminstrator!!", Toast.LENGTH_LONG).show();
                                            Toast.makeText(getApplicationContext(), "Details submitted successfully", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(DriverRegisterActivity.this, DriverUploadImage.class);
                                            intent.putExtra("email", email);
                                            intent.putExtra("name", name);
                                            intent.putExtra("v_type", v_type);
                                            intent.putExtra("l_plate", l_plate);
                                            intent.putExtra("phone_no", phone_no);
                                            DriverRegisterActivity.this.startActivity(intent);
                                        }else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(DriverRegisterActivity.this);
                                            builder.setMessage("Register Failed")
                                                    .setNegativeButton("Retry", null)
                                                    .create()
                                                    .show();
                                        }
                                    }else{
                                        String error = jsonResponse.getString("error");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(DriverRegisterActivity.this);
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

/*                    DriverEmailCheckRequest driverEmailCheckRequest = new DriverEmailCheckRequest(email, responseListener);
                    RequestQueue emailQueue = Volley.newRequestQueue(DriverRegisterActivity.this);
                    emailQueue.add(driverEmailCheckRequest);*/

                    DriverRegisterRequest registerRequest = new DriverRegisterRequest(name, v_type, l_plate, phone_no, email, password, status, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(DriverRegisterActivity.this);
                    queue.add(registerRequest);

                }
                else
                {
                    //if passwords dont match
                    AlertDialog.Builder builder = new AlertDialog.Builder(DriverRegisterActivity.this);
                    builder.setMessage("Passwords donot match")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }

            }
        });

    }
}


