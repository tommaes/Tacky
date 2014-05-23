package com.nextgen.tacky.activities.rooms;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;
import com.nextgen.tacky.db.TackyLocation;
import com.nextgen.tacky.db.localDB.Location_DB;

/**
 * Created by maes on 2/12/13.
 */
public class Outdoors extends MainRoom {

    protected TackyLocationListener gpsListener;
    protected LocationManager gpsService;
    protected static TackyLocation CURRENT_LOCATION = null; // prevent repeatedly asking user about the same location
    final Location_DB location_db = new Location_DB(this);

    public enum OutdoorsType {
        NORMAL,
        PARK,
        RESTAURANT
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gpsService = (LocationManager) getSystemService(LOCATION_SERVICE);
        gpsListener = new TackyLocationListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gpsService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, gpsListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsService.removeUpdates(gpsListener);
    }

    // --------------------- Outdoors switch --------------


    private Intent startOutdoorsActivity(Context context, TackyLocation tackyLocation){

        Intent intent = null;

        switch(tackyLocation.getOutdoorsType()){
            case PARK:{
                intent = new Intent(context, Park.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
            case RESTAURANT: {
                intent = new Intent(context, Restaurant.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
        }

        return intent;

    }


    // ------------- GPS ---------------------------


    private void initializeGPS() {

        boolean enabled = gpsService.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) return; // GPS device is disabled by user

        // Register for one update every 5 seconds
        gpsService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, gpsListener);
    }

    protected class TackyLocationListener implements LocationListener {
        private boolean dialogOpen = false; // prevent multiple dialogs from opening

        @Override
        public void onLocationChanged(Location location) {
            TackyLocation query = location_db.getNearbyLocation(location);
            if (query != null){
                if (CURRENT_LOCATION == null || (!query.isSameLocation(CURRENT_LOCATION) && !dialogOpen)) {
                    askUser(query);
                }
            }
        }

        private void askUser(final TackyLocation tloc) {
            dialogOpen = true;
            CURRENT_LOCATION = tloc;
            final AlertDialog.Builder builder = new AlertDialog.Builder(Outdoors.this);
            builder.setMessage("Would you like to switch to " + tloc.getRoomName() + "?");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialogOpen = false;
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialogOpen = false;
                    tloc.transferTackyTo(tacky);
                    Intent intent = startOutdoorsActivity(Outdoors.this, tloc);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
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
    }

}
