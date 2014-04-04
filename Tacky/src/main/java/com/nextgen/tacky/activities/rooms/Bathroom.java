package com.nextgen.tacky.activities.rooms;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by maes on 13/11/13.
 */
public class Bathroom extends Chamber {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        //menu.add(0, MENU_CHANGE_BACKGROUND_ROOM, Menu.NONE, "Change Background");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            /*case MENU_CHANGE_BACKGROUND_ROOM : {
                changeBackgroundRoom();
                break;
            }*/
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
