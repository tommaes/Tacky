package com.nextgen.tacky.basic.tacky;

import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.basic.State;

/**
 * Created by maes on 19/04/14.
 */
public class TackyState implements Parcelable {

    public enum TackyStatus{
        NORMAL,
        SLEEPING,
        TRYTOSLEEP
    }

    public enum TackyHappiness {
        HAPPY,
        NORMAL,
        SAD
    }

    private double happiness;
    private State happinessState;
    private State energy;
    private State satisfied;

    private TackyStatus currentStatus = TackyStatus.NORMAL;
    private TackyHappiness tackyHappiness;

    private final double startValue = 75;
    private final float sleepValue = 0.0015f;
    private final float awakeValue = -0.0015f;

    public TackyState() {
        this.happinessState = new State(0, awakeValue / 5);
        this.energy = new State(startValue, awakeValue);
        this.satisfied = new State(startValue, awakeValue);
        calculateHappiness();
    }

    public TackyState(State happinessState, State energy, State satisfied) {
        this.happinessState = happinessState;
        this.energy = energy;
        this.satisfied = satisfied;
        calculateHappiness();
    }

    public TackyState(Parcel p){

        this.happinessState = p.readParcelable(State.class.getClassLoader());
        this.energy = p.readParcelable(State.class.getClassLoader());
        this.satisfied = p.readParcelable(State.class.getClassLoader());
        this.currentStatus = TackyStatus.valueOf(p.readString());
        calculateHappiness();
    }

    public synchronized void calculateHappiness(){
        double minimum = Math.min(energy.getStateLevel(), satisfied.getStateLevel());
        if (minimum > 50)
            happiness = (energy.getStateLevel() + satisfied.getStateLevel()) / 2;
        else happiness = minimum;
        happiness += happinessState.getStateLevel();
        happiness = Math.min(happiness, 100);
        happinessState.calculateState();
        setTackyHappiness();
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



    public synchronized double getHappinessLevel() {
        return happiness;
    }

    public double getHappinessStateLevel(){
        return happinessState.getStateLevel();
    }

    public synchronized void addHappinessStateLevel(double lvl){
        this.happinessState.addState(lvl);
    }

    public Double getHappinessGain() {
        return happinessState.getGainingState();
    }

    public double getMaxHappinessLevel() {
        return happinessState.getMaxLevel();
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

    public TackyHappiness getTackyHappiness() {
        return tackyHappiness;
    }

    private void setTackyHappiness(){
        if (this.happiness > 75)
            this.tackyHappiness = TackyHappiness.HAPPY;
        else {
            if (this.happiness > 30)
                this.tackyHappiness = TackyHappiness.NORMAL;
            else this.tackyHappiness = TackyHappiness.SAD;
        }
    }

    public boolean isAlive(){
        return (energy.getStateLevel() > 0) && (satisfied.getStateLevel() > 0);
    }

    public void move(){
        this.happinessState.addState(0.001);
        this.energy.addState(-0.00001);
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
        dest.writeParcelable(this.happinessState, flags);
        dest.writeParcelable(this.energy, flags);
        dest.writeParcelable(this.satisfied, flags);
        dest.writeString(this.currentStatus.toString());
    }
}
