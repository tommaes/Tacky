package com.nextgen.tacky.basic;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

/**
 * Created by maes on 23/10/13.
 */
public class Tacky implements Parcelable {

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

    private String name;
    private Time dayOfBirth;
    private double happiness;
    private State happinessState;
    private State energy;
    private State satisfied;
    private Room currentRoom;
    private TackyStatus currentStatus = TackyStatus.NORMAL;
    private TackyHappiness tackyHappiness;

    private final double startValue = 75;
    private final double sleepValue = 0.0015;
    private final double awakeValue = -0.0015;

    private int headId;
    private int bodyId;
    private int expressionId;


    public Tacky(String n, Room room, int h, int b, int e) {
        this.name = n;
        this.dayOfBirth = new Time();
        this.dayOfBirth.setToNow();
        this.happinessState = new State(0, awakeValue / 5);
        this.energy = new State(startValue, awakeValue);
        this.satisfied = new State(startValue, awakeValue);
        this.currentRoom = room;

        headId = h;
        bodyId = b;
        expressionId = e;

        calculateHappiness();
    }

    public Tacky(String n, long birthday,
                 State happiness, State energy, State satisfied,
                 Room r, int h, int b, int e
    ) {
        this.name = n;
        Time time = new Time();
        time.set(birthday);
        this.dayOfBirth = time;
        this.happinessState = happiness;
        this.energy = energy;
        this.satisfied = satisfied;
        this.currentRoom = r;

        headId = h;
        bodyId = b;
        expressionId = e;

        calculateHappiness();
    }

    Tacky(Parcel p) {
        this.name = p.readString();

        Long time = p.readLong();
        this.dayOfBirth =  new Time();
        this.dayOfBirth.set(time);
        this.happinessState = p.readParcelable(State.class.getClassLoader());
        this.energy = p.readParcelable(State.class.getClassLoader());
        this.satisfied = p.readParcelable(State.class.getClassLoader());
        this.currentRoom = p.readParcelable(Room.class.getClassLoader());
        this.currentStatus = TackyStatus.valueOf(p.readString());

        headId = p.readInt();
        bodyId = p.readInt();
        expressionId = p.readInt();

        calculateHappiness();
    }

    public final static String TACKY = "com.nextgen.tacky.basic.Tacky";

    public static final Tacky.Creator<Tacky> CREATOR = new Tacky.Creator<Tacky>(){

        @Override
        public Tacky createFromParcel(Parcel source) {
            return new Tacky(source);
        }

        @Override
        public Tacky[] newArray(int size) {
            return new Tacky[size];
        }
    };

    public String getName() {
        return name;
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

    public Room getCurrentRoom() {
        return currentRoom;
    }
    public Time getDayOfBirth() {
        return dayOfBirth;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getHeadId() {
        return headId;
    }

    public int getExpressionId() {
        return expressionId;
    }

    public int getBodyId() {
        return bodyId;
    }

    public boolean isAlive(){
        return (energy.getStateLevel() > 0) && (satisfied.getStateLevel() > 0);
    }

    public void move(){
        this.happinessState.addState(0.001);
        this.energy.addState(-0.00001);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.dayOfBirth.toMillis(false));
        dest.writeParcelable(this.happinessState, flags);
        dest.writeParcelable(this.energy, flags);
        dest.writeParcelable(this.satisfied, flags);
        dest.writeParcelable(this.currentRoom, flags);
        dest.writeString(this.currentStatus.toString());
        dest.writeInt(this.headId);
        dest.writeInt(this.bodyId);
        dest.writeInt(this.expressionId);
    }
}
