package com.nextgen.tacky.activities.rooms.outdoor;

import android.content.Context;
import android.content.Intent;

import com.nextgen.tacky.basic.TackyLocation;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;

/**
 * Created by maes on 16/06/14.
 */
public class RoomSwitch {

    public static Intent roomSwitch(Context context, Tacky tacky, TackyLocation tackyLocation){

        Intent intent = null;

        switch(tackyLocation.getOutdoorsType()){
            case PARK:{
                intent = new Intent(context, Park.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
            case RESTAURANT: {
                intent = new Intent(context, Restaurant.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
        }

        return intent;

    }

}
