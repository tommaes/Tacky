package com.nextgen.tacky.activities.rooms;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nextgen.tacky.basic.Tacky;

/**
 * Created by maes on 13/11/13.
 */
public class Bedroom extends Chamber {

    private static final int MENU_SLEEP = MENU_CHAMBER_LAST;
    private static final int MENU_WAKE_UP = MENU_SLEEP + 1;
    public static final int MENU_BEDROOM_LAST = MENU_WAKE_UP + 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.add(0, MENU_SLEEP, Menu.NONE, "Sleep");
        menu.add(0, MENU_WAKE_UP, Menu.NONE, "Wake Up");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case MENU_SLEEP : {
                tacky.setCurrentStatus(Tacky.TackyStatus.SLEEPING);
                break;
            }
            case MENU_WAKE_UP : {
                tacky.setCurrentStatus(Tacky.TackyStatus.TRYTOSLEEP);
                break;
            }
            default:{
                super.onOptionsItemSelected(menuItem);
            }
        }
        return true;
    }

    public void onDestroy(){
        super.onDestroy();
    }

}
