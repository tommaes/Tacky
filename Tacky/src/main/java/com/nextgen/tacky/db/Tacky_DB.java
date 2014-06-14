package com.nextgen.tacky.db;

import android.content.Context;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.LocalTacky_DB;
import com.nextgen.tacky.display.TackyBody;
import com.nextgen.tacky.display.TackyExpression;
import com.nextgen.tacky.display.TackyHead;

import java.util.ArrayList;

/**
 * Created by maes on 14/06/14.
 */
public class Tacky_DB {

    private LocalTacky_DB localTacky_db;

    public Tacky_DB(Context context){
        localTacky_db = new LocalTacky_DB(context);
    }

    public ArrayList<String> getTackyNames() {
        return localTacky_db.getTackyNames();
    }

    public void deleteTacky(String name) {
        localTacky_db.deleteTacky(name);
    }

    public Tacky getTacky(String name) {
        return localTacky_db.getTacky(name);
    }

    public Tacky initializeTacky(String name, TackyHead head, TackyBody body, TackyExpression expression) {
        return localTacky_db.initializeTacky(name, head, body, expression);
    }

    public void updateTacky(Tacky t) {
        localTacky_db.updateTacky(t);
    }

    public void updateTackyWithoutRoom(Tacky t) {
        localTacky_db.updateTackyWithoutRoom(t);
    }
}
