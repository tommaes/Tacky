package com.nextgen.tackyTests.unitTests.display;

import android.os.Parcel;

import com.nextgen.tacky.display.TackyExpression;

import junit.framework.TestCase;

/**
 * Created by maes on 6/06/14.
 */
public class TackyExpressionTest extends TestCase {

    private String frontHappy = "frontHappy";
    private String frontNormal = "frontNormal";
    private String frontSad = "frontSad";
    private String sideHappy = "sideHappy";
    private String sideNormal = "sideNormal";
    private String sideSad = "sideSad";
    private String sleep = "sleep";
    private int id = 10;

    public void testConstructor() throws Exception {

        TackyExpression tackyExpression = new TackyExpression(frontHappy, frontNormal, frontSad, sideHappy, sideNormal, sideSad, sleep, id);

        // test ID getter
        assertEquals(id, tackyExpression.getDatabaseId());

    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        TackyExpression tackyExpression = new TackyExpression(frontHappy, frontNormal, frontSad, sideHappy, sideNormal, sideSad, sleep, id);
        tackyExpression.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        TackyExpression parcelTackyExpression = TackyExpression.CREATOR.createFromParcel(parcel);

        assertEquals(id, parcelTackyExpression.getDatabaseId());
    }
}
