package com.nextgen.tacky.activities.rooms;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nextgen.tacky.R;
import com.nextgen.tacky.db.localDB.LocalRoom_DB;
import com.nextgen.tacky.db.localDB.LocalTacky_DB;

import java.util.ArrayList;

/**
 * Created by maes on 12/11/13.
 */
public class Chamber extends MainRoom {

    private static final int MENU_CHANGE_BACKGROUND_ROOM = MENU_MAINTACKY_LAST;
    public static final int MENU_CHAMBER_LAST = MENU_CHANGE_BACKGROUND_ROOM + 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.add(0, MENU_CHANGE_BACKGROUND_ROOM, Menu.NONE, "Change Background");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case MENU_CHANGE_BACKGROUND_ROOM : {
                changeBackgroundRoom();
                break;
            }
            default:{
                super.onOptionsItemSelected(menuItem);
            }
        }
        return true;
    }

    public void changeBackgroundRoom(){
        final Dialog dialog = new Dialog(this);
        final LocalRoom_DB localRoom_db = new LocalRoom_DB(this);
        final LocalTacky_DB localTacky_db = new LocalTacky_DB(this);
        dialog.setContentView(R.layout.activity_display_stuff);
        dialog.setTitle("Change background room.");

        LinearLayout l = (LinearLayout) dialog.findViewById(R.id.showStuff);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ArrayList<String> backgrounds = localRoom_db.getBackgrounds();

        for(final String background : backgrounds){
            ImageView i = new ImageView(this);
            String k = getApplicationContext().getPackageName();
            int imageResource = getResources().getIdentifier(background, "drawable", k);
            i.setBackgroundResource(imageResource);
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tacky.setRoomVisualization(background);
                    localTacky_db.updateTacky(tacky);
                    localRoom_db.storeRoom(tacky);
                    dialog.dismiss();
                }
            });
            l.addView(i, imageParams);
        }
        dialog.show();
    }

    public void onDestroy(){
        super.onDestroy();
    }
}
