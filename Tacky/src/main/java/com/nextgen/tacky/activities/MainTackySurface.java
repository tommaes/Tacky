package com.nextgen.tacky.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nextgen.tacky.activities.rooms.MainRoom;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basicDisplay.RoomDisplay;
import com.nextgen.tacky.basicDisplay.TackyDisplay;

/**
 * Created by maes on 30/10/13.
 */
public class MainTackySurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Tacky tacky;
    private MainRoom parentActivity;
    private RoomDisplay roomDisplay;
    private TackyDisplay tackyDisplay;
    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private Boolean canDraw;

    public MainTackySurface(Context context, MainRoom p, SurfaceHolder surfaceHolder, Tacky t) {
        super(context);
        this.tacky = t;
        this.parentActivity = p;
        this.tackyDisplay = new TackyDisplay(context, this.tacky);
        this.roomDisplay = new RoomDisplay(context, this.tacky.getCurrentRoom());
        surfaceHolder.addCallback(this);
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.canDraw = true;
        thread = new Thread(this);
        thread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        synchronized (thread){
            this.surfaceHolder = holder;
            int middleWidth = width / 2;
            int middleHeight = height / 2;
            tackyDisplay.changeMiddle(middleWidth, middleHeight);
            thread.notify();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            synchronized (thread) {
                this.canDraw = false;
            }
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void endTackyDisplay(){
        try {
            this.canDraw = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void newRoom(Context context, Room room){
        this.roomDisplay.newRoom(context, room);
    }

    public void moveTacky(float x, float y){
        tackyDisplay.moveTacky(x, y);
    }

    private synchronized void redraw(){
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parentActivity.setTitle(tacky.getCurrentRoom().getName());
            }
        });
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.restore();
            roomDisplay.display(canvas, canvas.getWidth(), canvas.getHeight());
            tackyDisplay.display(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        synchronized (thread) {
            while(tacky.isAlive() && canDraw){
                   try {
                       redraw();
                       thread.wait(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
        }
        if(canDraw && !tacky.isAlive())
            redraw();
    }
}
