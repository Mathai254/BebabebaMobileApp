package com.example.bebabeba;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PassengerMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    public DatabaseHelper myDb;
    public Marker pmarker, dmarker;
    public BackgroundTask backgroundTask;

    ArrayList<Contact> arrayList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        arrayList = new ArrayList<>();
        myDb = new DatabaseHelper(this);

        Intent intent = getIntent();
        final String v_type = intent.getStringExtra("v_type");
        final String email = intent.getStringExtra("email");
        final String locality = intent.getStringExtra("locality");

        Button bProceed;

        if (googleServicesAvailable()) {

            setContentView(R.layout.activity_passenger_map);

            myDb.clearSqlLite();

            backgroundTask = new BackgroundTask(PassengerMapActivity.this);


            try {
                backgroundTask.getList(v_type, locality);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            bProceed = (Button) findViewById(R.id.bProceed);

            bProceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(PassengerMapActivity.this, ListViewActivity.class);
                    intent.putExtra("v_type", v_type);
                    intent.putExtra("email", email);
                    //intent.putExtra("locality", locality);
                    PassengerMapActivity.this.startActivity(intent);
                }
            });
            initMap();
        } else {
            //no google maps layout
            Toast.makeText(this, "no google maps layout!!", Toast.LENGTH_LONG).show();
        }
    }



    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.passenger_map);
        mapFragment.getMapAsync(this);

    }


    private void setMarker(double lat, double lng) {

        if(pmarker!=null){
            pmarker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .title("Current location")
                .position(new LatLng(lat, lng));
                //.snippet(v_type);
        pmarker = mGoogleMap.addMarker(options);

    }

    private void setDriverMarker(double lat, double lng, String driver_id) {

        MarkerOptions options = new MarkerOptions()
                .title("Driver ID: "+driver_id)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(new LatLng(lat, lng));
        //.snippet(v_type);
        dmarker = mGoogleMap.addMarker(options);

    }


    /*private void setDriverMarker()
    {
        MarkerOptions options = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(new LatLng(lat, lng));
    }*/

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

            //set drivers markers
            //receive data from sqlite
/*            Cursor res = myDb.getAllData();
            if(res.getCount()==0){
                //show message
                showMessage("Error", "Nothing found");
            }
            else{
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    //add marker to map
                    String driver_id = res.getString(1);
                    String dlatitude = res.getString(2);
                    String dlongitude = res.getString(3);

                    if(dlatitude == "") dlatitude = "0";
                    if(dlongitude == "") dlongitude = "0";

                    double driverlat, driverlng;

                    try {
                        driverlat = new Double(dlatitude);
                        driverlng = new Double(dlongitude);
                    } catch (NumberFormatException e) {
                        driverlat = 0;
                        driverlng = 0;
                    }

                    setDriverMarker(driverlat, driverlng, driver_id);
                }
            }*/

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
            PassengerLocationSubmit locationSubmit = new PassengerLocationSubmit(latitude, longitude, email, responseListener);
            RequestQueue queue = Volley.newRequestQueue(PassengerMapActivity.this);
            queue.add(locationSubmit);

            //Access db and retrieve driver details
            final String v_type = intent.getStringExtra("v_type");

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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        if(mGoogleMap != null){


/*            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
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
            });*/
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

    public void showMessage (String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
