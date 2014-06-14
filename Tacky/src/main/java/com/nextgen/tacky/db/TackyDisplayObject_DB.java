package com.nextgen.tacky.db;

import android.content.Context;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.LocalTackyBody_DB;
import com.nextgen.tacky.db.localDB.LocalTackyDisplayObject_DB;
import com.nextgen.tacky.db.localDB.LocalTackyEventHead_DB;
import com.nextgen.tacky.db.localDB.LocalTackyExpression_DB;
import com.nextgen.tacky.db.localDB.LocalTackyHead_DB;
import com.nextgen.tacky.display.TackyDisplayObject;

/**
 * Created by maes on 14/06/14.
 */
public class TackyDisplayObject_DB {

    private LocalTackyDisplayObject_DB localTackyDisplayObject_db;

    public TackyDisplayObject_DB(Context context){
        localTackyDisplayObject_db = new LocalTackyDisplayObject_DB(context);
    }

    public TackyDisplayObject getTackyDisplayObject(Tacky t) {
        return localTackyDisplayObject_db.getTackyDisplayObject(t);
    }
}
