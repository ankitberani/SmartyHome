package com.smartyhome.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.smartyhome.app.main.home.MainInterfaces;

public class LocationUtils implements LocationListener {
    Context context;
    LocationManager locationManager;

    double latitude = 0;
    double longitude = 0;
     MainInterfaces _interface;
     public LocationUtils(Context splashActivity,MainInterfaces _interface) {
        this.context = splashActivity;
        this._interface = _interface;
    }

    @SuppressLint({"MissingPermission"})
    public String fn_getlocation() {
        this.locationManager = (LocationManager) this.context.getSystemService("location");
        boolean isGPSEnable = this.locationManager.isProviderEnabled("gps");
        boolean isNetworkEnable = this.locationManager.isProviderEnabled("network");
        if (isGPSEnable || isNetworkEnable) {
            if (isNetworkEnable) {
                this.locationManager.requestLocationUpdates("network", 1000, 0.0f, this);
                LocationManager locationManager2 = this.locationManager;
                if (locationManager2 != null) {
                    Location location = locationManager2.getLastKnownLocation("network");
                    if (location != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(location.getLatitude());
                        sb.append("");
                        Log.e("latitude", sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(location.getLongitude());
                        sb2.append("");
                        Log.e("longitude", sb2.toString());
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
            if (isGPSEnable) {
                this.locationManager.requestLocationUpdates("gps", 1000, 0.0f, this);
                LocationManager locationManager3 = this.locationManager;
                if (locationManager3 != null) {
                    Location location2 = locationManager3.getLastKnownLocation("gps");
                    if (location2 != null) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(location2.getLatitude());
                        sb3.append("");
                        Log.e("latitude", sb3.toString());
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(location2.getLongitude());
                        sb4.append("");
                        Log.e("longitude", sb4.toString());
                        latitude = location2.getLatitude();
                        longitude = location2.getLongitude();
                    }
                }
            }
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append(String.valueOf(longitude));
        sb5.append(',');
        sb5.append(String.valueOf(latitude));
        _interface.setupLatLong(latitude,longitude);
        return sb5.toString();
    }

    public void onLocationChanged(Location location) {
        try {
            double lat = location.getLatitude();
            double Long = location.getLongitude();
            latitude = lat;
            longitude = Long;
            this.locationManager.removeUpdates(this);
            this.locationManager = null;
        } catch (Exception e){

        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }


    public double getLat() {
        return latitude;
    }

    public double getLongi() {
        return longitude;
    }


}
