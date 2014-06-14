package com.nextgen.tacky.db;

import android.content.Context;

import com.nextgen.tacky.db.localDB.LocalTackyExpression_DB;
import com.nextgen.tacky.display.TackyExpression;

import java.util.ArrayList;

/**
 * Created by maes on 14/06/14.
 */
public class TackyExpression_DB {

    private LocalTackyExpression_DB localTackyExpression_db;

    public TackyExpression_DB(Context context){
        localTackyExpression_db = new LocalTackyExpression_DB(context);
    }

    public ArrayList<TackyExpression> getExpressions() {
        return localTackyExpression_db.getExpressions();
    }

    public TackyExpression getExpression(int id) {
        return localTackyExpression_db.getExpression(id);
    }
}
