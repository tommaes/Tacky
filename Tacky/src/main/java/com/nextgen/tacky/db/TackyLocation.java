package com.nextgen.tacky.db;

import android.location.Location;

import com.nextgen.tacky.activities.rooms.Outdoors;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;

/**
 * Created by Bram on 3/12/13.
 */
public class TackyLocation {
    private Room room;
    private Outdoors.OutdoorsType type;
    private Location location;
    private double radius;

    public TackyLocation(String name, String image, Outdoors.OutdoorsType type, Location loc, double radius) {
        this.room = new Room(name, image, Room.RoomType.OUTDOORS);
        this.type = type;
        this.location = loc;
        this.radius = radius;
    }

    public TackyLocation(String name, String image, Outdoors.OutdoorsType type, double latitude, double longitude, double radius) {
        this.room = new Room(name, image, Room.RoomType.OUTDOORS);
        this.type = type;
        this.radius = radius;

        this.location = new Location("TACKY_LOCATION");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
    }

    public void transferTackyTo(Tacky tacky){
        tacky.setCurrentRoom(room);
    }

    public String getRoomName() {
        return room.getName();
    }

    public Outdoors.OutdoorsType getType() {
        return type;
    }

    public double getRadius() {
        return radius;
    }

    public Location getLocation() {
        return location;
    }

    public Outdoors.OutdoorsType getOutdoorsType() {
        return type;
    }

    public boolean isSameLocation(Location location){
        return (location.distanceTo(this.location) <= this.radius);
    }

    public boolean isSameLocation(TackyLocation location){
        return this.isSameLocation(location.getLocation());
    }
}