package com.nextgen.tackyTests.unitTests.display;

import android.os.Parcel;

import com.nextgen.tacky.display.TackyBody;

import junit.framework.TestCase;

/**
 * Created by maes on 5/06/14.
 */
public class TackyBodyTest extends TestCase{

    private String name = "name";
    private int id = 10;

    public void testConstructor() throws Exception {

        TackyBody tackyBody = new TackyBody(name, id);

        // test ID getter
        assertEquals(id, tackyBody.getDatabaseId());

    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        TackyBody tackyBody = new TackyBody(name, id);
        tackyBody.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        TackyBody parcelTackyBody = TackyBody.CREATOR.createFromParcel(parcel);

        assertEquals(id, parcelTackyBody.getDatabaseId());
    }

}
