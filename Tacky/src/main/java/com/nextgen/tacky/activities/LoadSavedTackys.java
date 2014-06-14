package com.nextgen.tacky.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextgen.tacky.R;
import com.nextgen.tacky.activities.rooms.RoomSwitch;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.LocalRoom_DB;
import com.nextgen.tacky.db.localDB.LocalTacky_DB;

import java.util.ArrayList;

/**
 * Created by maes on 2/11/13.
 */
public class LoadSavedTackys extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        final LocalTacky_DB localTacky_db = new LocalTacky_DB(this);
        final LocalRoom_DB localRoom_db = new LocalRoom_DB(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_saved_tackys);
        LinearLayout l = (LinearLayout) findViewById(R.id.savedTackys);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<String> names = localTacky_db.getTackyNames();
        final Context context = this;
        for(final String name : names){
            TextView text = new TextView(this);
            text.setText(name);
            text.setTextSize(50);
            text.setTextColor(Color.parseColor("#ffffff"));
            text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tacky tacky = localTacky_db.getTacky(name);
                        tacky.setCurrentRoom(localRoom_db.getRoom(tacky.getRoomName(), tacky.getName()));
                        Intent intent = new RoomSwitch().roomSwitch(context, tacky);
                        startActivity(intent);
                        finish();
                    }
                });
                l.addView(text, imageParams);
        }
        if(names.size() == 0){
            TextView text = new TextView(this);
            text.setText("There are no Tackys created yet.");
            text.setTextSize(50);
            text.setTextColor(Color.parseColor("#ffffff"));
            l.addView(text, imageParams);
        }
    }
}
