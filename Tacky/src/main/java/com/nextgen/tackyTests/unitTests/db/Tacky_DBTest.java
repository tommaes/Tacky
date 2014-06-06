package com.nextgen.tackyTests.unitTests.db;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.db.localDB.Tacky_DB;

/**
 * Created by maes on 6/06/14.
 */
public class Tacky_DBTest extends AndroidTestCase {

    private Tacky_DB tacky_db;

    // Room info
    private String name = "name";
    private String visual = "visual";
    Room.RoomType type = Room.RoomType.MAIN;

    // Tacky info
    private final String tackyName = "testTacky";
    private final int headId = 1;
    private final int bodyId = 2;
    private final int expressionId = 3;

    public void setUp(){
        RenamingDelegatingContext renamingDelegatingContext = new RenamingDelegatingContext(getContext(), "test_DB_");
        tacky_db = new Tacky_DB(renamingDelegatingContext);
    }



}
