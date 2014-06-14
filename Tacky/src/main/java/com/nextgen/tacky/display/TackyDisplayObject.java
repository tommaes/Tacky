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

    public Bitmap getHeadFront(LocalTackyDisplayObject_DB db) {
        return head.getFront(db.getTackyHead_db());
    }

    public Bitmap getHeadSleep(LocalTackyDisplayObject_DB db) {
        return head.getSleep(db.getTackyHead_db());
    }

    public Bitmap getHeadDown(LocalTackyDisplayObject_DB db) {
        return head.getDown(db.getTackyHead_db());
    }

    public Bitmap getHeadSide(LocalTackyDisplayObject_DB db) {
        return head.getSide(db.getTackyHead_db());
    }

    public Bitmap getHeadUp(LocalTackyDisplayObject_DB db) {
        return head.getUp(db.getTackyHead_db());
    }

    public Bitmap getBodyFront(LocalTackyDisplayObject_DB db) {
        return body.getFront(db.getTackyBody_db());
    }

    public Bitmap getBodySleep(LocalTackyDisplayObject_DB db) {
        return body.getSleep(db.getTackyBody_db());
    }

    public Bitmap getBodyUp(LocalTackyDisplayObject_DB db) {
        return body.getUp(db.getTackyBody_db());
    }

    public Bitmap getBodyDown(LocalTackyDisplayObject_DB db) {
        return body.getDown(db.getTackyBody_db());
    }

    public Bitmap getBodySide(LocalTackyDisplayObject_DB db) {
        return body.getSide(db.getTackyBody_db());
    }

    public Bitmap getExpressionFront(LocalTackyDisplayObject_DB db, MoodState.MoodValue moodValue) {
        return expression.getFront(db.getTackyExpression_db(), moodValue);
    }

    public Bitmap getExpressionSide(LocalTackyDisplayObject_DB db, MoodState.MoodValue moodValue) {
        return expression.getSide(db.getTackyExpression_db(), moodValue);
    }

    public Bitmap getExpressionSleep(LocalTackyDisplayObject_DB db) {
        return expression.getSleep(db.getTackyExpression_db());
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
