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

    /*
    private static final String LOCATION_ID = "id";
    private static final String LOCATION_NAME = "name";
    private static final String LOCATION_LAT = "lat";
    private static final String LOCATION_LONG = "long";
    private static final String LOCATION_RADIUS = "radius";
    private static final String LOCATION_TYPE = "type";
    */

    /*private static final String LOCATION_SQL_CREATE = "CREATE TABLE " + LOCATION_TABLE + " (" +
            LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LOCATION_NAME + " TEXT," +
            LOCATION_LAT + " FLOAT(9,6)," +
            LOCATION_LONG + " FLOAT(9,6)," +
            LOCATION_RADIUS + " INTEGER," +
            LOCATION_TYPE + " INTEGER)";*/

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
