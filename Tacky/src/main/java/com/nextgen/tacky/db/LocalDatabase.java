package com.nextgen.tacky.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.text.format.Time;

import com.nextgen.tacky.activities.rooms.Outdoors;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.basic.State.State;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;
import com.nextgen.tacky.db.localDB.Food_DB;
import com.nextgen.tacky.db.localDB.ReadCommand;
import com.nextgen.tacky.db.localDB.Room_DB;
import com.nextgen.tacky.db.localDB.StoreCommand;
import com.nextgen.tacky.db.localDB.TackyHead_DB;
import com.nextgen.tacky.db.localDB.Tacky_DB;
import com.nextgen.tacky.display.TackyBody;
import com.nextgen.tacky.display.TackyDisplayObject;
import com.nextgen.tacky.display.TackyExpression;
import com.nextgen.tacky.display.TackyHead;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bram on 31/10/13.
 */
public class LocalDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "tackyDB";
    private static final int DB_VERSION = 14;
    private static LocalDatabase db = null;

    private Context context;


    private static final String TACKY_TABLE = "tackies";
    private static final String FOOD_TABLE = "foods";
    private static final String ROOM_TABLE = "rooms";

    private static final String HEAD_TABLE = "heads";
    private static final String BODY_TABLE = "bodies";
    private static final String EXPRESSION_TABLE = "expressions";
    private static final String EVENT_HEAD_TABLE = "eventHeads";

    private static final String HEAD_ID = "id";
    private static final String HEAD_BONNET = "bonnet";
    private static final String HEAD_NORMAL = "normal";
    private static final String HEAD_UP = "up";
    private static final String HEAD_DOWN = "down";

    private static final String BODY_ID = "id";
    private static final String BODY_VISUAL = "visualization";

    private static final String EXPRESSION_ID = "id";
    private static final String EXPRESSION_FRONT_HAPPY = "fronthappy";
    private static final String EXPRESSION_FRONT_NORMAL = "frontnormal";
    private static final String EXPRESSION_FRONT_UNHAPPY = "frontunhappy";
    private static final String EXPRESSION_SIDE_HAPPY = "sidehappy";
    private static final String EXPRESSION_SIDE_NORMAL = "sidenormal";
    private static final String EXPRESSION_SIDE_UNHAPPY = "sideunhappy";
    private static final String EXPRESSION_SLEEP = "sleep";
    private static final String EXPRESSION_SLEEP_SMILE = "sleepsmile";

    private static final String EVENT_HEAD_ID = "id";
    private static final String EVENT_HEAD_BONNET = "bonnet";
    private static final String EVENT_HEAD_NORMAL = "normal";
    private static final String EVENT_HEAD_UP = "up";
    private static final String EVENT_HEAD_DOWN = "down";
    private static final String EVENT_START = "start";
    private static final String EVENT_END = "end";

    /*
    private static final String LOCATION_ID = "id";
    private static final String LOCATION_NAME = "name";
    private static final String LOCATION_LAT = "lat";
    private static final String LOCATION_LONG = "long";
    private static final String LOCATION_RADIUS = "radius";
    private static final String LOCATION_TYPE = "type";
    */

    private static final String HEAD_SQL_CREATE = "CREATE TABLE " + HEAD_TABLE + " (" +
            HEAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            HEAD_BONNET + " TEXT," +
            HEAD_NORMAL + " TEXT," +
            HEAD_UP + " TEXT," +
            HEAD_DOWN + " TEXT)";
    private static final String BODY_SQL_CREATE = "CREATE TABLE " + BODY_TABLE + " (" +
            BODY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BODY_VISUAL + " TEXT)";
    private static final String EXPRESSION_SQL_CREATE = "CREATE TABLE " + EXPRESSION_TABLE + " (" +
            EXPRESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EXPRESSION_FRONT_HAPPY + " TEXT," +
            EXPRESSION_FRONT_NORMAL + " TEXT," +
            EXPRESSION_FRONT_UNHAPPY + " TEXT," +
            EXPRESSION_SIDE_HAPPY + " TEXT," +
            EXPRESSION_SIDE_NORMAL + " TEXT," +
            EXPRESSION_SIDE_UNHAPPY + " TEXT," +
            EXPRESSION_SLEEP + " TEXT," +
            EXPRESSION_SLEEP_SMILE + " TEXT)";

    private static final String EVENT_HEAD_SQL_CREATE = "CREATE TABLE " + EVENT_HEAD_TABLE + " (" +
            EVENT_HEAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EVENT_HEAD_BONNET + " TEXT," +
            EVENT_HEAD_NORMAL + " TEXT," +
            EVENT_HEAD_UP + " TEXT," +
            EVENT_HEAD_DOWN + " TEXT," +
            EVENT_START + " DATE," +
            EVENT_END + " DATE)";

    /*private static final String LOCATION_SQL_CREATE = "CREATE TABLE " + LOCATION_TABLE + " (" +
            LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LOCATION_NAME + " TEXT," +
            LOCATION_LAT + " FLOAT(9,6)," +
            LOCATION_LONG + " FLOAT(9,6)," +
            LOCATION_RADIUS + " INTEGER," +
            LOCATION_TYPE + " INTEGER)";*/

    public LocalDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(BODY_SQL_CREATE);
        db.execSQL(EXPRESSION_SQL_CREATE);

        db.execSQL(EVENT_HEAD_SQL_CREATE);
        //db.execSQL(LOCATION_SQL_CREATE);

        Food_DB.initializeTable(db);
        Room_DB.initializeTable(db);
        Tacky_DB.initializeTable(db);
        TackyHead_DB.initializeTable(db);

        initializeHeadTable(db);
        initializeBodyTable(db);
        initializeExpressionTable(db);
        initializeEventHeadTable(db);
    }

    private static final String dropTable = "DROP TABLE IF EXISTS ";
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable + TACKY_TABLE);
        db.execSQL(dropTable + ROOM_TABLE);
        db.execSQL(dropTable + FOOD_TABLE);

        db.execSQL(dropTable + HEAD_TABLE);
        db.execSQL(dropTable + BODY_TABLE);
        db.execSQL(dropTable + EXPRESSION_TABLE);

        db.execSQL(dropTable + EVENT_HEAD_TABLE);

        onCreate(db);
    }

    public static LocalDatabase getDatabase(Context ctx){

        if(db == null)
            db = new LocalDatabase(ctx.getApplicationContext());

        return db;

    }

    public void deleteValue(String tableName, String whereClause, String[] whereArgs){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        sqlDB.delete(tableName, whereClause, whereArgs);
        sqlDB.close();
    }

    public <T> void insertValue(String tableName, T value, StoreCommand<T> storeCommand){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        ContentValues cv = storeCommand.storeItem(value);

        sqlDB.insert(tableName, null, cv);
        sqlDB.close();
    }

    public <T> T readValue(String query, ReadCommand<T> readCommand){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(query, null);
        cur.moveToFirst();
        T result = null;

        if (cur.getCount() == 1) {
            result = readCommand.readItem(cur);
        }

        cur.close();
        db.close();
        return result;
    }

    public <T> ArrayList<T> readValues(String query, ReadCommand<T> readCommand){
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor cur = sqlDB.rawQuery(query, null);
        cur.moveToFirst();

        ArrayList<T> result = new ArrayList<T>();
        while(!cur.isAfterLast()) {
            result.add(readCommand.readItem(cur));
            cur.moveToNext();
        }

        cur.close();
        sqlDB.close();
        return result;
    }

    public <T> void replaceValue(String tableName, T value, StoreCommand<T> storeCommand){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        ContentValues cv = storeCommand.storeItem(value);

        sqlDB.replace(tableName, null, cv); // replace entry if one already exists

        sqlDB.close();
    }

    public <T> void updateValue(String tableName, String whereClause, String[] whereArgs, T value, StoreCommand<T> storeCommand){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        ContentValues cv = storeCommand.storeItem(value);

        sqlDB.update(tableName, cv, whereClause, whereArgs);
        sqlDB.close();
    }

    public Bitmap decodeImage(String name){
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), imageResource);
    }

    private void storeHead(String normal, String bonnet, String up, String down, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(HEAD_BONNET, bonnet);
        cv.put(HEAD_NORMAL, normal);
        cv.put(HEAD_UP, up);
        cv.put(HEAD_DOWN, down);
        db.insert(HEAD_TABLE, null, cv);
    }
    private void storeBody(String vis, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(BODY_VISUAL, vis);
        db.insert(BODY_TABLE, null, cv);
    }
    private void storeExpression(String fhappy, String fnormal, String funhappy, // front
                                 String shappy, String snormal, String sunhappy, // side
                                 String sleep, String sleepsmile, // sleep
                                 SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(EXPRESSION_FRONT_HAPPY, fhappy);
        cv.put(EXPRESSION_FRONT_NORMAL, fnormal);
        cv.put(EXPRESSION_FRONT_UNHAPPY, funhappy);
        cv.put(EXPRESSION_SIDE_HAPPY, shappy);
        cv.put(EXPRESSION_SIDE_NORMAL, snormal);
        cv.put(EXPRESSION_SIDE_UNHAPPY, sunhappy);
        cv.put(EXPRESSION_SLEEP, sleep);
        cv.put(EXPRESSION_SLEEP_SMILE, sleepsmile);
        db.insert(EXPRESSION_TABLE, null, cv);
    }

    public TackyBody[] getBodies() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(BODY_TABLE, null, null, null, null, null, null);
        cur.moveToFirst();

        TackyBody[] result = new TackyBody[cur.getCount()];
        for (int i = 0; !cur.isAfterLast(); ++i) {
            int dbid = cur.getInt(cur.getColumnIndex(BODY_ID));
            String visual = cur.getString(cur.getColumnIndex(BODY_VISUAL));

            result[i] = new TackyBody(visual, dbid);
            cur.moveToNext();
        }

        cur.close();
        db.close();
        return result;
    }
    public TackyExpression[] getExpressions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(EXPRESSION_TABLE, null, null, null, null, null, null);
        cur.moveToFirst();

        TackyExpression[] result = new TackyExpression[cur.getCount()];
        for (int i = 0; !cur.isAfterLast(); ++i) {
            int dbid = cur.getInt(cur.getColumnIndex(EXPRESSION_ID));
            String frontHappy = cur.getString(cur.getColumnIndex(EXPRESSION_FRONT_HAPPY));
            String frontNormal = cur.getString(cur.getColumnIndex(EXPRESSION_FRONT_NORMAL));
            String frontSad = cur.getString(cur.getColumnIndex(EXPRESSION_FRONT_UNHAPPY));
            String sideHappy = cur.getString(cur.getColumnIndex(EXPRESSION_SIDE_HAPPY));
            String sideNormal = cur.getString(cur.getColumnIndex(EXPRESSION_SIDE_NORMAL));
            String sideSad = cur.getString(cur.getColumnIndex(EXPRESSION_SIDE_UNHAPPY));
            String sleep = cur.getString(cur.getColumnIndex(EXPRESSION_SLEEP));

            result[i] = new TackyExpression(frontHappy, frontNormal, frontSad, sideHappy, sideNormal, sideSad, sleep, dbid);
            cur.moveToNext();
        }

        cur.close();
        db.close();
        return result;
    }

    public TackyDisplayObject getTackyDisplayObject(Tacky t){

        TackyHead head = this.getEventHead(t.getHeadId()); // check for special head first
        if (head == null) head = this.getHead(t.getHeadId()); // no special head, use normal head
        TackyBody body = this.getBody(t.getBodyId());
        TackyExpression expression = this.getExpression(t.getExpressionId());

        return new TackyDisplayObject(head, body, expression);
    }

    public TackyHead getHead(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(HEAD_TABLE, null, HEAD_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        cur.moveToFirst();
        TackyHead result = null;

        if (cur.getCount() == 1) {
            String bonnet = cur.getString(cur.getColumnIndex(HEAD_BONNET));
            String normal = cur.getString(cur.getColumnIndex(HEAD_NORMAL));
            String up = cur.getString(cur.getColumnIndex(HEAD_UP));
            String down = cur.getString(cur.getColumnIndex(HEAD_DOWN));
            result = new TackyHead(normal, bonnet, up, down, id);
        }

        cur.close();
        db.close();
        return result;
    }
    public TackyBody getBody(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(BODY_TABLE, new String[]{BODY_VISUAL}, BODY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        cur.moveToFirst();
        TackyBody result = null;

        if (cur.getCount() == 1) {
            String vis = cur.getString(cur.getColumnIndex(BODY_VISUAL));
            result = new TackyBody(vis, id);
        }

        cur.close();
        db.close();
        return result;
    }
    public TackyExpression getExpression(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(EXPRESSION_TABLE, null, EXPRESSION_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        cur.moveToFirst();
        TackyExpression result = null;

        if (cur.getCount() == 1) {
            String frontHappy = cur.getString(cur.getColumnIndex(EXPRESSION_FRONT_HAPPY));
            String frontNormal = cur.getString(cur.getColumnIndex(EXPRESSION_FRONT_NORMAL));
            String frontSad = cur.getString(cur.getColumnIndex(EXPRESSION_FRONT_UNHAPPY));
            String sideHappy = cur.getString(cur.getColumnIndex(EXPRESSION_SIDE_HAPPY));
            String sideNormal = cur.getString(cur.getColumnIndex(EXPRESSION_SIDE_NORMAL));
            String sideSad = cur.getString(cur.getColumnIndex(EXPRESSION_SIDE_UNHAPPY));
            String sleep = cur.getString(cur.getColumnIndex(EXPRESSION_SLEEP));
            result = new TackyExpression(frontHappy, frontNormal, frontSad, sideHappy, sideNormal, sideSad, sleep, id);
        }

        cur.close();
        db.close();
        return result;
    }

    private void storeEventHead(String normal, String bonnet, String up, String down, Time start, Time end, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(EVENT_HEAD_BONNET, bonnet);
        cv.put(EVENT_HEAD_NORMAL, normal);
        cv.put(EVENT_HEAD_UP, up);
        cv.put(EVENT_HEAD_DOWN, down);
        cv.put(EVENT_START, start.toMillis(false));
        cv.put(EVENT_END, end.toMillis(false));
        db.insert(EVENT_HEAD_TABLE, null, cv);
    }

    public TackyHead getEventHead(int id) {
        Time now = new Time();
        now.setToNow();
        now.hour = now.minute = now.second = 0;
        now.allDay = true;
        String nowstr = Long.toString(now.toMillis(false));

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(EVENT_HEAD_TABLE,
                new String[]{EVENT_HEAD_NORMAL, EVENT_HEAD_BONNET, EVENT_HEAD_UP, EVENT_HEAD_DOWN},
                EVENT_START + "<=? AND " + EVENT_END + ">=?", new String[]{nowstr, nowstr},
                null, null, null);
        cur.moveToFirst();
        TackyHead result = null;

        if (cur.getCount() > 0) {
            String bonnet = cur.getString(cur.getColumnIndex(EVENT_HEAD_BONNET));
            String normal = cur.getString(cur.getColumnIndex(EVENT_HEAD_NORMAL));
            String up = cur.getString(cur.getColumnIndex(EVENT_HEAD_UP));
            String down = cur.getString(cur.getColumnIndex(EVENT_HEAD_DOWN));
            result = new TackyHead(normal, bonnet, up, down, id);
        }

        cur.close();
        db.close();
        return result;
    }

    private static final String[] colors = {"green", "orange", "pink", "red", "turquoise", "yellow"};
    private void initializeHeadTable(SQLiteDatabase db) {
        for (String color : colors) {
            String normal = "head_normal_" + color;
            String bonnet = "bonnet_head_normal_" + color;
            String up = "head_up_" + color;
            String down = "head_down_" + color;
            storeHead(normal, bonnet, up, down, db);
        }
    }
    private void initializeBodyTable(SQLiteDatabase db) {
        for (String color : colors) {
            storeBody("body_" + color, db);
        }
    }
    private void initializeExpressionTable(SQLiteDatabase db) {
        for (String color : colors) {
            String fhappy = "front_happy_" + color;
            String fnormal = "front_normal_" + color;
            String funhappy = "front_unhappy_" + color;
            String shappy = "side_happy_" + color;
            String snormal = "side_normal_" + color;
            String sunhappy = "side_unhappy_" + color;
            String sleep = "sleep_normal_" + color;
            String sleepsmile = "sleep_normal_smile_" + color;
            storeExpression(fhappy, fnormal, funhappy,
                    shappy, snormal, sunhappy,
                    sleep, sleepsmile, db);
        }
    }

    private void initializeEventHeadTable(SQLiteDatabase db) {
        Time xmasStart = new Time();
        xmasStart.year = 2013;
        xmasStart.month = 12 - 1; // zero-based
        xmasStart.monthDay = 21;
        xmasStart.hour = xmasStart.minute = xmasStart.second = 0;
        xmasStart.allDay = true;
        Time xmasEnd = new Time(xmasStart);
        xmasEnd.monthDay = 28;
        storeEventHead("kerstmuts_normal", "kerstmuts_normal", "kerstmuts_up", "kerstmuts_down", xmasStart, xmasEnd, db);

        Time easterStart = new Time(xmasStart);
        easterStart.year = 2014;
        easterStart.month = 4 - 1; // zero-based
        easterStart.monthDay = 13;
        easterStart.allDay = true;
        Time easterEnd = new Time(easterStart);
        easterEnd.monthDay = 24;
        storeEventHead("pasen_normal", "pasen_normal", "pasen_up", "pasen_down", easterStart, easterEnd, db);
    }


}