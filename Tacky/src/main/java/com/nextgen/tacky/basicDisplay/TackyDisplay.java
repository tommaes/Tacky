package com.nextgen.tacky.basicDisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.nextgen.tacky.activities.rooms.MainRoom;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.TackyDisplayObject_DB;
import com.nextgen.tacky.display.TackyDisplayObject;
import com.nextgen.tacky.db.LocalDatabase;

/**
 * Created by maes on 29/10/13.
 */
public class TackyDisplay implements Display {

    private Tacky tacky;
    private Bitmap rip;
    protected Display display;

    private float xPosition;
    private float yPosition;
    private float middleWidth = -1;
    private float middleHeight = -1;

    private TackyPosition tackyPosition;

    private TackyDisplayObject_DB tackyDisplayObject_db;
    private TackyDisplayObject tackyDisplayObject;

    private enum TackyPosition {
        DOWNLEFT,
        DOWNRIGHT,
        NORMAL,
        NORMALLEFT,
        NORMALRIGHT,
        UPLEFT,
        UPRIGHT
    }

    public TackyDisplay(Context context, Tacky t, Display display) {
        this.tacky = t;
        int imageResource2 = context.getResources().getIdentifier("rip", "drawable", MainRoom.MAIN_TACKY_PACKAGE);
        this.rip = BitmapFactory.decodeResource(context.getResources(), imageResource2);
        this.tackyDisplayObject_db = new TackyDisplayObject_DB(context);
        this.tackyDisplayObject = tackyDisplayObject_db.getTackyDisplayObject(t);
        this.tackyPosition = TackyPosition.NORMAL;
        this.display = display;
    }

    public synchronized void changeMiddle(float w, float h) {
        if(middleHeight == -1) {
            xPosition = w;
            yPosition = h;
        }
        middleWidth = w;
        middleHeight = h;
    }

    public synchronized void moveTacky(float x, float y){
        if(xPosition < x) {
            if(yPosition < y){
                this.tackyPosition = TackyPosition.UPRIGHT;
            }
            else if(yPosition == y){
                this.tackyPosition = TackyPosition.NORMALRIGHT;
            }
            else this.tackyPosition = TackyPosition.DOWNRIGHT;
        }
        else if(xPosition == x){
            this.tackyPosition = TackyPosition.NORMAL;
        }
        else {
            if(yPosition < y){
                this.tackyPosition = TackyPosition.UPLEFT;
            }
            else if(yPosition == y){
                this.tackyPosition = TackyPosition.NORMALLEFT;
            }
            else this.tackyPosition = TackyPosition.DOWNLEFT;
        }
        xPosition = x;
        yPosition = y;
    }

