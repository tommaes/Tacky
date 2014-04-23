package com.nextgen.tacky.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nextgen.tacky.R;
import com.nextgen.tacky.db.LocalDatabase;

/**
 * Created by maes on 2/11/13.
 */
public class DeleteTacky extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_saved_tackys);
        LinearLayout l = (LinearLayout) findViewById(R.id.savedTackys);
        displayTackys(l);

    }

    public void displayTackys(final LinearLayout l){
        l.removeAllViews();
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LocalDatabase db = new LocalDatabase(this);
        String[] names = db.getTackyNames();
        for(final String name : names){
            TextView text = new TextView(this);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            text.setText(name);
            text.setTextSize(50);
            text.setTextColor(Color.parseColor("#ffffff"));
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage("Are you sure?");
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteTacky(name);
                            dialog.cancel();
                            displayTackys(l);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            l.addView(text, imageParams);
        }
        if(names.length == 0){
            TextView text = new TextView(this);
            text.setText("There are no Tackys available to delete.");
            text.setTextSize(50);
            text.setTextColor(Color.parseColor("#ffffff"));
            l.addView(text, imageParams);
        }
        l.refreshDrawableState();
    }
}
