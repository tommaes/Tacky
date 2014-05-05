package com.nextgen.tackyTests.unitTests.basic.tacky;

import android.os.Parcel;

import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;

import junit.framework.TestCase;

/**
 * Created by maes on 3/05/14.
 */
public class TackyTest extends TestCase {

    private final String name = "testTacky";
    private final int headId = 1;
    private final int bodyId = 2;
    private final int expressionId = 3;

    private final String roomName = "testRoom";
    private final String background = "background";
    private final Room.RoomType roomType = Room.RoomType.MAIN;

    public void testParcel() throws Exception {
        Parcel parcel = Parcel.obtain();
        Room room = new Room(roomName, background, roomType);
        Tacky tacky = new Tacky(name, room, headId, bodyId, expressionId);
        tacky.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Tacky parcelTacky = Tacky.CREATOR.createFromParcel(parcel);

        assertEquals(name, parcelTacky.getName());
        assertEquals(headId, parcelTacky.getHeadId());
        assertEquals(bodyId, parcelTacky.getBodyId());
        assertEquals(expressionId, parcelTacky.getExpressionId());
        assertEquals(roomName, parcelTacky.getRoomName());
        assertEquals(roomType, parcelTacky.getRoomType());
    }

}
