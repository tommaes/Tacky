package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.LocalDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maes on 21/05/14.
 */
public class Room_DB {

    private static LocalDatabase db = null;

    public static final String ROOM_TABLE = "rooms";

    public static final String ROOM_NAME = "name";
    public static final String ROOM_OWNER = "owner";
    public static final String ROOM_VISUAL = "visualization";
    public static final String ROOM_TYPE = "type";

    private static final String ROOM_SQL_CREATE = "CREATE TABLE " + ROOM_TABLE + " (" +
            ROOM_NAME + " TEXT," +
            ROOM_OWNER + " TEXT," +
            ROOM_VISUAL + " TEXT," +
            ROOM_TYPE + " INTEGER," +
            "PRIMARY KEY(" + ROOM_NAME + "," + ROOM_OWNER + ")," +
            "FOREIGN KEY(" + ROOM_OWNER + ") REFERENCES " + Tacky_DB.TACKY_TABLE + "(" + Tacky_DB.TACKY_NAME + "))";

    private static final String ROOM_SQL_SELECT_ALL = "SELECT * " +
                                                      "FROM " + ROOM_TABLE +
                                                      " WHERE " + ROOM_OWNER + " = '%s'";

    private static final String ROOM_SQL_SELECT_ROOM = "SELECT * FROM " + ROOM_TABLE +
                                                       " WHERE " + ROOM_NAME + " = '%s'" + " AND " +
                                                       ROOM_OWNER + " = '%s'";

    public Room_DB(Context ctx){
        if(db == null)
            db = LocalDatabase.getDatabase(ctx);
    }

    public void deleteRooms(String owner){
        String[] args = new String[]{owner};
        db.deleteValue(ROOM_TABLE, ROOM_OWNER + "=?", args);
    }

    public static void initializeTable(SQLiteDatabase db) {
        db.execSQL(ROOM_SQL_CREATE);
    }

    public void initializeTackyRooms(Tacky tacky){
        Room kitchen = new Room("Kitchen", "kitchen_background", Room.RoomType.KITCHEN);
        Room bedroom = new Room("Bedroom", "bedroom_background2", Room.RoomType.BEDROOM);
        Room bathroom = new Room("Bathroom", "bathroom_background1", Room.RoomType.BATHROOM);
        Room outdoors = new Room("Outdoors", "background4", Room.RoomType.OUTDOORS);

        storeRoom(tacky);
        storeRoom(kitchen, tacky);
        storeRoom(bedroom, tacky);
        storeRoom(bathroom, tacky);
        storeRoom(outdoors, tacky);
    }

    public Room getRoom(String name, String owner) {
        String query = String.format(ROOM_SQL_SELECT_ROOM, name, owner);
        return db.readValue(query, new ReadCommand<Room>() {
            @Override
            public Room readItem(Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndex(ROOM_NAME));
                String vis = cursor.getString(cursor.getColumnIndex(ROOM_VISUAL));
                int type = cursor.getInt(cursor.getColumnIndex(ROOM_TYPE));
                return new Room(name, vis, type);
            }
        });
    }

    public ArrayList<Room> getRooms(Tacky owner){
        String query = String.format(ROOM_SQL_SELECT_ALL, owner.getName());
        return db.readValues(query, new ReadCommand<Room>() {
            @Override
            public Room readItem(Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndex(ROOM_NAME));
                String vis = cursor.getString(cursor.getColumnIndex(ROOM_VISUAL));
                int type = cursor.getInt(cursor.getColumnIndex(ROOM_TYPE));
                return new Room(name, vis, type);
            }
        });
    }

    public ArrayList<String> getBackgrounds(){
        ArrayList<String> backgrounds = new ArrayList<String>();
        backgrounds.add("green_yellow_blue_tile");
        backgrounds.add("soccer");
        backgrounds.add("background1");
        backgrounds.add("background2");
        backgrounds.add("background3");
        backgrounds.add("background4");
        backgrounds.add("kitchen_background");
        backgrounds.add("bathroom_background1");
        backgrounds.add("bathroom_background2");
        backgrounds.add("bedroom_background1");
        backgrounds.add("bedroom_background2");
        return backgrounds;
    }

    public void storeRoom(final Tacky owner){
        db.replaceValue(ROOM_TABLE, null, new StoreCommand<Object>() {
            @Override
            public ContentValues storeItem(Object item) {
                ContentValues cv = new ContentValues();
                cv.put(ROOM_NAME, owner.getRoomName());
                cv.put(ROOM_OWNER, owner.getName());
                cv.put(ROOM_VISUAL, owner.getRoomVisualization());
                cv.put(ROOM_TYPE, owner.getRoomType().ordinal());

                return cv;
            }
        });
    }

    public void storeRoom(final Room r, final Tacky owner) {
        db.replaceValue(ROOM_TABLE, null, new StoreCommand<Object>() {
            @Override
            public ContentValues storeItem(Object item) {
                ContentValues cv = new ContentValues();
                cv.put(ROOM_NAME, r.getName());
                cv.put(ROOM_OWNER, owner.getName());
                cv.put(ROOM_VISUAL, r.getVisualization());
                cv.put(ROOM_TYPE, r.getRoomType().ordinal());

                return cv;
            }
        });
    }

}
