package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;

import com.nextgen.tacky.activities.rooms.Outdoors;
import com.nextgen.tacky.basic.TackyLocation;
import com.nextgen.tacky.db.generic.ReadCommand;
import com.nextgen.tacky.db.generic.StoreCommand;

import java.util.ArrayList;

/**
 * Created by maes on 23/05/14.
 */
public class Location_DB {

    private static LocalDatabase db = null;

    private static final String LOCATION_TABLE = "locations";

    private static final String LOCATION_ID = "id";
    private static final String LOCATION_NAME = "name";
    private static final String LOCATION_IMAGE = "image";
    private static final String LOCATION_LAT = "lat";
    private static final String LOCATION_LONG = "long";
    private static final String LOCATION_RADIUS = "radius";
    private static final String LOCATION_TYPE = "type";


    private static final String LOCATION_SQL_CREATE = "CREATE TABLE " + LOCATION_TABLE + " (" +
            LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LOCATION_NAME + " TEXT," +
            LOCATION_IMAGE + " TEXT," +
            LOCATION_LAT + " FLOAT(9,6)," +
            LOCATION_LONG + " FLOAT(9,6)," +
            LOCATION_RADIUS + " INTEGER," +
            LOCATION_TYPE + " INTEGER)";

    private static final String LOCATION_SQL_SELECT_ALL = "SELECT * " +
                                                          "FROM " + LOCATION_TABLE +
                                                          "WHERE " + LOCATION_RADIUS + " <= '%s'";

    private static final int RADIUS_SELECT = 500;

    public Location_DB(Context context){
        if(db == null)
            db = LocalDatabase.getDatabase(context);
    }

    public static void initializeTable(LocalDatabase db) {
        db.addTable(LOCATION_SQL_CREATE);

        // basic locations
        TackyLocation[] dbLocations = {
              new TackyLocation("Atomium", "atomium", Outdoors.OutdoorsType.PARK, 50.894722, 4.341111, 50),
              new TackyLocation("Castle of Gaasbeek", "gaasbeek_park", Outdoors.OutdoorsType.PARK, 50.799968, 4.201902, 100)
        };

        StoreCommand<TackyLocation, ContentValues> command = new StoreCommand<TackyLocation, ContentValues>() {
            @Override
            public ContentValues storeItem(TackyLocation item) {
                ContentValues cv = new ContentValues();
                cv.put(LOCATION_NAME, item.getRoomName());
                cv.put(LOCATION_IMAGE, item.getVisualization());
                cv.put(LOCATION_LAT, item.getLatitude());
                cv.put(LOCATION_LONG, item.getLongitude());
                cv.put(LOCATION_RADIUS, item.getRadius());
                cv.put(LOCATION_TYPE, item.getOutdoorsType().ordinal());

                return cv;
            }
        };

        for(TackyLocation tackyLocation : dbLocations){
            db.insertValue(LOCATION_TABLE, tackyLocation, command);
        }
    }

    public TackyLocation getNearbyLocation(Location currentLocation) {

        TackyLocation l = null;

        // select all locations with a radius of less than x meter
        String query = String.format(LOCATION_SQL_SELECT_ALL, Integer.toString(RADIUS_SELECT));
        ArrayList<TackyLocation> tackyLocations = db.readValues(query, new ReadCommand<TackyLocation, Cursor>() {
            @Override
            public TackyLocation readItem(Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndex(LOCATION_NAME));
                String image = cursor.getString(cursor.getColumnIndex(LOCATION_IMAGE));
                double longitude = cursor.getDouble(cursor.getColumnIndex(LOCATION_LONG));
                double latitude = cursor.getDouble(cursor.getColumnIndex(LOCATION_LAT));
                double radius = cursor.getDouble(cursor.getColumnIndex(LOCATION_RADIUS));
                int outdoorsType = cursor.getInt(cursor.getColumnIndex(LOCATION_TYPE));
                return new TackyLocation(name, image, outdoorsType, latitude, longitude, radius);
            }
        });

        for (TackyLocation location : tackyLocations) {
            if (location.isSameLocation(currentLocation)) {
                l = location; // return first location in range
                break;
            }
        }

        // check return value for null

        return l;
    }

    public static void dropTable(LocalDatabase db, String dropTable) {
        db.dropTable(dropTable + LOCATION_TABLE);
    }

}
