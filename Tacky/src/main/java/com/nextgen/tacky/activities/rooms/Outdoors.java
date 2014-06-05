package com.nextgen.tacky.activities.rooms;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;
import com.nextgen.tacky.basic.TackyLocation;
import com.nextgen.tacky.db.localDB.Location_DB;
import com.nextgen.tacky.sensor.gps.OutdoorsCommand;
import com.nextgen.tacky.sensor.gps.OutdoorsLocationListener;

/**
 * Created by maes on 2/12/13.
 */
public class Outdoors extends MainRoom {

    protected OutdoorsLocationListener outdoorsLocationListener;
    protected OutdoorsCommand outdoorsCommand;
    protected static TackyLocation CURRENT_LOCATION = null; // prevent repeatedly asking user about the same location
    private boolean dialogOpen = false;
    final Location_DB location_db = new Location_DB(this);

    public enum OutdoorsType {
        NORMAL,
        PARK,
        RESTAURANT
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        outdoorsCommand = new OutdoorsCommand() {
            @Override
            public void onLocationChanged(Location location) {
                TackyLocation query = location_db.getNearbyLocation(location);
                if (query != null){
                    if (CURRENT_LOCATION == null || (!query.isSameLocation(CURRENT_LOCATION) && !dialogOpen)) {
                        askUser(query);
                    }
                }
            }
        };

        outdoorsLocationListener = new OutdoorsLocationListener(outdoorsCommand);
    }

    @Override
    protected void onResume() {
        super.onResume();
        outdoorsLocationListener.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        outdoorsLocationListener.onPause();
    }

    private void askUser(final TackyLocation tloc) {
        dialogOpen = true;
        CURRENT_LOCATION = tloc;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

}
