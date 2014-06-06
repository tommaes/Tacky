package com.nextgen.tackyTests.unitTests.display;

import android.os.Parcel;

import com.nextgen.tacky.display.TackyHead;

import junit.framework.TestCase;

/**
 * Created by maes on 6/06/14.
 */
public class TackyHeadTest extends TestCase {

    private String normal = "normal";
    private String sleep = "sleep";
    private String up = "up";
    private String down = "down";
    private int id = 10;

    public void testConstructor() throws Exception {

        TackyHead tackyHead = new TackyHead(normal, sleep, up, down, id);

        // test ID getter
        assertEquals(id, tackyHead.getDatabaseId());

    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        TackyHead tackyHead = new TackyHead(normal, sleep, up, down, id);
        tackyHead.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        TackyHead parcelTackyHead = TackyHead.CREATOR.createFromParcel(parcel);

        assertEquals(id, parcelTackyHead.getDatabaseId());
    }
}
