package com.nextgen.tacky.db;

import android.content.Context;
import android.location.Location;

import com.nextgen.tacky.basic.TackyLocation;
import com.nextgen.tacky.db.localDB.LocalLocation_DB;

/**
 * Created by maes on 14/06/14.
 */
public class Location_DB {

    private LocalLocation_DB localLocation_db;

    public Location_DB(Context context){
        localLocation_db = new LocalLocation_DB(context);
    }

    public TackyLocation getNearbyLocation(Location currentLocation) {
        return localLocation_db.getNearbyLocation(currentLocation);
    }
}
