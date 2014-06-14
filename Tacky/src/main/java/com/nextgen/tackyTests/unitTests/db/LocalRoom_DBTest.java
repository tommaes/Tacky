package com.nextgen.tackyTests.unitTests.db;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.LocalRoom_DB;

import java.util.ArrayList;

/**
 * Created by maes on 6/06/14.
 */
public class LocalRoom_DBTest extends AndroidTestCase {

    private LocalRoom_DB localRoom_db;

    // Room info
    private String name = "name";
    private String visual = "visual";
    Room.RoomType type = Room.RoomType.MAIN;

    // Tacky info
    private final String tackyName = "testTacky";
    private final int headId = 1;
    private final int bodyId = 2;
    private final int expressionId = 3;

    public void setUp(){
        RenamingDelegatingContext renamingDelegatingContext = new RenamingDelegatingContext(getContext(), "test_DB_");
        localRoom_db = new LocalRoom_DB(renamingDelegatingContext);
    }

    public void testAddRoomAndGetRoom1() throws Exception {
        Room room = new Room(name, visual, type);
        Tacky tacky = new Tacky(tackyName, room, headId, bodyId, expressionId);

        localRoom_db.storeRoom(room, tacky);

        Room readRoom = localRoom_db.getRoom(name, tackyName);

        assertEquals(room.getName(), readRoom.getName());
        assertEquals(room.getVisualization(), readRoom.getVisualization());
        assertEquals(room.getRoomType(), readRoom.getRoomType());

    }

    public void testAddRoomAndGetRoom2() throws Exception {
        Room room = new Room(name, visual, type);
        Tacky tacky = new Tacky(tackyName, room, headId, bodyId, expressionId);

        localRoom_db.storeRoom(tacky);

        Room readRoom = localRoom_db.getRoom(name, tackyName);

        assertEquals(room.getName(), readRoom.getName());
        assertEquals(room.getVisualization(), readRoom.getVisualization());
        assertEquals(room.getRoomType(), readRoom.getRoomType());

    }

    public void testAddRoomAndGetRooms() throws Exception {
        Room room = new Room(name, visual, type);
        Tacky tacky = new Tacky(tackyName, room, headId, bodyId, expressionId);

        localRoom_db.storeRoom(room, tacky);

        ArrayList<Room> rooms = localRoom_db.getRooms(tacky);

        boolean contains = false;

        for(Room readRoom : rooms){
            if(room.getName().equals(readRoom.getName()) &&
               room.getVisualization().equals(readRoom.getVisualization()) &&
               room.getRoomType() == readRoom.getRoomType()){
                contains = true;
                break;
            }

        }

        assertTrue(contains);

    }


}
