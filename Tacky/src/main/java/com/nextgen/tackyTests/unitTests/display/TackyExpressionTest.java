package com.nextgen.tackyTests.unitTests.display;

import android.os.Parcel;

import com.nextgen.tacky.display.Expression;
import com.nextgen.tacky.display.TackyExpression;

import junit.framework.TestCase;

/**
 * Created by maes on 6/06/14.
 */
public class TackyExpressionTest extends TestCase {

    private int id = 10;

    Expression front = new Expression("frontHappy", "frontNormal", "frontSad");
    Expression side = new Expression("sideHappy", "sideNormal", "sideSad");
    Expression sleep = new Expression("sleep");

    public void testConstructor() throws Exception {

        TackyExpression tackyExpression = new TackyExpression(front, side, sleep, id);

        // test ID getter
        assertEquals(id, tackyExpression.getDatabaseId());

    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        TackyExpression tackyExpression = new TackyExpression(front, side, sleep, id);
        tackyExpression.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        TackyExpression parcelTackyExpression = TackyExpression.CREATOR.createFromParcel(parcel);

        assert parcelTackyExpression != null;
        assertEquals(id, parcelTackyExpression.getDatabaseId());
    }
}
