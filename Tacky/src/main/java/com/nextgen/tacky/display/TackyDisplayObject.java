package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.db.localDB.LocalTackyDisplayObject_DB;

/**
 * Created by maes on 7/11/13.
 */
public class TackyDisplayObject implements Parcelable {

    private TackyHead head;
    private TackyBody body;
    private TackyExpression expression;

    public TackyDisplayObject(TackyHead head, TackyBody body, TackyExpression expression) {
        this.head = head;
        this.body = body;
        this.expression = expression;
    }

    private TackyDisplayObject(Parcel p) {
        this.head = p.readParcelable(TackyHead.class.getClassLoader());
        this.body = p.readParcelable(TackyBody.class.getClassLoader());
        this.expression = p.readParcelable(TackyExpression.class.getClassLoader());
    }

    public DisplayItem getHeadFront() {
        return head.getFront();
    }

    public DisplayItem getHeadSleep() {
        return head.getSleep();
    }

    public DisplayItem getHeadDown() {
        return head.getDown();
    }

    public DisplayItem getHeadSide() {
        return head.getSide();
    }

    public DisplayItem getHeadUp() {
        return head.getUp();
    }

    public DisplayItem getBodyFront() {
        return body.getFront();
    }

    public DisplayItem getBodySleep() {
        return body.getSleep();
    }

    public DisplayItem getBodyUp() {
        return body.getUp();
    }

    public DisplayItem getBodyDown() {
        return body.getDown();
    }

    public DisplayItem getBodySide() {
        return body.getSide();
    }

    public DisplayItem getExpressionFront(MoodState.MoodValue moodValue) {
        return expression.getFront(moodValue);
    }

    public DisplayItem getExpressionSide(MoodState.MoodValue moodValue) {
        return expression.getSide(moodValue);
    }

    public DisplayItem getExpressionSleep() {
        return expression.getSleep();
    }

    // implements Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(head, flags);
        dest.writeParcelable(body, flags);
        dest.writeParcelable(expression, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public TackyDisplayObject createFromParcel(Parcel source) {
            return new TackyDisplayObject(source);
        }
        @Override
        public TackyDisplayObject[] newArray(int size) {
            return new TackyDisplayObject[size];
        }
    };
}
