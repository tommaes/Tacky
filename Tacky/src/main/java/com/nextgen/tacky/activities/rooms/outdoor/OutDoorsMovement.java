package com.nextgen.tacky.activities.rooms.outdoor;

import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Created by maes on 8/12/13.
 */
public class OutDoorsMovement extends Outdoors {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public boolean onTouchEvent(MotionEvent event){

        switch (event.getAction()) {
            // Start of press gesture
            case MotionEvent.ACTION_DOWN: {
                mainTackySurface.moveTacky(event.getX(), event.getY());
                break;
            }
            // Movement
            case MotionEvent.ACTION_MOVE: {
                mainTackySurface.moveTacky(event.getX(), event.getY());
                break;
            }
            // Press gesture has ended
            case MotionEvent.ACTION_UP: {
                mainTackySurface.moveTacky(event.getX(), event.getY());
                break;
            }
        }
        tacky.move();
        return true;
    }


}
