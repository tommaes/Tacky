package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maes on 14/11/13.
 */
public class DisplayItem implements Parcelable {

    private String name;

    public DisplayItem(String name) {
        this.name = name;
    }

    public String getDisplayItemName() {
        return name;
    }

    // implements Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        // bitmap not included in Parcel, can be reloaded
    }


    public static final DisplayItem.Creator<DisplayItem> CREATOR = new DisplayItem.Creator<DisplayItem>(){
        @Override
        public DisplayItem createFromParcel(Parcel source) {
            String name = source.readString();
            return new DisplayItem(name);
        }
        @Override
        public DisplayItem[] newArray(int size) {
            return new DisplayItem[size];
        }
    };
}