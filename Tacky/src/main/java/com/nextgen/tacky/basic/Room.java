package com.nextgen.tacky.basic;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maes on 25/10/13.
 */
public class Room extends MainItem implements Parcelable {

    public enum RoomType { // do not change order or remove elements, only add at the end
        BEDROOM,
        BATHROOM,
        KITCHEN,
        OUTDOORS,
        MAIN
    }

    private RoomType roomType;


    public Room(String n, String str, RoomType type) {
        super(n, str);
        this.roomType = type;
    }
    public Room(String n, String str, int type) {
        super(n, str);
        this.roomType = RoomType.values()[type];
    }

    Room(Parcel p){
        super(p.readString(), p.readString());
        this.roomType = (RoomType) p.readSerializable();
    }

    public void setVisualization(String visualization){
        super.setVisualization(visualization);
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public static final Room.Creator<Room> CREATOR = new Room.Creator<Room>(){

        @Override
        public Room createFromParcel(Parcel source) {
            return new Room(source);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(super.getName());
        dest.writeString(super.getVisualization());
        dest.writeSerializable(this.roomType);
    }

}
