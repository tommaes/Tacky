package com.nextgen.tacky.sensor.gps;

import android.location.Location;

/**
 * Created by maes on 31/05/14.
 */
public interface OutdoorsCommand {

    public void onLocationChanged(Location location);

}
