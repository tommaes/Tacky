package com.nextgen.tacky.basic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maes on 30/10/13.
 */
public class Food extends MainItem implements Parcelable {

    private int energyValue;
    private int totalUses;

    public Food(String name, String visualisation, int v, int uses) {
        super(name, visualisation);
        energyValue = v;
        totalUses = uses;
    }

    private Food(Parcel p) {
        super(p.readString(), p.readString());
        energyValue = p.readInt();
        totalUses = p.readInt();
    }

    public int getEnergyValue(){
        return energyValue;
    }

    public int getTotalUses() {
        return totalUses;
    }

    public void used(){
        if(this.totalUses > 0)
            this.totalUses -= 1;
    }

    public boolean canStillBeUsed(){
        return (this.totalUses != 0);
    }

    public static final Parcelable.Creator<Food> CREATOR
            = new Parcelable.Creator<Food>() {
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(visualization);
        dest.writeInt(energyValue);
        dest.writeInt(totalUses);
    }
}