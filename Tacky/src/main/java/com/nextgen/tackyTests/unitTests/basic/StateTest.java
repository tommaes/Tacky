package com.nextgen.tackyTests.unitTests.basic;

import android.os.Parcel;

import com.nextgen.tacky.basic.State.State;

import junit.framework.TestCase;

/**
 * Created by maes on 3/05/14.
 */
public class StateTest extends TestCase {

    private double gaining = 1;
    private double level = 75;

    public void testGetGaining() throws Exception {

        State state = new State(level, gaining);
        assertEquals(gaining, state.getGainingState());

    }

    public void testGetSetGainingState() throws Exception {

        State state = new State(level, gaining);

        assertEquals(level, state.getStateLevel());

        double newGainingState = 2;
        state.setGainingState(newGainingState);
        assertEquals(newGainingState, state.getGainingState());
    }

    public void testCalculateState() throws Exception {

        State state = new State(level, gaining);
        state.calculateState();
        assertEquals(level + gaining, state.getStateLevel());
    }

    public void testAddState() throws Exception {

        State state = new State(level, gaining);
        double addStateValue = 5;
        state.addState(addStateValue);
        assertEquals(level + addStateValue, state.getStateLevel());
    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        State state = new State(level, gaining);
        state.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        State parcelState = State.CREATOR.createFromParcel(parcel);

        assertEquals(gaining, parcelState.getGainingState());
        assertEquals(level, parcelState.getStateLevel());
    }
}
