package com.nextgen.tacky.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nextgen.tacky.R;
import com.nextgen.tacky.activities.rooms.RoomSwitch;
import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;
import com.nextgen.tacky.db.LocalDatabase;
import com.nextgen.tacky.display.TackyBody;
import com.nextgen.tacky.display.TackyExpression;
import com.nextgen.tacky.display.TackyHead;

/**
 * Created by maes on 27/10/13.
 */
public class CreateTacky extends Activity {

    private TackyHead head = null;
    private TackyBody body = null;
    private TackyExpression expression = null;

    private ImageView imageView;
    private LocalDatabase db;

    private static final int MENU_HEAD = Menu.FIRST;
    private static final int MENU_BODY = MENU_HEAD + 1;
    private static final int MENU_EXPRESSION = MENU_BODY + 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tacky);
        this.db = new LocalDatabase(this);
        imageView = (ImageView) findViewById(R.id.displayTacky);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        menu.clear();
        menu.add(0, MENU_HEAD, Menu.NONE,"Show Heads");
        menu.add(0, MENU_BODY, Menu.NONE,"Show Bodies");
        menu.add(0, MENU_EXPRESSION, Menu.NONE,"Show Expressions");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case MENU_HEAD : {
                choseHead();
                break;
            }
            case MENU_BODY : {
                choseBody();
                break;
            }
            case MENU_EXPRESSION : {
                choseExpression();
                break;
            }
        }
        return true;
    }

    private void choseHead(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_display_stuff);
        dialog.setTitle("Choose a head for your Tacky");

        TackyHead[] heads = db.getHeads();

        LinearLayout l = (LinearLayout) dialog.findViewById(R.id.showStuff);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.setMargins(10, 0, 10, 0);
        for(final TackyHead h : heads){
            ImageView i = new ImageView(this);
            i.setImageBitmap(h.getFront(db));
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    head = h;
                    Bitmap bitmap = h.getFront(db).copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(bitmap);
                    imageView.draw(canvas);
                    imageView.setImageBitmap(bitmap);
                }
            });
            l.addView(i, imageParams);
        }
        dialog.show();
    }

    private void choseBody(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_display_stuff);
        dialog.setTitle("Choose a body for your Tacky");

        TackyBody[] bodies = db.getBodies();

        LinearLayout l = (LinearLayout) dialog.findViewById(R.id.showStuff);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.setMargins(10, 0, 10, 0);
        for(final TackyBody b : bodies){
            ImageView i = new ImageView(this);
            i.setImageBitmap(b.getFront(db));
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    body = b;
                    Bitmap bitmap = b.getFront(db).copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(bitmap);
                    imageView.draw(canvas);
                    imageView.setImageBitmap(bitmap);
                }
            });
            l.addView(i, imageParams);
        }
        dialog.show();
    }

    private void choseExpression(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_display_stuff);
        dialog.setTitle("Choose an expression for your Tacky");

        TackyExpression[] expressions = db.getExpressions();

        LinearLayout l = (LinearLayout) dialog.findViewById(R.id.showStuff);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.setMargins(10, 0, 10, 0);
        for(final TackyExpression e : expressions){
            ImageView i = new ImageView(this);
            i.setImageBitmap(e.getFront(db, TackyState.TackyStatus.NORMAL, MoodState.MoodValue.HAPPY));
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    expression = e;
                    Bitmap eb = e.getFront(db, TackyState.TackyStatus.NORMAL, MoodState.MoodValue.HAPPY).copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap bitmap = Bitmap.createBitmap(eb.getWidth(), eb.getHeight(),Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    imageView.draw(canvas);
                    canvas.drawBitmap(eb, 0, 0, null);
                    imageView.setImageBitmap(bitmap);
                }
            });
            l.addView(i, imageParams);
        }
        dialog.show();
    }

    public void createTacky(View view) {
        EditText editText = (EditText) findViewById(R.id.nameTacky);
        String name = editText.getText().toString();
        if (!name.equals("")) {
            LocalDatabase db = new LocalDatabase(this);
            Tacky tacky = db.getTacky(name);
            if(tacky == null){
                if(head != null && body != null && expression != null) {
                    tacky = db.initializeTacky(name, head, body, expression);
                    Intent intent = new RoomSwitch().roomSwitch(this, tacky);
                    startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Need to choose a head, body and expression.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Name is already taken.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        }
    }

}
