package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.Tacky;
import com.nextgen.tacky.db.LocalDatabase;

/**
 * Created by maes on 14/11/13.
 */
public class TackyExpression implements Parcelable {

    private DisplayItem frontHappy;
    private DisplayItem frontNormal;
    private DisplayItem frontSad;
    private DisplayItem sideHappy;
    private DisplayItem sideNormal;
    private DisplayItem sideSad;
    private DisplayItem sleep;
    private int databaseId;

    public TackyExpression(String frontHappy, String frontNormal, String frontSad, String sideHappy, String sideNormal, String sideSad, String sleep, int databaseId) {
        this.frontHappy = new DisplayItem(frontHappy);
        this.frontNormal = new DisplayItem(frontNormal);
        this.frontSad = new DisplayItem(frontSad);
        this.sideHappy = new DisplayItem(sideHappy);
        this.sideNormal = new DisplayItem(sideNormal);
        this.sideSad = new DisplayItem(sideSad);
        this.sleep = new DisplayItem(sleep);
        this.databaseId = databaseId;
    }

    private TackyExpression(Parcel p) {
        this.frontHappy = p.readParcelable(DisplayItem.class.getClassLoader());
        this.frontNormal = p.readParcelable(DisplayItem.class.getClassLoader());
        this.frontSad = p.readParcelable(DisplayItem.class.getClassLoader());
        this.sideHappy = p.readParcelable(DisplayItem.class.getClassLoader());
        this.sideNormal = p.readParcelable(DisplayItem.class.getClassLoader());
        this.sideSad = p.readParcelable(DisplayItem.class.getClassLoader());
        this.sleep = p.readParcelable(DisplayItem.class.getClassLoader());
        this.databaseId = p.readInt();
    }

    public Bitmap getFront(LocalDatabase db, Tacky.TackyStatus tackyStatus, Tacky.TackyHappiness tackyHappiness){
        Bitmap displayItem = null;
        switch (tackyStatus) {
            case SLEEPING: {
                displayItem = getSleep(db);
                break;
            }
            default: {
                switch (tackyHappiness) {
                    case HAPPY: {
                        displayItem = getFrontHappy(db);
                        break;
                    }
                    case NORMAL: {
                        displayItem = getFrontNormal(db);
                        break;
                    }
                    case SAD: {
                        displayItem = getFrontSad(db);
                        break;
                    }
                }
            }
        }
        return displayItem;
    }

    public Bitmap getSide(LocalDatabase db, Tacky.TackyStatus tackyStatus, Tacky.TackyHappiness tackyHappiness){
        Bitmap displayItem = null;
        switch (tackyStatus) {
            case SLEEPING: {
                displayItem = getSleep(db);
                break;
            }
            default: {
                switch (tackyHappiness) {
                    case HAPPY: {
                        displayItem = getSideHappy(db);
                        break;
                    }
                    case NORMAL: {
                        displayItem = getSideNormal(db);
                        break;
                    }
                    case SAD: {
                        displayItem = getSideSad(db);
                        break;
                    }
                }
            }
        }
        return displayItem;
    }

    private Bitmap getFrontHappy(LocalDatabase db) {
        return getBitmap(db, frontHappy);
    }

    private Bitmap getFrontNormal(LocalDatabase db) {
        return getBitmap(db, frontNormal);
    }

    private Bitmap getFrontSad(LocalDatabase db) {
        return getBitmap(db, frontSad);
    }

    private Bitmap getSideHappy(LocalDatabase db) {
        return getBitmap(db, sideHappy);
    }

    private Bitmap getSideNormal(LocalDatabase db) {
        return getBitmap(db, sideNormal);
    }

    private Bitmap getSideSad(LocalDatabase db) {
        return getBitmap(db, sideSad);
    }

    public Bitmap getSleep(LocalDatabase db) {
        return getBitmap(db, sleep);
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
        dest.writeParcelable(frontHappy, flags);
        dest.writeParcelable(frontNormal, flags);
        dest.writeParcelable(frontSad, flags);
        dest.writeParcelable(sideHappy, flags);
        dest.writeParcelable(sideNormal, flags);
        dest.writeParcelable(sideSad, flags);
        dest.writeParcelable(sleep, flags);
        dest.writeInt(databaseId);
    }
    public final static String TACKYEXPRESSION = "com.nextgen.tacky.display.TackyHead";
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public TackyExpression createFromParcel(Parcel source) {
            return new TackyExpression(source);
        }
        @Override
        public TackyExpression[] newArray(int size) {
            return new TackyExpression[size];
        }
    };
}
