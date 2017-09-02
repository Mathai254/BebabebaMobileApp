package com.example.bebabeba;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DriverUploadImage extends AppCompatActivity implements View.OnClickListener{

    private Button bChooseImage, bUpload;
    private ImageView imageViewDriver;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private String UploadUrl = "http://192.168.201.1/Bebabeba/android/uploadPics.php";
    public String email, name, v_type, l_plate, phone_no, price, locality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_upload_image);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        v_type = intent.getStringExtra("v_type");
        l_plate = intent.getStringExtra("l_plate");
        phone_no = intent.getStringExtra("phone_no");
        price = intent.getStringExtra("price");
        locality = intent.getStringExtra("locality");


        bChooseImage = (Button) findViewById(R.id.bChooseImage);
        bUpload = (Button) findViewById(R.id.bUpload);

        imageViewDriver = (ImageView) findViewById(R.id.imageVDriver);

        bChooseImage.setOnClickListener(this);
        bUpload.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageViewDriver.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bChooseImage:

                selectImage();

                break;

            case R.id.bUpload:

                uploadImage();
                proceedToLogin();

                break;
        }
    }

    private void proceedToLogin()
    {
        Intent pIntent = new Intent(DriverUploadImage.this, SetCharges.class);
        pIntent.putExtra("email", email);
        pIntent.putExtra("name", name);
        pIntent.putExtra("v_type", v_type);
        pIntent.putExtra("l_plate", l_plate);
        pIntent.putExtra("phone_no", phone_no);
        pIntent.putExtra("price", price);
        pIntent.putExtra("locality", locality);

        DriverUploadImage.this.startActivity(pIntent);
    }

    private void selectImage()
    {
        Intent ImageIntent = new Intent();
        ImageIntent.setType("image/*");
        ImageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(ImageIntent, IMG_REQUEST);
    }

    private void uploadImage()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            Toast.makeText(DriverUploadImage.this, Response, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("image", imageToString(bitmap));

                return params;
            }
        };
        MySingleton.getmInstance(DriverUploadImage.this).addToRequestque(stringRequest);
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
