package com.nextgen.tackyTests.unitTests.display;

import android.os.Parcel;

import com.nextgen.tacky.display.DisplayItem;

import junit.framework.TestCase;

/**
 * Created by maes on 5/06/14.
 */
public class DisplayItemTest extends TestCase {

    private String name = "name";

    public void testConstructor() throws Exception {

        DisplayItem displayItem = new DisplayItem(name);

        // test Name getter
        assertEquals(name, displayItem.getName());

        // test empty bitmap
        assertFalse(displayItem.hasBitmap());
    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        DisplayItem displayItem = new DisplayItem(name);
        displayItem.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        DisplayItem parcelDisplayItem = DisplayItem.CREATOR.createFromParcel(parcel);

        assertEquals(name, parcelDisplayItem.getName());
    }

}
