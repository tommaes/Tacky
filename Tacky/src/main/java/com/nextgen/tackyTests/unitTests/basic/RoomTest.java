package com.nextgen.tackyTests.unitTests.basic;

import android.os.Parcel;

import com.nextgen.tacky.basic.Room;

import junit.framework.TestCase;

/**
 * Created by maes on 3/05/14.
 */
public class RoomTest extends TestCase {

    private String name = "name";
    private String visual = "visual";
    Room.RoomType type = Room.RoomType.MAIN;

    public void testGetName() throws Exception {

        Room room = new Room(name, visual, type);
        assertEquals(name, room.getName());
    }

    public void testGetRoomType() throws Exception {

        Room room = new Room(name, visual, type);
        assertEquals(type, room.getRoomType());
    }

    public void testGetSetVisual() throws Exception {

        Room room = new Room(name, visual, type);

        assertEquals(visual, room.getVisualization());

        String newVisual = "newVisual";
        room.setVisualization(newVisual);
        assertEquals(newVisual, room.getVisualization());
    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        Room room = new Room(name, visual, type);
        room.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Room parcelRoom = Room.CREATOR.createFromParcel(parcel);

        assertEquals(name, parcelRoom.getName());
        assertEquals(visual, parcelRoom.getVisualization());
        assertEquals(type, parcelRoom.getRoomType());
    }
}
