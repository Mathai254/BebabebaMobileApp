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

public class PassengerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        final EditText etEmailAddress = (EditText) findViewById(R.id.tvLocality);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.etPassword2);
        final Button bRegister = (Button) findViewById(R.id.bUpdate);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String phone_no = etPhoneNumber.getText().toString();
                final String email = etEmailAddress.getText().toString();
                final String password = etPassword.getText().toString();
                final String password2 = etPassword2.getText().toString();
                final String status = "Waiting";



                if(password.equals(password2)){
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PassengerRegisterActivity.this);
                                    builder.setMessage(error)
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                                else
                                {
                                    if(validated)
                                    {
                                        if(success)
                                        {
                                            //Toast.makeText(getApplicationContext(), "Kindly wait for approval by the Adminstrator!!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(PassengerRegisterActivity.this, PassengerWelcomeActivity.class);
                                            intent.putExtra("name", name);
                                            intent.putExtra("phone_no", phone_no);
                                            intent.putExtra("email", email);

                                            PassengerRegisterActivity.this.startActivity(intent);
                                        }else
                                        {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(PassengerRegisterActivity.this);
                                            builder.setMessage("Register Failed")
                                                    .setNegativeButton("Retry", null)
                                                    .create()
                                                    .show();
                                        }
                                    }
                                    else
                                    {
                                        String error = jsonResponse.getString("error");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(PassengerRegisterActivity.this);
                                        builder.setMessage(error)
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                }

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    PassengerRegisterRequest registerRequest = new PassengerRegisterRequest(name, phone_no, email, password, status, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(PassengerRegisterActivity.this);
                    queue.add(registerRequest);

                }else{
                    //if passwords dont match
                    AlertDialog.Builder builder = new AlertDialog.Builder(PassengerRegisterActivity.this);
                    builder.setMessage("Passwords donot match")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            }
        });
    }

}
