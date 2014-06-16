package com.nextgen.tacky.sensor.gps;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by maes on 31/05/14.
 */
public class OutdoorsLocationListener extends Service implements LocationListener {

    protected boolean isGPSEnabled;

    protected LocationManager locationManager;
    protected OutdoorsCommand outdoorsCommand;

    // In seconds.
    protected static long MINIMUM_TIME_FOR_MEASURING = 1000 * 30;

    // In meters.
    protected static long MINIMUM_DISTANCE_FOR_MEASURING = 5;

    public OutdoorsLocationListener(OutdoorsCommand outdoorsCommand, Activity activity) {
        this.outdoorsCommand = outdoorsCommand;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        initializeGPS();
    }

    protected void initializeGPS(){
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGPSEnabled) return; // GPS device is disabled by user

        // Register for one update every 5 seconds
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                               MINIMUM_TIME_FOR_MEASURING,
                                               MINIMUM_DISTANCE_FOR_MEASURING,
                                               this);
    }

    public void onResume() {
        initializeGPS();
    }

    public void onPause() {
        if(isGPSEnabled) locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        outdoorsCommand.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
