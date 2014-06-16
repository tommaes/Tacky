package com.nextgen.tacky.display;

import android.os.Parcel;
import android.os.Parcelable;

import com.nextgen.tacky.basic.State.MoodState;

/**
 * Created by maes on 14/11/13.
 */
public class TackyExpression implements Parcelable {

    private Expression front;
    private Expression side;
    private Expression sleep;
    private int databaseId;

    public TackyExpression(Expression front, Expression side, Expression sleep, int databaseId) {
        this.front = front;
        this.side = side;
        this.sleep = sleep;
        this.databaseId = databaseId;
    }

    private TackyExpression(Parcel p) {
        this.front = p.readParcelable(Expression.class.getClassLoader());
        this.side = p.readParcelable(Expression.class.getClassLoader());
        this.sleep = p.readParcelable(Expression.class.getClassLoader());
        this.databaseId = p.readInt();
    }

    public DisplayItem getFront(MoodState.MoodValue moodValue){
        return front.getExpression(moodValue);
    }

    public DisplayItem getSide(MoodState.MoodValue moodValue){
        return side.getExpression(moodValue);
    }

    public DisplayItem getSleep() {
        return sleep.getExpression(MoodState.MoodValue.NORMAL);
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
        dest.writeParcelable(front, flags);
        dest.writeParcelable(side, flags);
        dest.writeParcelable(sleep, flags);
        dest.writeInt(databaseId);
    }

    public static final TackyExpression.Creator<TackyExpression> CREATOR = new TackyExpression.Creator<TackyExpression>() {
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
