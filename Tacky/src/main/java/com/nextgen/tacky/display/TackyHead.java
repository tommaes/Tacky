package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.db.LocalDatabase;
import com.nextgen.tacky.db.localDB.TackyBody_DB;
import com.nextgen.tacky.db.localDB.TackyHead_DB;

/**
 * Created by maes on 14/11/13.
 */
public class TackyHead implements Parcelable {

    private DisplayItem front;
    private DisplayItem side;
    private DisplayItem sleep;
    private DisplayItem up;
    private DisplayItem down;
    private int databaseId;

    public TackyHead(String normal, String sleep, String up, String down, int databaseId) {
        this.front = new DisplayItem(normal);
        this.side = new DisplayItem(normal);
        this.sleep = new DisplayItem(sleep);
        this.up = new DisplayItem(up);
        this.down = new DisplayItem(down);
        this.databaseId = databaseId;
    }

    private TackyHead(Parcel p) {
        this.front = p.readParcelable(DisplayItem.class.getClassLoader());
        this.side = p.readParcelable(DisplayItem.class.getClassLoader());
        this.sleep = p.readParcelable(DisplayItem.class.getClassLoader());
        this.up = p.readParcelable(DisplayItem.class.getClassLoader());
        this.down = p.readParcelable(DisplayItem.class.getClassLoader());
        this.databaseId = p.readInt();
    }

    public Bitmap getFront(TackyHead_DB db) {
        return getBitmap(db, front);
    }

    public Bitmap getSide(TackyHead_DB db) {
        return getBitmap(db, side);
    }

    public Bitmap getSleep(TackyHead_DB db) {
        return getBitmap(db, sleep);
    }

    public Bitmap getUp(TackyHead_DB db) {
        return getBitmap(db, up);
    }

    public Bitmap getDown(TackyHead_DB db) {
        return getBitmap(db, down);
    }

    private Bitmap getBitmap(TackyHead_DB db, DisplayItem displayItem) {
        if (!displayItem.hasBitmap())
            displayItem.setBitmap(db.decodeImage(displayItem.getName()));
        return displayItem.getBitmap();
    }

    public int getDatabaseId() {
        return databaseId;
    }

    // implements Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(front, flags);
        dest.writeParcelable(side, flags);
        dest.writeParcelable(sleep, flags);
        dest.writeParcelable(up, flags);
        dest.writeParcelable(down, flags);
        dest.writeInt(databaseId);
    }

    public static final TackyHead.Creator<TackyHead> CREATOR = new TackyHead.Creator<TackyHead>() {
        @Override
        public TackyHead createFromParcel(Parcel source) {
            return new TackyHead(source);
        }
        @Override
        public TackyHead[] newArray(int size) {
            return new TackyHead[size];
        }
    };
}
