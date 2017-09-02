package com.example.bebabeba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverLoginActivity extends AppCompatActivity {

    public boolean checked;
    public EditText etEmailAddress;
    public EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        SharedPreferences sharedpref = getSharedPreferences("driverInfo", Context.MODE_PRIVATE);

        etEmailAddress = (EditText) findViewById(R.id.tvLocality);
        etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        if(sharedpref != null)
        {
            loadSavedPreferences();
        }

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(DriverLoginActivity.this,DriverRegisterActivity.class);
                DriverLoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmailAddress.getText().toString();
                final String password = etPassword.getText().toString();

                if(checked)
                {
                    saveInfo();
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                String name = jsonResponse.getString("name");
                                String v_type = jsonResponse.getString("v_type");
                                String l_plate = jsonResponse.getString("l_plate");
                                String phone_no = jsonResponse.getString("phone_no");
                                String email = jsonResponse.getString("email");
                                String password = jsonResponse.getString("password");
                                String status = jsonResponse.getString("status");

                                if(status.equals("approved")){
                                    Intent intent = new Intent(DriverLoginActivity.this, DriverUserAreaActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    intent.putExtra("v_type", v_type);
                                    intent.putExtra("l_plate", l_plate);
                                    intent.putExtra("phone_no", phone_no);

                                    DriverLoginActivity.this.startActivity(intent);
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DriverLoginActivity.this);
                                    builder.setMessage("Kindly wait for approval from the administrator")
                                            .setNegativeButton("OK", null)
                                            .create()
                                            .show();
                                }

                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(DriverLoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DriverLoginRequest driverLoginRequest = new DriverLoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DriverLoginActivity.this);
                queue.add(driverLoginRequest);
            }
        });

    }

    //checkbox code
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        checked = true;
    }

    public void saveInfo() {
        SharedPreferences sharedpref = getSharedPreferences("driverInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("email", etEmailAddress.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.apply();

        //Toast.makeText(this, "Password saved!", Toast.LENGTH_LONG).show();
    }
    private void loadSavedPreferences() {
        SharedPreferences userDetails = this.getSharedPreferences("driverInfo", Context.MODE_PRIVATE);
        String tempEmail = userDetails.getString("email", "");  // (key, default)
        String tempPassword = userDetails.getString("password","");
        etEmailAddress.setText(tempEmail);
        etPassword.setText(tempPassword);
    }
}
