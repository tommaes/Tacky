package com.nextgen.tacky.activities.rooms.indoor;

import android.content.Context;
import android.content.Intent;

import com.nextgen.tacky.activities.rooms.MainRoom;
import com.nextgen.tacky.activities.rooms.outdoor.Outdoors;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;

/**
 * Created by maes on 11/11/13.
 */
public class RoomSwitch {

    public static Intent roomSwitch(Context context, Tacky tacky){
        Intent intent = null;
        switch (tacky.getRoomType()) {
            case BATHROOM: {
                intent = new Intent(context, Bathroom.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
            case BEDROOM: {
                intent = new Intent(context, Bedroom.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.TRYTOSLEEP);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
            case KITCHEN: {
                intent = new Intent(context, Kitchen.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
            case OUTDOORS: {
                intent = new Intent(context, Outdoors.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
            case MAIN: {
                intent = new Intent(context, MainRoom.class);
                tacky.setCurrentStatus(TackyState.TackyStatus.NORMAL);
                intent.putExtra(Tacky.TACKY, tacky);
                break;
            }
            default: {

            }
        }
        return intent;
    }

}
