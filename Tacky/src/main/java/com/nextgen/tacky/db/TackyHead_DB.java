package com.nextgen.tacky.db;

import android.content.Context;

import com.nextgen.tacky.db.localDB.LocalTackyHead_DB;
import com.nextgen.tacky.display.TackyHead;

import java.util.ArrayList;

/**
 * Created by maes on 14/06/14.
 */
public class TackyHead_DB {

    private LocalTackyHead_DB localTackyHead_db;

    public TackyHead_DB(Context context){
        localTackyHead_db = new LocalTackyHead_DB(context);
    }

    public ArrayList<TackyHead> getHeads() {
        return localTackyHead_db.getHeads();
    }

    public TackyHead getHead(int id) {
        return localTackyHead_db.getHead(id);
    }
}
