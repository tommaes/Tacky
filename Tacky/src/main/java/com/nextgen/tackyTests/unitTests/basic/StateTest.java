package com.nextgen.tackyTests.unitTests.basic;

import com.nextgen.tacky.basic.State;

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

    public void testGetStateLevel() throws Exception {

        State state = new State(level, gaining);
        assertEquals(level, state.getStateLevel());

    }

    public void testSetGainingState() throws Exception {
        State state = new State(level, gaining);
        double newGainingState = 2;
        state.setGainingState(newGainingState);
        assertEquals(newGainingState, state.getGainingState());
    }
}
