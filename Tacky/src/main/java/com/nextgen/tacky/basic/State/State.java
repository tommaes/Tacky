package com.nextgen.tacky.basic.State;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maes on 23/10/13.
 */
public class State implements Parcelable {

    protected static final double maxLevel = 100;
    protected static final double minLevel = 0;
    protected double gainingState;
    protected double stateLevel;

    public State(double state, double gaining){
        this.gainingState = gaining;
        this.stateLevel = state;
    }

    public State(Parcel p){
        this.gainingState = p.readDouble();
        this.stateLevel = p.readDouble();
    }

    public static final State.Creator<State> CREATOR = new State.Creator<State>(){

        @Override
        public State createFromParcel(Parcel source) {
            return new State(source);
        }

        @Override
        public State[] newArray(int size) {
            return new State[0];
        }
    };

    public double getMaxLevel() {
        return maxLevel;
    }
    public double getStateLevel() {
        return stateLevel;
    }
    public double getGainingState() { return gainingState; }


    public void calculateState(){
        stateLevel = Math.min(Math.max(minLevel, stateLevel + gainingState), maxLevel);
    }

    public void addState(double value){
        stateLevel = Math.max(Math.min(this.stateLevel + value, maxLevel), minLevel);
    }

    public void setGainingState(double gainingState) {
        this.gainingState = gainingState;
    }

    public boolean aboveMinimum() {
        return stateLevel > 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.gainingState);
        dest.writeDouble(this.stateLevel);
    }
}
