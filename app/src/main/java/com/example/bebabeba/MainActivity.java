package com.example.bebabeba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button bDriver = (Button) findViewById(R.id.bDriver);
        final Button bPassenger = (Button) findViewById(R.id.bPassenger);

        bDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverIntent = new Intent(MainActivity.this, DriverLoginActivity.class);
                MainActivity.this.startActivity(driverIntent);
            }
        });

        bPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passengerIntent = new Intent(MainActivity.this, PassengerLoginActivity.class);
                MainActivity.this.startActivity(passengerIntent);
            }
        });

    }
}
