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
import com.nextgen.tacky.db.LocalDatabase;

/**
 * Created by maes on 2/11/13.
 */
public class LoadSavedTackys extends Activity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_saved_tackys);
        LinearLayout l = (LinearLayout) findViewById(R.id.savedTackys);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LocalDatabase db = new LocalDatabase(this);
        String[] names = db.getTackyNames();
        final Context context = this;
        for(final String name : names){
            TextView text = new TextView(this);
            text.setText(name);
            text.setTextSize(50);
            text.setTextColor(Color.parseColor("#ffffff"));
            text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tacky tacky = db.getTacky(name);
                        Intent intent = new RoomSwitch().roomSwitch(context, tacky);
                        startActivity(intent);
                        finish();
                    }
                });
                l.addView(text, imageParams);
        }
        if(names.length == 0){
            TextView text = new TextView(this);
            text.setText("There are no Tackys created yet.");
            text.setTextSize(50);
            text.setTextColor(Color.parseColor("#ffffff"));
            l.addView(text, imageParams);
        }
    }
}
