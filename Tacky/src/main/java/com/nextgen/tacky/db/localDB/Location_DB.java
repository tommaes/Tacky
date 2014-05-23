package com.nextgen.tacky.db.localDB;

import android.content.Context;
import android.location.Location;

import com.nextgen.tacky.activities.rooms.Outdoors;
import com.nextgen.tacky.db.LocalDatabase;
import com.nextgen.tacky.db.TackyLocation;

/**
 * Created by maes on 23/05/14.
 */
public class Location_DB {

    private static LocalDatabase db = null;

    public Location_DB(Context context){
        if(db == null)
            db = LocalDatabase.getDatabase(context);
    }

    private static TackyLocation[] dbLocations = {
            new TackyLocation("Atomium", "atomium", Outdoors.OutdoorsType.PARK, 50.894722, 4.341111, 100),
            new TackyLocation("Castle of Gaasbeek", "gaasbeek_park", Outdoors.OutdoorsType.PARK, 50.799968, 4.201902, 100)
    };


    public TackyLocation getNearbyLocation(Location currentLocation) {

        TackyLocation l = null;

        for (TackyLocation location : dbLocations) {
            if (location.isSameLocation(currentLocation)) {
                l = location; // return first location in range
                break;
            }
        }

        return l; // no suitable location found
    }

}
