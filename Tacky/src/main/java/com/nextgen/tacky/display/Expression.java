package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.db.localDB.TackyExpression_DB;

/**
 * Created by maes on 7/06/14.
 */
public class Expression implements Parcelable {

    private DisplayItem happy;
    private DisplayItem normal;
    private DisplayItem sad;

    public Expression(String happy, String normal, String sad) {
        this.happy = new DisplayItem(happy);
        this.normal = new DisplayItem(normal);
        this.sad = new DisplayItem(sad);
    }

    public Expression(String expression){
        this.happy = new DisplayItem(expression);
        this.normal = new DisplayItem(expression);
        this.sad = new DisplayItem(expression);
    }

    private Expression(Parcel p) {
        this.happy = p.readParcelable(DisplayItem.class.getClassLoader());
        this.normal = p.readParcelable(DisplayItem.class.getClassLoader());
        this.sad = p.readParcelable(DisplayItem.class.getClassLoader());
    }

    public Bitmap getExpression(TackyExpression_DB db, MoodState.MoodValue moodValue){
        Bitmap displayItem = null;
        switch (moodValue) {
            case HAPPY: {
                displayItem = getHappyExpression(db);
                break;
            }
            case SAD: {
                displayItem = getSadExpression(db);
                break;
            }
            default: {
                displayItem = getNormalExpression(db);
                break;
            }
        }
        return displayItem;
    }

    private Bitmap getHappyExpression(TackyExpression_DB db) {
        return getBitmap(db, happy);
    }

    private Bitmap getNormalExpression(TackyExpression_DB db) {
        return getBitmap(db, normal);
    }

    private Bitmap getSadExpression(TackyExpression_DB db) {
        return getBitmap(db, sad);
    }

    private Bitmap getBitmap(TackyExpression_DB db, DisplayItem displayItem) {
        if (!displayItem.hasBitmap())
            displayItem.setBitmap(db.decodeImage(displayItem.getName()));
        return displayItem.getBitmap();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(happy, flags);
        dest.writeParcelable(normal, flags);
        dest.writeParcelable(sad, flags);
    }

    public static final Expression.Creator<Expression> CREATOR = new Expression.Creator<Expression>() {
        @Override
        public Expression createFromParcel(Parcel source) {
            return new Expression(source);
        }
        @Override
        public Expression[] newArray(int size) {
            return new Expression[size];
        }
    };
}
