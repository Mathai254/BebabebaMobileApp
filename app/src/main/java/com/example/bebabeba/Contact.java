package com.example.bebabeba;

/**
 * Created by Samuel Mathai on 4/23/2017.
 */

public class Contact {

    private String driver_id, latitude, longitude;

    public Contact(String driver_id, String latitude, String longitude)
    {
        this.setDriver_id(driver_id);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
