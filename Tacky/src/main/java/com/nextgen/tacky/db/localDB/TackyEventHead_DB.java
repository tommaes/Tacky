package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.format.Time;

import com.nextgen.tacky.db.generic.ReadCommand;
import com.nextgen.tacky.db.generic.StoreCommand;
import com.nextgen.tacky.display.TackyHead;

/**
 * Created by maes on 29/05/14.
 */
public class TackyEventHead_DB {

    private static LocalDatabase db = null;

    private static final String EVENT_HEAD_TABLE = "eventHeads";

    private static final String EVENT_HEAD_ID = "id";
    private static final String EVENT_HEAD_BONNET = "bonnet";
    private static final String EVENT_HEAD_NORMAL = "normal";
    private static final String EVENT_HEAD_UP = "up";
    private static final String EVENT_HEAD_DOWN = "down";
    private static final String EVENT_START = "start";
    private static final String EVENT_END = "end";

    private static final String EVENT_HEAD_SQL_CREATE = "CREATE TABLE " + EVENT_HEAD_TABLE + " (" +
            EVENT_HEAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EVENT_HEAD_BONNET + " TEXT," +
            EVENT_HEAD_NORMAL + " TEXT," +
            EVENT_HEAD_UP + " TEXT," +
            EVENT_HEAD_DOWN + " TEXT," +
            EVENT_START + " DATE," +
            EVENT_END + " DATE)";

    private static final String EVENT_HEAD_SQL_SELECT_HEAD = "SELECT * FROM " + EVENT_HEAD_TABLE +
                                                             " WHERE " + EVENT_START + " <= '%s' AND " + EVENT_END + " >= '%s'";

    public TackyEventHead_DB(Context ctx){
        if(db == null)
            db = LocalDatabase.getDatabase(ctx);
    }

    public static void initializeTable(LocalDatabase db) {
        db.addTable(EVENT_HEAD_SQL_CREATE);
        final Time xmasStart = new Time();
        xmasStart.year = 2013;
        xmasStart.month = 12 - 1; // zero-based
        xmasStart.monthDay = 21;
        xmasStart.hour = xmasStart.minute = xmasStart.second = 0;
        xmasStart.allDay = true;
        final Time xmasEnd = new Time(xmasStart);
        xmasEnd.monthDay = 28;
        db.insertValue(EVENT_HEAD_TABLE, null, new StoreCommand<TackyHead, ContentValues>() {
            @Override
            public ContentValues storeItem(TackyHead item) {
                ContentValues cv = new ContentValues();
                cv.put(EVENT_HEAD_BONNET, "kerstmuts_normal");
                cv.put(EVENT_HEAD_NORMAL, "kerstmuts_normal");
                cv.put(EVENT_HEAD_UP, "kerstmuts_up");
                cv.put(EVENT_HEAD_DOWN, "kerstmuts_down");
                cv.put(EVENT_START, xmasStart.toMillis(false));
                cv.put(EVENT_END, xmasEnd.toMillis(false));

                return cv;
            }
        });

        final Time easterStart = new Time(xmasStart);
        easterStart.year = 2014;
        easterStart.month = 4 - 1; // zero-based
        easterStart.monthDay = 13;
        easterStart.allDay = true;
        final Time easterEnd = new Time(easterStart);
        easterEnd.monthDay = 24;
        db.insertValue(EVENT_HEAD_TABLE, null, new StoreCommand<TackyHead, ContentValues>() {
            @Override
            public ContentValues storeItem(TackyHead item) {
                ContentValues cv = new ContentValues();
                cv.put(EVENT_HEAD_BONNET, "pasen_normal");
                cv.put(EVENT_HEAD_NORMAL, "pasen_normal");
                cv.put(EVENT_HEAD_UP, "pasen_up");
                cv.put(EVENT_HEAD_DOWN, "pasen_down");
                cv.put(EVENT_START, easterStart.toMillis(false));
                cv.put(EVENT_END, easterEnd.toMillis(false));

                return cv;
            }
        });
    }

    public TackyHead getEventHead(final int id)  {
        Time now = new Time();
        now.setToNow();
        now.hour = now.minute = now.second = 0;
        now.allDay = true;
        String nowstr = Long.toString(now.toMillis(false));
        String query = String.format(EVENT_HEAD_SQL_SELECT_HEAD, nowstr, nowstr);
        return db.readValue(query, new ReadCommand<TackyHead, Cursor>() {
            @Override
            public TackyHead readItem(Cursor cursor) {
                String bonnet = cursor.getString(cursor.getColumnIndex(EVENT_HEAD_BONNET));
                String normal = cursor.getString(cursor.getColumnIndex(EVENT_HEAD_NORMAL));
                String up = cursor.getString(cursor.getColumnIndex(EVENT_HEAD_UP));
                String down = cursor.getString(cursor.getColumnIndex(EVENT_HEAD_DOWN));
                return new TackyHead(normal, bonnet, up, down, id);
            }
        });
    }

    public static void dropTable(LocalDatabase db, String dropTable) {
        db.dropTable(dropTable + EVENT_HEAD_TABLE);
    }
}
