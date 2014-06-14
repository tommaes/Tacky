package com.nextgen.tackyTests.unitTests.db;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.db.localDB.LocalTacky_DB;
import com.nextgen.tacky.display.Expression;
import com.nextgen.tacky.display.TackyBody;
import com.nextgen.tacky.display.TackyExpression;
import com.nextgen.tacky.display.TackyHead;

import java.util.ArrayList;

/**
 * Created by maes on 6/06/14.
 */
public class LocalTacky_DBTest extends AndroidTestCase {

    private LocalTacky_DB localTacky_db;

    // Room info
    private String name = "name";
    private String visual = "visual";
    Room.RoomType type = Room.RoomType.MAIN;

    // Tacky info
    private final String tackyName = "testTacky";
    private final int headId = 1;
    private final int bodyId = 2;
    private final int expressionId = 3;

    // Visualisation
    TackyHead tackyHead = new TackyHead("normal", "sleep", "up", "down", headId);
    TackyBody tackyBody = new TackyBody("body", bodyId);
    Expression front = new Expression("frontHappy", "frontNormal", "frontSad");
    Expression side = new Expression("sideHappy", "sideNormal", "sideSad");
    Expression sleep = new Expression("sleep");
    TackyExpression tackyExpression = new TackyExpression(front, side, sleep, expressionId);

    public void setUp(){
        RenamingDelegatingContext renamingDelegatingContext = new RenamingDelegatingContext(getContext(), "test_DB_");
        localTacky_db = new LocalTacky_DB(renamingDelegatingContext);
    }

    public void testAddTackyAndGetTacky() throws Exception {

        localTacky_db.initializeTacky(tackyName, tackyHead, tackyBody, tackyExpression);

        Tacky tacky = localTacky_db.getTacky(tackyName);

        assertTrue(tacky.isAlive());
        assertEquals(headId, tacky.getHeadId());
        assertEquals(bodyId, tacky.getBodyId());
        assertEquals(expressionId, tacky.getExpressionId());
    }

    public void testAddTackyAndGetTackyNames() throws Exception {

        localTacky_db.initializeTacky(tackyName, tackyHead, tackyBody, tackyExpression);

        ArrayList<String> names = localTacky_db.getTackyNames();

        boolean isAdded = false;

        for(String name : names){
            if(name.equals(tackyName)){
                isAdded = true;
                break;
            }
        }

        assertTrue(isAdded);
    }

    public void testAddTackyAndDeleteTacky() throws Exception {

        localTacky_db.initializeTacky(tackyName, tackyHead, tackyBody, tackyExpression);

        assertNotNull(localTacky_db.getTacky(tackyName));

        localTacky_db.deleteTacky(tackyName);

        assertNull(localTacky_db.getTacky(tackyName));
    }

}
