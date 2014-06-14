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
public class LocalTackyDisplayObject_DB {

    private static LocalTackyBody_DB localTackyBody_db = null;
    private static LocalTackyEventHead_DB localTackyEventHead_db = null;
    private static LocalTackyHead_DB localTackyHead_db = null;
    private static LocalTackyExpression_DB localTackyExpression_db = null;

    public LocalTackyDisplayObject_DB(Context ctx){
        if(localTackyBody_db == null)
            localTackyBody_db = new LocalTackyBody_DB(ctx);
        if(localTackyEventHead_db == null)
            localTackyEventHead_db = new LocalTackyEventHead_DB(ctx);
        if(localTackyHead_db == null)
            localTackyHead_db = new LocalTackyHead_DB(ctx);
        if(localTackyExpression_db == null)
            localTackyExpression_db = new LocalTackyExpression_DB(ctx);
    }

    public TackyDisplayObject getTackyDisplayObject(Tacky t){

        TackyHead head = localTackyEventHead_db.getEventHead(t.getHeadId()); // check for special head first
        if (head == null) head = localTackyHead_db.getHead(t.getHeadId()); // no special head, use normal head
        TackyBody body = localTackyBody_db.getBody(t.getBodyId());
        TackyExpression expression = localTackyExpression_db.getExpression(t.getExpressionId());

        return new TackyDisplayObject(head, body, expression);
    }
}
