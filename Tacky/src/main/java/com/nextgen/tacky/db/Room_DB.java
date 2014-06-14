package com.nextgen.tacky.db;

import android.content.Context;

import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.LocalRoom_DB;

import java.util.ArrayList;

/**
 * Created by maes on 14/06/14.
 */
public class Room_DB {

    private LocalRoom_DB localRoom_db;

    public Room_DB(Context context){
        localRoom_db = new LocalRoom_DB(context);
    }

    public void deleteRooms(String owner) {
        localRoom_db.deleteRooms(owner);
    }

    public void initializeTackyRooms(Tacky tacky) {
        localRoom_db.initializeTackyRooms(tacky);
    }

    public Room getRoom(String name, String owner) {
        return localRoom_db.getRoom(name, owner);
    }

    public ArrayList<Room> getRooms(Tacky owner) {
        return localRoom_db.getRooms(owner);
    }

    public ArrayList<String> getBackgrounds() {
        return localRoom_db.getBackgrounds();
    }

    public void storeRoom(Tacky owner) {
        localRoom_db.storeRoom(owner);
    }

    public void storeRoom(Room r, Tacky owner) {
        localRoom_db.storeRoom(r, owner);
    }
}
