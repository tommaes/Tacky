package com.nextgen.tacky.db.localDB;

import android.content.Context;

import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.display.TackyBody;
import com.nextgen.tacky.display.TackyDisplayObject;
import com.nextgen.tacky.display.TackyExpression;
import com.nextgen.tacky.display.TackyHead;

/**
 * Created by maes on 29/05/14.
 */
public class TackyDisplayObject_DB {

    private static TackyBody_DB tackyBody_db = null;
    private static TackyEventHead_DB tackyEventHead_db = null;
    private static TackyHead_DB tackyHead_db = null;
    private static TackyExpression_DB tackyExpression_db = null;

    public TackyDisplayObject_DB(Context ctx){
        if(tackyBody_db == null)
            tackyBody_db = new TackyBody_DB(ctx);
        if(tackyEventHead_db == null)
            tackyEventHead_db = new TackyEventHead_DB(ctx);
        if(tackyHead_db == null)
            tackyHead_db = new TackyHead_DB(ctx);
        if(tackyExpression_db == null)
            tackyExpression_db = new TackyExpression_DB(ctx);
    }

    public TackyDisplayObject getTackyDisplayObject(Tacky t){

        TackyHead head = tackyEventHead_db.getEventHead(t.getHeadId()); // check for special head first
        if (head == null) head = tackyHead_db.getHead(t.getHeadId()); // no special head, use normal head
        TackyBody body = tackyBody_db.getBody(t.getBodyId());
        TackyExpression expression = tackyExpression_db.getExpression(t.getExpressionId());

        return new TackyDisplayObject(head, body, expression);
    }

    public TackyBody_DB getTackyBody_db() {
        return tackyBody_db;
    }

    public TackyEventHead_DB getTackyEventHead_db() {
        return tackyEventHead_db;
    }

    public TackyHead_DB getTackyHead_db() {
        return tackyHead_db;
    }

    public TackyExpression_DB getTackyExpression_db() {
        return tackyExpression_db;
    }
}
