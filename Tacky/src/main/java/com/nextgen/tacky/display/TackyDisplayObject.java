package com.nextgen.tacky.display;

import android.os.Parcel;
import android.os.Parcelable;

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

    public TackyHead getHead() {
        return head;
    }
    public TackyBody getBody() {
        return body;
    }
    public TackyExpression getExpression() {
        return expression;
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
