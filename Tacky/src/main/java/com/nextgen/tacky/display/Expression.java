package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.db.localDB.LocalTackyExpression_DB;

/**
 * Created by maes on 7/06/14.
 */
public class Expression implements Parcelable {

    // pure classes
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

    public DisplayItem getExpression(MoodState.MoodValue moodValue){
        DisplayItem displayItem = null;
        switch (moodValue) {
            case HAPPY: {
                displayItem = getHappyExpression();
                break;
            }
            case SAD: {
                displayItem = getSadExpression();
                break;
            }
            case NORMAL: {
                displayItem = getNormalExpression();
                break;
            }
        }
        return displayItem;
    }

    private DisplayItem getHappyExpression() {
        return happy;
    }

    private DisplayItem getNormalExpression() {
        return normal;
    }

    private DisplayItem getSadExpression() {
        return sad;
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
