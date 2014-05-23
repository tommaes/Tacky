package com.nextgen.tacky.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nextgen.tacky.activities.rooms.MainRoom;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.observer.Observable;
import com.nextgen.tacky.basic.observer.Observer;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basicDisplay.NILLDisplay;
import com.nextgen.tacky.basicDisplay.RoomDisplay;
import com.nextgen.tacky.basicDisplay.TackyDisplay;


/**
 * Created by maes on 30/10/13.
 */
public class MainTackySurface extends SurfaceView implements SurfaceHolder.Callback, Observer {

    private Tacky tacky;
    private MainRoom parentActivity;
    private TackyDisplay tackyDisplay;
    private SurfaceHolder surfaceHolder;

    public MainTackySurface(Context context, MainRoom p, SurfaceHolder surfaceHolder, Tacky t) {
        super(context);
        this.tacky = t;
        this.parentActivity = p;

        NILLDisplay nillDisplay = new NILLDisplay();
        RoomDisplay roomDisplay = new RoomDisplay(context, this.tacky, nillDisplay);
        this.tackyDisplay = new TackyDisplay(context, this.tacky, roomDisplay);


        surfaceHolder.addCallback(this);
        this.surfaceHolder = surfaceHolder;
        tacky.addObserver(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        this.surfaceHolder = holder;
        int middleWidth = width / 2;
        int middleHeight = height / 2;
        tackyDisplay.changeMiddle(middleWidth, middleHeight);
        redraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void moveTacky(float x, float y){
        tackyDisplay.moveTacky(x, y);
        redraw();
    }

    private synchronized void redraw(){
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parentActivity.setTitle(tacky.getRoomName());
            }
        });
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.restore();
            tackyDisplay.display(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    @Override
    public void update(Observable observable) {
        redraw();
    }

    @Override
    public void update(Observable observable, Object arg) {

    }
}
