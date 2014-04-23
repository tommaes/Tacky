package com.nextgen.tacky.display;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;
import com.nextgen.tacky.db.LocalDatabase;

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

    public Bitmap getHeadFront(LocalDatabase db) {
        return head.getFront(db);
    }

    public Bitmap getHeadSleep(LocalDatabase db) {
        return head.getSleep(db);
    }

    public Bitmap getHeadDown(LocalDatabase db) {
        return head.getDown(db);
    }

    public Bitmap getHeadSide(LocalDatabase db) {
        return head.getSide(db);
    }

    public Bitmap getHeadUp(LocalDatabase db) {
        return head.getUp(db);
    }

    public Bitmap getBodyFront(LocalDatabase db) {
        return body.getFront(db);
    }

    public Bitmap getBodySleep(LocalDatabase db) {
        return body.getSleep(db);
    }

    public Bitmap getBodyUp(LocalDatabase db) {
        return body.getUp(db);
    }

    public Bitmap getBodyDown(LocalDatabase db) {
        return body.getDown(db);
    }

    public Bitmap getBodySide(LocalDatabase db) {
        return body.getSide(db);
    }

    public Bitmap getExpressionFront(LocalDatabase db, TackyState.TackyStatus tackyStatus, TackyState.TackyHappiness tackyHappiness) {
        return expression.getFront(db, tackyStatus, tackyHappiness);
    }

    public Bitmap getExpressionSide(LocalDatabase db, TackyState.TackyStatus tackyStatus, TackyState.TackyHappiness tackyHappiness) {
        return expression.getSide(db, tackyStatus, tackyHappiness);
    }

    public Bitmap getExpressionSleep(LocalDatabase db) {
        return expression.getSleep(db);
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
    public final static String TACKYDISPLAYOBJECT = "com.nextgen.tacky.display.TackyDisplayObject";
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
