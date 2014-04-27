package com.nextgen.tacky.basic.observer;

import java.util.*;

/**
 * Created by maes on 27/04/14.
 */
public interface Observer {

    void update(Observable observable);
    void update(Observable observable, Object arg);

}
