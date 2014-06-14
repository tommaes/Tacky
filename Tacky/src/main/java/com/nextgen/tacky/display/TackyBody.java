package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.db.localDB.LocalTackyBody_DB;

/**
 * Created by maes on 14/11/13.
 */
public class TackyBody implements Parcelable {

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

    public Bitmap getFront(LocalTackyBody_DB db) {
        return getBitmap(db, body);
    }

    public Bitmap getSide(LocalTackyBody_DB db) {
        return getBitmap(db, body);
    }

    public Bitmap getSleep(LocalTackyBody_DB db) {
        return getBitmap(db, body);
    }

    public Bitmap getUp(LocalTackyBody_DB db) {
        return getBitmap(db, body);
    }

    public Bitmap getDown(LocalTackyBody_DB db) {
        return getBitmap(db, body);
    }

    private Bitmap getBitmap(LocalTackyBody_DB db, DisplayItem displayItem) {
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
        dest.writeParcelable(body, flags);
        dest.writeInt(databaseId);
    }

    public static final TackyBody.Creator<TackyBody> CREATOR = new TackyBody.Creator<TackyBody>() {
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
