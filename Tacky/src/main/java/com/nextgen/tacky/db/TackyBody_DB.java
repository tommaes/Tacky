package com.nextgen.tacky.db;

import android.content.Context;
import android.graphics.Bitmap;

import com.nextgen.tacky.db.localDB.LocalTackyBody_DB;
import com.nextgen.tacky.display.TackyBody;

import java.util.ArrayList;

/**
 * Created by maes on 14/06/14.
 */
public class TackyBody_DB {

    private LocalTackyBody_DB localTackyBody_db;

    public TackyBody_DB(Context context){
        localTackyBody_db = new LocalTackyBody_DB(context);
    }

    public ArrayList<TackyBody> getBodies() {
        return localTackyBody_db.getBodies();
    }
}
