package com.example.bebabeba;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class DriverMapActivity extends  FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    public Button bBookings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =getIntent();
        final String driverEmail = intent.getStringExtra("email");

        if (googleServicesAvailable()) {
            //Toast.makeText(this, "Perfect!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_driver_map);
            bBookings = (Button) findViewById(R.id.bBookings);

            //bookings button listener
            bBookings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //move to bookings activity
                    Intent bookingsIntent = new Intent(DriverMapActivity.this, BookingsActivity.class);
                    bookingsIntent.putExtra("driverEmail", driverEmail);
                    DriverMapActivity.this.startActivity(bookingsIntent);
                }
            });

            //Initialize map
            initMap();

        } else {
            //no google maps layout
            Toast.makeText(this, "no google maps layout!!", Toast.LENGTH_LONG).show();
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.driver_map);
        mapFragment.getMapAsync(this);
    }



    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100000);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String phone_no = intent.getStringExtra("phone_no");
        final String email = intent.getStringExtra("email");

        if(location == null){
            Toast.makeText(this, "Cannot get current location", Toast.LENGTH_LONG).show();
        }else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            mGoogleMap.animateCamera(update);

            double lat = ll.latitude;
            double lng = ll.longitude;
            setMarker(lat, lng);

            String latitude = Double.toString(lat);
            String longitude = Double.toString(lng);

            Response.Listener<String> responseListener = new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){
                            //Toast.makeText(getApplicationContext(), "Location submitted to db", Toast.LENGTH_LONG).show();
                        }else {
                            //Toast.makeText(getApplicationContext(), "Location submission failed", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            DriverLocationSubmit locationSubmit = new DriverLocationSubmit(latitude, longitude, email, responseListener);
            RequestQueue queue = Volley.newRequestQueue(DriverMapActivity.this);
            queue.add(locationSubmit);
        }
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    Marker marker;

    private void setMarker(double lat, double lng) {

        if(marker!=null){
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .title("Current location")
                .position(new LatLng(lat, lng))
                .snippet("Name");
        marker = mGoogleMap.addMarker(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;


        if(mGoogleMap != null){

            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    TextView tvInfo = (TextView) v.findViewById(R.id.tv_info);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);


                    LatLng ll = marker.getPosition();
                    tvInfo.setText(marker.getTitle());
                    tvLat.setText("Latitude: "+ ll.latitude);
                    tvLng.setText("Longitude: "+ ll.longitude);
                    tvSnippet.setText(marker.getSnippet());
                    return v;
                }
            });
        }
/*        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);*/
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }
}
