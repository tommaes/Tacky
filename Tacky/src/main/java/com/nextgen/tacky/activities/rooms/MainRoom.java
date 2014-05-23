package com.nextgen.tacky.activities.rooms;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nextgen.tacky.R;
import com.nextgen.tacky.activities.MainTackySurface;
import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.LocalDatabase;
import com.nextgen.tacky.db.localDB.Food_DB;
import com.nextgen.tacky.db.localDB.Room_DB;
import com.nextgen.tacky.db.localDB.Tacky_DB;
import com.nextgen.tacky.threads.TackyThread;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by maes on 28/10/13.
 */
public class MainRoom extends Activity {

    protected Tacky tacky;
    protected LocalDatabase db;
    public static String MAIN_TACKY_PACKAGE;
    protected MainTackySurface mainTackySurface;
    private TackyThread tackyThread;

    private NfcAdapter nfcAdapter;
    private PendingIntent nfcPendingIntent;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tacky);
        Intent i = getIntent();
        this.tacky = i.getParcelableExtra(Tacky.TACKY);
        this.db = new LocalDatabase(this);
        SurfaceView surf = (SurfaceView) findViewById(R.id.roomView);
        SurfaceHolder sh = surf.getHolder();
        MAIN_TACKY_PACKAGE = getApplicationContext().getPackageName();
        this.mainTackySurface = new MainTackySurface(this, this, sh, tacky);
        tackyThread = new TackyThread(tacky, new Tacky_DB(this));

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private static final int MENU_CHARACTERISTICS = Menu.FIRST;
    private static final int MENU_CHANGE_ROOM = MENU_CHARACTERISTICS + 1;
    public static final int MENU_MAINTACKY_LAST = MENU_CHANGE_ROOM + 1;

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        menu.clear();
        menu.add(0, MENU_CHARACTERISTICS, Menu.NONE,"Show Characteristics");
        menu.add(0, MENU_CHANGE_ROOM, Menu.NONE,"Change Room");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case MENU_CHARACTERISTICS : {
                showCharacteristics();
                break;
            }
            case MENU_CHANGE_ROOM : {
                changeRoom();
                break;
            }
        }
        return true;
    }

    public void onBackPressed() {
        Tacky_DB tacky_db = new Tacky_DB(this);
        tacky_db.updateTackyWithoutRoom(tacky);
        super.onBackPressed();
    }

    public void showCharacteristics(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_display_characteristics);
        dialog.setTitle("Characteristics");

        ProgressBar happiness = (ProgressBar) dialog.findViewById(R.id.progressHappiness);
        ProgressBar energy = (ProgressBar) dialog.findViewById(R.id.progressEnergy);
        ProgressBar satisfied = (ProgressBar) dialog.findViewById(R.id.progressSatisfied);

        happiness.setMax((int) tacky.getMaxHappinessLevel());
        energy.setMax((int) tacky.getMaxEnergyLevel());
        satisfied.setMax((int) tacky.getMaxSatisfiedLevel());

        happiness.setProgress((int) tacky.getHappinessLevel());
        energy.setProgress((int) tacky.getEnergyLevel());
        satisfied.setProgress((int) tacky.getSatisfiedLevel());

        dialog.show();
    }

    public void changeRoom(){
        final Dialog dialog = new Dialog(this);
        final Room_DB room_db = new Room_DB(this);
        final Tacky_DB tacky_db = new Tacky_DB(this);
        dialog.setContentView(R.layout.activity_display_stuff);
        dialog.setTitle("Rooms");

        ArrayList<Room> rooms = room_db.getRooms(this.tacky);

        final Context mainTacky = this;

        LinearLayout l = (LinearLayout) dialog.findViewById(R.id.showStuff);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageParams.setMargins(10, 0, 10, 0);
        for(final Room r : rooms){
            Button button = new Button(this);
            String k = getApplicationContext().getPackageName();
            int imageResource = getResources().getIdentifier(r.getVisualization(), "drawable", k);
            button.setBackgroundResource(imageResource);
            button.setText(r.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tacky.setCurrentRoom(r);
                    tacky_db.updateTacky(tacky);
                    Intent intent = RoomSwitch.roomSwitch(mainTacky, tacky);
                    dialog.dismiss();
                    startActivity(intent);
                    finish();
                }
            });
            l.addView(button, imageParams);
        }
        dialog.show();
    }

    public void onDestroy(){
        Tacky_DB tacky_db = new Tacky_DB(this);
        tackyThread.stopRunning();
        tacky_db.updateTackyWithoutRoom(this.tacky);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableNfcAdapterDispatch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableNfcAdapterDispatch();
    }


    public void newFood(final Food f){
        final Dialog dialog = new Dialog(this);
        final Food_DB food_db = new Food_DB(this);
        dialog.setContentView(R.layout.activity_new_food);
        dialog.setTitle("New food discovered.");
        dialog.show();

        Button buttonView = (Button) dialog.findViewById(R.id.foodView);
        Button buttonAdd = (Button) dialog.findViewById(R.id.addFood);


        String k = getApplicationContext().getPackageName();
        int imageResource = getResources().getIdentifier(f.getVisualization(), "drawable", k);
        buttonView.setBackgroundResource(imageResource);
        buttonView.setText(f.getName());

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food_db.storeFood(f);
                dialog.dismiss();
            }
        });

    }

    // ---------- NFC ------------------


    @Override
    protected void onNewIntent(Intent intent) {
        // For NFC functionality
        handleIntent(intent);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void handleIntent(Intent intent) {
        String action = intent.getAction();

        if (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            // Probably unformatted tag?
        } else if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;

            if (rawMsgs == null) {
                // no NdefMessages
            } else {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                    Log.i("NFC", i + " " + Arrays.toString(msgs[i].toByteArray()));
                    for (NdefRecord rec : msgs[i].getRecords()) {

                        String typeStr = new String(rec.getType());
                        if (typeStr.equals("com.nextgen.tacky:food")) {
                            // Reading Food object from NFC
                            byte[] data = rec.getPayload(); // byte data from NFC tag

                            Parcel p = Parcel.obtain();
                            p.unmarshall(data, 0, data.length); // force byte data from NFC into Parcel
                            p.setDataPosition(0); // crucial!
                            Food f = Food.CREATOR.createFromParcel(p);
                            p.recycle();

                            Log.i("NFC", "name " + f.getName());
                            Log.i("NFC", "vis " + f.getVisualization());
                            Log.i("NFC", "energy " + f.getEnergyValue());
                            Log.i("NFC", "uses " + f.getTotalUses());
                            newFood(f);
                        }
                    }
                }
            }

        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void enableNfcAdapterDispatch() {
        if (nfcAdapter != null) nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, null, null);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void disableNfcAdapterDispatch() {
        if (nfcAdapter != null) nfcAdapter.disableForegroundDispatch(this);
    }
}
