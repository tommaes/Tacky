package com.nextgen.tacky.threads;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.LocalDatabase;

/**
 * Created by maes on 3/11/13.
 */
public class TackyThread implements Runnable {

    private Tacky tacky;
    private LocalDatabase db;
    private Thread thread;
    private boolean keepRunning = true;


    public TackyThread(Tacky t, LocalDatabase db){
        tacky = t;
        this.db = db;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(tacky.isAlive() && keepRunning){
            tacky.calculateEnergy();
            tacky.calculateSatisfaction();
            tacky.calculateHappiness();

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
