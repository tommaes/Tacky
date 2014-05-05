package com.nextgen.tacky.basic.tacky;

import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.basic.State.State;

/**
 * Created by maes on 19/04/14.
 */
public class TackyState implements Parcelable {

    public enum TackyStatus{
        NORMAL,
        SLEEPING,
        TRYTOSLEEP
    }

    private MoodState moodState;
    private State energy;
    private State satisfied;

    private TackyStatus currentStatus = TackyStatus.NORMAL;

    private final double startValue = 75;
    private final float sleepValue = 0.0015f;
    private final float awakeValue = -0.0015f;

    private final double moveGain = 0.001;
    private final double moveCost = -0.00001;

    public TackyState() {
        this.moodState = new MoodState(0, awakeValue / 5);
        this.energy = new State(startValue, awakeValue);
        this.satisfied = new State(startValue, awakeValue);
        calculateHappiness();
    }

    public TackyState(MoodState moodState, State energy, State satisfied) {
        this.moodState = moodState;
        this.energy = energy;
        this.satisfied = satisfied;
        calculateHappiness();
    }

    public TackyState(Parcel p){

        this.moodState = p.readParcelable(MoodState.class.getClassLoader());
        this.energy = p.readParcelable(State.class.getClassLoader());
        this.satisfied = p.readParcelable(State.class.getClassLoader());
        this.currentStatus = TackyStatus.valueOf(p.readString());
        calculateHappiness();
    }

    public synchronized void calculateHappiness(){
        moodState.calculateHappiness(energy.getStateLevel(), satisfied.getStateLevel());
    }

    public synchronized void calculateEnergy(){
        this.energy.calculateState();
    }

    public synchronized double getEnergyLevel() {
        return energy.getStateLevel();
    }

    public synchronized double getEnergyGain() {
        return energy.getGainingState();
    }

    public synchronized double getMaxEnergyLevel() {
        return energy.getMaxLevel();
    }

    public synchronized void calculateSatisfaction(){
        this.satisfied.calculateState();
    }

    public synchronized double getSatisfiedLevel() {
        return satisfied.getStateLevel();
    }

    public synchronized double getSatisfiedGain() {
        return satisfied.getGainingState();
    }

    public synchronized double getMaxSatisfiedLevel() {
        return satisfied.getMaxLevel();
    }

    public synchronized void addSatisfiedState(Food f){
        this.satisfied.addState(f.getEnergyValue());
        f.used();
    }

    public TackyStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(TackyStatus currentStatus) {
        this.currentStatus = currentStatus;
        switch(currentStatus) {
            case SLEEPING: {
                this.energy.setGainingState(sleepValue);
                break;
            }
            default: {
                this.energy.setGainingState(awakeValue);
            }
        }
    }

    public synchronized double getHappinessLevel() {
        return moodState.getHappinessValue();
    }

    public double getHappinessStateLevel(){
        return moodState.getStateLevel();
    }

    public Double getHappinessGain() {
        return moodState.getGainingState();
    }

    public double getMaxHappinessLevel() {
        return moodState.getMaxLevel();
    }

    public MoodState.MoodValue getMoodValue() {
        return moodState.getMoodValue();
    }

    public boolean isAlive(){
        return (energy.aboveMinimum() && satisfied.aboveMinimum());
    }

    public void move(){
        this.moodState.addState(moveGain);
        this.energy.addState(moveCost);
    }

    public static final TackyState.Creator<TackyState> CREATOR = new TackyState.Creator<TackyState>(){

        @Override
        public TackyState createFromParcel(Parcel source) {
            return new TackyState(source);
        }

        @Override
        public TackyState[] newArray(int size) {
            return new TackyState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.moodState, flags);
        dest.writeParcelable(this.energy, flags);
        dest.writeParcelable(this.satisfied, flags);
        dest.writeString(this.currentStatus.toString());
    }
}
