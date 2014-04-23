package com.nextgen.tacky.basic.tacky;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.State;

/**
 * Created by maes on 23/10/13.
 */
public class Tacky implements Parcelable {


    private String name;
    private Time dayOfBirth;
    private TackyState tackyState;
    private Room currentRoom;


    private int headId;
    private int bodyId;
    private int expressionId;


    public Tacky(String n, Room room, int h, int b, int e) {
        this.name = n;
        this.dayOfBirth = new Time();
        this.dayOfBirth.setToNow();
        this.currentRoom = room;
        this.tackyState = new TackyState();

        headId = h;
        bodyId = b;
        expressionId = e;

    }

    public Tacky(String n, long birthday,
                 TackyState tackyState,
                 Room r, int h, int b, int e
    ) {
        this.name = n;
        Time time = new Time();
        time.set(birthday);
        this.dayOfBirth = time;
        this.currentRoom = r;
        this.tackyState = tackyState;
        headId = h;
        bodyId = b;
        expressionId = e;

    }

    Tacky(Parcel p) {
        this.name = p.readString();

        Long time = p.readLong();
        this.dayOfBirth =  new Time();
        this.dayOfBirth.set(time);
        this.tackyState = p.readParcelable(TackyState.class.getClassLoader());
        this.currentRoom = p.readParcelable(Room.class.getClassLoader());

        headId = p.readInt();
        bodyId = p.readInt();
        expressionId = p.readInt();

    }

    public final static String TACKY = "com.nextgen.tacky.basic.tacky.Tacky";

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
        return tackyState.isAlive();
    }

    public void move(){
        tackyState.move();
    }

    public void calculateHappiness() {
        tackyState.calculateHappiness();
    }

    public TackyState.TackyStatus getCurrentStatus() {
        return tackyState.getCurrentStatus();
    }

    public void calculateSatisfaction() {
        tackyState.calculateSatisfaction();
    }

    public double getMaxSatisfiedLevel() {
        return tackyState.getMaxSatisfiedLevel();
    }

    public double getEnergyLevel() {
        return tackyState.getEnergyLevel();
    }

    public TackyState.TackyHappiness getTackyHappiness() {
        return tackyState.getTackyHappiness();
    }

    public void calculateEnergy() {
        tackyState.calculateEnergy();
    }

    public double getHappinessStateLevel() {
        return tackyState.getHappinessStateLevel();
    }

    public double getSatisfiedGain() {
        return tackyState.getSatisfiedGain();
    }

    public void setCurrentStatus(TackyState.TackyStatus currentStatus) {
        tackyState.setCurrentStatus(currentStatus);
    }

    public double getMaxEnergyLevel() {
        return tackyState.getMaxEnergyLevel();
    }

    public Double getHappinessGain() {
        return tackyState.getHappinessGain();
    }

    public double getSatisfiedLevel() {
        return tackyState.getSatisfiedLevel();
    }

    public double getMaxHappinessLevel() {
        return tackyState.getMaxHappinessLevel();
    }

    public double getEnergyGain() {
        return tackyState.getEnergyGain();
    }

    public void addSatisfiedState(Food f) {
        tackyState.addSatisfiedState(f);
    }

    public double getHappinessLevel() {
        return tackyState.getHappinessLevel();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.dayOfBirth.toMillis(false));
        dest.writeParcelable(this.tackyState, flags);
        dest.writeParcelable(this.currentRoom, flags);
        dest.writeInt(this.headId);
        dest.writeInt(this.bodyId);
        dest.writeInt(this.expressionId);
    }
}
