package com.nextgen.tacky.basicDisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.nextgen.tacky.activities.rooms.MainRoom;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.TackyDisplayObject_DB;
import com.nextgen.tacky.db.localDB.LocalTackyDisplayObject_DB;
import com.nextgen.tacky.display.DisplayFactory;
import com.nextgen.tacky.display.DisplayItem;
import com.nextgen.tacky.display.TackyDisplayObject;

/**
 * Created by maes on 29/10/13.
 */
public class TackyDisplay implements Display {

    private Tacky tacky;
    private Bitmap rip;
    protected DisplayFactory displayFactory;
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
        this.displayFactory = new DisplayFactory(context);
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
        DisplayItem body = tackyDisplayObject.getBodyFront();
        DisplayItem head = tackyDisplayObject.getHeadFront();
        DisplayItem expression = tackyDisplayObject.getExpressionFront(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression);
    }

    private void displayFrontTackyReadyToSleep(Canvas canvas){
        DisplayItem body = tackyDisplayObject.getBodySleep();
        DisplayItem head = tackyDisplayObject.getHeadSleep();
        DisplayItem expression = tackyDisplayObject.getExpressionFront(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression);
    }

    private void displayFrontTackySleep(Canvas canvas){
        DisplayItem body = tackyDisplayObject.getBodySleep();
        DisplayItem head = tackyDisplayObject.getHeadSleep();
        DisplayItem expression = tackyDisplayObject.getExpressionSleep();

        displayTacky(canvas, head, body, expression);
    }

    private void displayLeftNormalTacky(Canvas canvas){

        DisplayItem body = tackyDisplayObject.getBodySide();
        DisplayItem head = tackyDisplayObject.getHeadSide();
        DisplayItem expression = tackyDisplayObject.getExpressionSide(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression);
    }

    private void displayRightNormalTacky(Canvas canvas){
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);

        DisplayItem head = tackyDisplayObject.getHeadDown();
        DisplayItem body = tackyDisplayObject.getBodyDown();
        DisplayItem expression = tackyDisplayObject.getExpressionSide(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression, matrix);
    }

    private void displayLeftUpTacky(Canvas canvas){

        DisplayItem body = tackyDisplayObject.getBodyUp();
        DisplayItem head = tackyDisplayObject.getHeadUp();
        DisplayItem expression = tackyDisplayObject.getExpressionSide(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression);
    }

    private void displayRightUpTacky(Canvas canvas){
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);

        DisplayItem head = tackyDisplayObject.getHeadDown();
        DisplayItem body = tackyDisplayObject.getBodyDown();
        DisplayItem expression = tackyDisplayObject.getExpressionSide(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression, matrix);
    }

    private void displayLeftDownTacky(Canvas canvas){

        DisplayItem body = tackyDisplayObject.getBodyDown();
        DisplayItem head = tackyDisplayObject.getHeadDown();
        DisplayItem expression = tackyDisplayObject.getExpressionSide(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression);
    }

    private void displayRightDownTacky(Canvas canvas){
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);

        DisplayItem head = tackyDisplayObject.getHeadDown();
        DisplayItem body = tackyDisplayObject.getBodyDown();
        DisplayItem expression = tackyDisplayObject.getExpressionSide(tacky.getTackyHappiness());

        displayTacky(canvas, head, body, expression, matrix);
    }

    private void displayTacky(Canvas canvas, DisplayItem headItem, DisplayItem bodyItem, DisplayItem expressionItem) {

        Bitmap head = displayFactory.getImage(headItem);
        Bitmap body = displayFactory.getImage(bodyItem);
        Bitmap expression = displayFactory.getImage(expressionItem);

        float yPositionPic = yPosition - (body.getHeight() / 2);
        float xPositionPic = xPosition - (body.getWidth() / 2);

        canvas.drawBitmap(head, xPositionPic, yPositionPic, null);
        canvas.drawBitmap(body, xPositionPic, yPositionPic, null);
        canvas.drawBitmap(expression, xPositionPic, yPositionPic, null);
    }

    private void displayTacky(Canvas canvas, DisplayItem headItem, DisplayItem bodyItem, DisplayItem expressionItem, Matrix matrix) {

        Bitmap head = displayFactory.getImage(headItem);
        Bitmap body = displayFactory.getImage(bodyItem);
        Bitmap expression = displayFactory.getImage(expressionItem);

        Bitmap rotatedHead = rotateImage(head, matrix);
        Bitmap rotatedBody = rotateImage(body, matrix);
        Bitmap rotatedExpression = rotateImage(expression, matrix);

        float yPositionPic = yPosition - (rotatedBody.getHeight() / 2);
        float xPositionPic = xPosition - (rotatedBody.getWidth() / 2);

        canvas.drawBitmap(rotatedHead, xPositionPic, yPositionPic, null);
        canvas.drawBitmap(rotatedBody, xPositionPic, yPositionPic, null);
        canvas.drawBitmap(rotatedExpression, xPositionPic, yPositionPic, null);
    }

    private Bitmap rotateImage(Bitmap bitmap, Matrix matrix){
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

}