    public void display(Canvas canvas){
        display.display(canvas);
            if(tacky.isAlive()) {
                switch(tacky.getRoomType()) {
                    // first check for specific room
                    case BEDROOM: {
                        switch (tacky.getCurrentStatus()) {
                            case TRYTOSLEEP: {
                                displayFrontTackyReadyToSleep(canvas);
                                break;
                            }
                            default: {
                                displayFrontTackySleep(canvas);
                                break;
                            }
                        }
                        break;
                    }
                    default: {
                        // then check for specific position of Tacky
                        switch(this.tackyPosition) {
                            case NORMAL: {
                                displayFrontTacky(canvas);
                                break;
                            }
                            case DOWNLEFT: {
                                displayLeftDownTacky(canvas);
                                break;
                            }
                            case DOWNRIGHT: {
                                displayRightDownTacky(canvas);
                                break;
                            }
                            case UPLEFT: {
                                displayLeftUpTacky(canvas);
                                break;
                            }
                            case UPRIGHT: {
                                displayRightUpTacky(canvas);
                                break;
                            }
                            case NORMALLEFT: {
                                displayLeftNormalTacky(canvas);
                                break;
                            }
                            case NORMALRIGHT: {
                                displayRightNormalTacky(canvas);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                float y = middleHeight - (this.rip.getHeight() / 3);
                float x = middleWidth - (this.rip.getWidth() / 2);
                canvas.drawBitmap(this.rip, x, y, null);
            }
    }

    private void displayFrontTacky(Canvas canvas){
        if (this.tackyDisplayObject != null) {
            Bitmap body = tackyDisplayObject.getBodyFront(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadFront(tackyDisplayObject_db);
            Bitmap expression = tackyDisplayObject.getExpressionFront(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            displayTacky(canvas, head, body, expression);
        }
    }

    private void displayFrontTackyReadyToSleep(Canvas canvas){
        if (this.tackyDisplayObject != null) {
            Bitmap body = tackyDisplayObject.getBodySleep(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadSleep(tackyDisplayObject_db);
            Bitmap expression = tackyDisplayObject.getExpressionFront(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            displayTacky(canvas, head, body, expression);
        }
    }

    private void displayFrontTackySleep(Canvas canvas){
        if (this.tackyDisplayObject != null) {
            Bitmap body = tackyDisplayObject.getBodySleep(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadSleep(tackyDisplayObject_db);
            Bitmap expression = tackyDisplayObject.getExpressionSleep(tackyDisplayObject_db);

            displayTacky(canvas, head, body, expression);
        }
    }

    private void displayLeftNormalTacky(Canvas canvas){
        if (this.tackyDisplayObject != null) {

            Bitmap body = tackyDisplayObject.getBodySide(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadSide(tackyDisplayObject_db);
            Bitmap expression = tackyDisplayObject.getExpressionSide(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            displayTacky(canvas, head, body, expression);
        }
    }

    private void displayRightNormalTacky(Canvas canvas){
        if (this.tackyDisplayObject != null) {
            Matrix matrix = new Matrix();
            matrix.setScale(-1, 1);

            Bitmap body = tackyDisplayObject.getBodySide(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadSide(tackyDisplayObject_db);
            Bitmap front = tackyDisplayObject.getExpressionSide(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            Bitmap bodyRight = Bitmap.createBitmap(body, 0, 0, body.getWidth(), body.getHeight(), matrix, true);
            Bitmap headRight = Bitmap.createBitmap(head, 0, 0, head.getWidth(), head.getHeight(), matrix, true);
            Bitmap expressionRight = Bitmap.createBitmap(front, 0, 0, front.getWidth(), front.getHeight(), matrix, true);

            displayTacky(canvas, headRight, bodyRight, expressionRight);
        }
    }

    private void displayLeftUpTacky(Canvas canvas){
        if (this.tackyDisplayObject != null) {

            Bitmap body = tackyDisplayObject.getBodyUp(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadUp(tackyDisplayObject_db);
            Bitmap expression = tackyDisplayObject.getExpressionSide(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            displayTacky(canvas, head, body, expression);
        }
    }

    private void displayRightUpTacky(Canvas canvas){
        if (this.tackyDisplayObject != null) {
            Matrix matrix = new Matrix();
            matrix.setScale(-1, 1);

            Bitmap body = tackyDisplayObject.getBodyUp(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadUp(tackyDisplayObject_db);
            Bitmap front = tackyDisplayObject.getExpressionSide(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            Bitmap bodyRight = Bitmap.createBitmap(body, 0, 0, body.getWidth(), body.getHeight(), matrix, true);
            Bitmap headRight = Bitmap.createBitmap(head, 0, 0, head.getWidth(), head.getHeight(), matrix, true);
            Bitmap expressionRight = Bitmap.createBitmap(front, 0, 0, front.getWidth(), front.getHeight(), matrix, true);

            displayTacky(canvas, headRight, bodyRight, expressionRight);
        }
    }

    private void displayLeftDownTacky(Canvas canvas){
        if (this.tackyDisplayObject != null) {

            Bitmap body = tackyDisplayObject.getBodyDown(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadDown(tackyDisplayObject_db);
            Bitmap expression = tackyDisplayObject.getExpressionSide(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            displayTacky(canvas, head, body, expression);
        }
    }

    private void displayRightDownTacky(Canvas canvas){
        if (this.tackyDisplayObject != null) {
            Matrix matrix = new Matrix();
            matrix.setScale(-1, 1);

            Bitmap body = tackyDisplayObject.getBodyDown(tackyDisplayObject_db);
            Bitmap head = tackyDisplayObject.getHeadDown(tackyDisplayObject_db);
            Bitmap front = tackyDisplayObject.getExpressionSide(tackyDisplayObject_db, tacky.getCurrentStatus(), tacky.getTackyHappiness());

            Bitmap bodyRight = Bitmap.createBitmap(body, 0, 0, body.getWidth(), body.getHeight(), matrix, true);
            Bitmap headRight = Bitmap.createBitmap(head, 0, 0, head.getWidth(), head.getHeight(), matrix, true);
            Bitmap expressionRight = Bitmap.createBitmap(front, 0, 0, front.getWidth(), front.getHeight(), matrix, true);

            displayTacky(canvas, headRight, bodyRight, expressionRight);
        }
    }

    private void displayTacky(Canvas canvas, Bitmap head, Bitmap body, Bitmap expression) {

        float yPositionPic = yPosition - (body.getHeight() / 2);
        float xPositionPic = xPosition - (body.getWidth() / 2);

        canvas.drawBitmap(head, xPositionPic, yPositionPic, null);
        canvas.drawBitmap(body, xPositionPic, yPositionPic, null);
        canvas.drawBitmap(expression, xPositionPic, yPositionPic, null);
    }

}
