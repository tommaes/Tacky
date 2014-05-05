package com.nextgen.tacky.basic.State;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maes on 5/05/14.
 */
public class MoodState extends State implements Parcelable{

    public enum MoodValue {
        HAPPY,
        NORMAL,
        SAD
    }

    private final int happyBorder = 75;
    private final int normalBorder = 30;

    private double happinessValue;
    private MoodValue moodValue = MoodValue.NORMAL;

    public MoodState(double state, double gaining) {
        super(state, gaining);
        happinessValue = 0;
    }

    public MoodState(Parcel p) {
        super(p);
        happinessValue = p.readDouble();
        moodValue = MoodValue.valueOf(p.readString());
    }

    public double getHappinessValue() {
        return happinessValue;
    }

    public MoodValue getMoodValue() {
        return moodValue;
    }

    public void calculateHappiness(double energyLevel, double satisfiedLevel){
        double minimum = Math.min(energyLevel, satisfiedLevel);
        if (minimum > 50)
            happinessValue = (energyLevel + satisfiedLevel) / 2;
        else happinessValue = minimum;
        happinessValue += stateLevel;
        happinessValue = Math.min(happinessValue, 100);
        this.calculateState();
        setTackyHappiness();
    }

    private void setTackyHappiness(){
        if (happinessValue > happyBorder)
            moodValue = MoodState.MoodValue.HAPPY;
        else {
            if (happinessValue > normalBorder)
                moodValue = MoodState.MoodValue.NORMAL;
            else moodValue = MoodState.MoodValue.SAD;
        }
    }

    public static final Parcelable.Creator<MoodState> CREATOR
            = new Parcelable.Creator<MoodState>() {
        public MoodState createFromParcel(Parcel in) {
            return new MoodState(in);
        }

        public MoodState[] newArray(int size) {
            return new MoodState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(happinessValue);
        dest.writeString(moodValue.toString());
    }

}
