package com.nextgen.tacky.activities.rooms.indoor;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nextgen.tacky.R;
import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.db.Food_DB;

import java.util.ArrayList;

/**
 * Created by maes on 12/11/13.
 */
public class Kitchen extends Chamber {

    private static final int MENU_FEED = MENU_CHAMBER_LAST;
    public static final int MENU_KITCHEN_LAST = MENU_FEED + 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.add(0, MENU_FEED, Menu.NONE, "Feed");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case MENU_FEED : {
                showFood();
                break;
            }
            default:{
                super.onOptionsItemSelected(menuItem);
            }
        }
        return true;
    }

    public void showFood(){
        final Dialog dialog = new Dialog(this);
        final Food_DB food_db = new Food_DB(this);
        dialog.setContentView(R.layout.activity_display_stuff);
        dialog.setTitle("Food");

        ArrayList<Food> foods = food_db.getFoods();

        LinearLayout l = (LinearLayout) dialog.findViewById(R.id.showStuff);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for(final Food f : foods){
            ImageView i = new ImageView(this);
            String k = getApplicationContext().getPackageName();
            int imageResource = getResources().getIdentifier(f.getVisualization(), "drawable", k);
            i.setBackgroundResource(imageResource);
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tacky.addSatisfiedState(f);
                    if(!f.canStillBeUsed())
                        food_db.deleteFood(f);
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
