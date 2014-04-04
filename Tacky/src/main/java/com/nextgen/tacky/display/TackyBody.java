package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.db.LocalDatabase;

/**
 * Created by maes on 14/11/13.
 */
public class TackyBody implements TackyDisplayCharacteristics, Parcelable {

    private DisplayItem body = null;
    private int databaseId;

    public TackyBody(String body, int databaseId) {
        this.body = new DisplayItem(body);
        this.databaseId = databaseId;
    }

    private TackyBody(Parcel p) {
        this.body = p.readParcelable(DisplayItem.class.getClassLoader());
        this.databaseId = p.readInt();
    }

    @Override
    public Bitmap getFront(LocalDatabase db) {
        return getBitmap(db, body);
    }

    @Override
    public Bitmap getSide(LocalDatabase db) {
        return getBitmap(db, body);
    }

    @Override
    public Bitmap getSleep(LocalDatabase db) {
        return getBitmap(db, body);
    }

    @Override
    public Bitmap getUp(LocalDatabase db) {
        return getBitmap(db, body);
    }

    @Override
    public Bitmap getDown(LocalDatabase db) {
        return getBitmap(db, body);
    }

    private Bitmap getBitmap(LocalDatabase db, DisplayItem displayItem) {
        if (displayItem.hasBitmap())
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
        dest.writeParcelable(body, flags);
        dest.writeInt(databaseId);
    }
    public final static String TACKYEXPRESSION = "com.nextgen.tacky.display.TackyBody";
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public TackyBody createFromParcel(Parcel source) {
            return new TackyBody(source);
        }
        @Override
        public TackyBody[] newArray(int size) {
            return new TackyBody[size];
        }
    };
}
