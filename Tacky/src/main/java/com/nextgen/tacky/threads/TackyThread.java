package com.nextgen.tacky.threads;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.Tacky_DB;

/**
 * Created by maes on 3/11/13.
 */
public class TackyThread implements Runnable {

    private Tacky tacky;
    private Tacky_DB db;
    private Thread thread;
    private boolean keepRunning = true;


    public TackyThread(Tacky t, Tacky_DB db){
        tacky = t;
        this.db = db;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(tacky.isAlive() && keepRunning){
            tacky.calculateStates();

            db.updateTackyWithoutRoom(tacky);

            try {
                synchronized (thread) {
                    thread.wait(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    public void stopRunning() {
        keepRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
