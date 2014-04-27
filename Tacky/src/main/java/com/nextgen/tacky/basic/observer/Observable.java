package com.nextgen.tacky.basic.observer;

import java.util.ArrayList;

/**
 * Created by maes on 27/04/14.
 */
public class Observable {

    private ArrayList<Observer> observers;

    public Observable(){
        observers = new ArrayList<Observer>();
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public int countObservers(){
        return observers.size();
    }

    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(this);
        }
    }

    public void notifyObservers(Object arg){
        for(Observer observer : observers){
            observer.update(this, arg);
        }
    }

}
