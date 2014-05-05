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
import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.basic.State.State;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;
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

    private Context context;

    private static final String TACKY_TABLE = "tackies";
    private static final String FOOD_TABLE = "foods";
    private static final String ROOM_TABLE = "rooms";

    private static final String HEAD_TABLE = "heads";
    private static final String BODY_TABLE = "bodies";
    private static final String EXPRESSION_TABLE = "expressions";
    private static final String EVENT_HEAD_TABLE = "eventHeads";
    //private static final String LOCATION_TABLE = "locations";

    private static final String TACKY_NAME = "name";
    private static final String TACKY_DAYOFBIRTH = "dayOfBirth";
    private static final String TACKY_HAPPYLVL = "happinessLevel";
    private static final String TACKY_HAPPYGAIN = "happinessGain";
    private static final String TACKY_ENERGYLVL = "energyLevel";
    private static final String TACKY_ENERGYGAIN = "energyGain";
    private static final String TACKY_SATISFIEDLVL = "satisfiedLevel";
    private static final String TACKY_SATISFIEDGAIN = "satisfiedGain";
    private static final String TACKY_CURRENTROOM = "currentRoom";
    private static final String TACKY_HEAD = "head";
    private static final String TACKY_BODY = "body";
    private static final String TACKY_EXPRESSION = "expression";

    private static final String ROOM_NAME = "name";
    private static final String ROOM_OWNER = "owner";
    private static final String ROOM_VISUAL = "visualization";
    private static final String ROOM_TYPE = "type";

    private static final String FOOD_NAME = "name";
    private static final String FOOD_VISUAL = "visualization";
    private static final String FOOD_ENERGY = "energyValue";
    private static final String FOOD_USES = "totalUses";

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

    private static final String TACKY_SQL_CREATE = "CREATE TABLE " + TACKY_TABLE + " (" +
            TACKY_NAME + " TEXT PRIMARY KEY," +
            TACKY_DAYOFBIRTH + " INTEGER," +
            TACKY_HAPPYLVL + " REAL," +
            TACKY_HAPPYGAIN + " REAL," +
            TACKY_ENERGYLVL + " REAL," +
            TACKY_ENERGYGAIN + " REAL," +
            TACKY_SATISFIEDLVL + " REAL," +
            TACKY_SATISFIEDGAIN + " REAL," +
            TACKY_CURRENTROOM + " TEXT," +
            TACKY_HEAD + " REAL," +
            TACKY_BODY + " REAL," +
            TACKY_EXPRESSION + " REAL," +
            "FOREIGN KEY(" + TACKY_CURRENTROOM + ") REFERENCES " + ROOM_TABLE + "(" + ROOM_NAME + ")," +
            "FOREIGN KEY(" + TACKY_HEAD + ") REFERENCES " + HEAD_TABLE + "(" + HEAD_ID + ")," +
            "FOREIGN KEY(" + TACKY_BODY + ") REFERENCES " + BODY_TABLE + "(" + BODY_ID + ")," +
            "FOREIGN KEY(" + TACKY_EXPRESSION + ") REFERENCES " + EXPRESSION_TABLE + "(" + EXPRESSION_ID + "))";

    private static final String ROOM_SQL_CREATE = "CREATE TABLE " + ROOM_TABLE + " (" +
            ROOM_NAME + " TEXT," +
            ROOM_OWNER + " TEXT," +
            ROOM_VISUAL + " TEXT," +
            ROOM_TYPE + " INTEGER," +
            "PRIMARY KEY(" + ROOM_NAME + "," + ROOM_OWNER + ")," +
            "FOREIGN KEY(" + ROOM_OWNER + ") REFERENCES " + TACKY_TABLE + "(" + TACKY_NAME + "))";

    private static final String FOOD_SQL_CREATE = "CREATE TABLE " + FOOD_TABLE + " (" +
            FOOD_NAME + " TEXT PRIMARY KEY," +
            FOOD_VISUAL + " TEXT," +
            FOOD_ENERGY + " INTEGER," +
            FOOD_USES + " INTEGER)";

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
        db.execSQL(TACKY_SQL_CREATE);
        db.execSQL(ROOM_SQL_CREATE);
        db.execSQL(FOOD_SQL_CREATE);

        db.execSQL(HEAD_SQL_CREATE);
        db.execSQL(BODY_SQL_CREATE);
        db.execSQL(EXPRESSION_SQL_CREATE);

        db.execSQL(EVENT_HEAD_SQL_CREATE);
        //db.execSQL(LOCATION_SQL_CREATE);

        initializeFoodTable(db);
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

    public Tacky getTacky(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TACKY_TABLE, null, TACKY_NAME + "=?", new String[]{name}, null, null, null);
        cur.moveToFirst();
        Tacky result = null;

        if (cur.getCount() == 1) {
            long birthday = cur.getLong(cur.getColumnIndex(TACKY_DAYOFBIRTH));
            double happinessLevel = cur.getDouble(cur.getColumnIndex(TACKY_HAPPYLVL));
            double happinessGain = cur.getDouble(cur.getColumnIndex(TACKY_HAPPYGAIN));
            double energyLevel = cur.getDouble(cur.getColumnIndex(TACKY_ENERGYLVL));
            double energyGain = cur.getDouble(cur.getColumnIndex(TACKY_ENERGYGAIN));
            double satisfiedLevel = cur.getDouble(cur.getColumnIndex(TACKY_SATISFIEDLVL));
            double satisfiedGain = cur.getDouble(cur.getColumnIndex(TACKY_SATISFIEDGAIN));
            MoodState happiness = new MoodState(happinessLevel, happinessGain);
            State energy = new State(energyLevel, energyGain);
            State satisfied = new State(satisfiedLevel, satisfiedGain);
            TackyState tackyState = new TackyState(happiness, energy, satisfied);
            Room r = getRoom(cur.getString(cur.getColumnIndex(TACKY_CURRENTROOM)), name);
            int headId = cur.getInt(cur.getColumnIndex(TACKY_HEAD));
            int bodyId = cur.getInt(cur.getColumnIndex(TACKY_BODY));
            int expressionId = cur.getInt(cur.getColumnIndex(TACKY_EXPRESSION));

            result = new Tacky(name, birthday, tackyState, r, headId, bodyId, expressionId);
        }

        cur.close();
        db.close();
        return result;
    }

    public String[] getTackyNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TACKY_TABLE, new String[]{TACKY_NAME}, null, null, null, null, null);
        cur.moveToFirst();

        String[] result = new String[cur.getCount()];
        for (int i = 0; !cur.isAfterLast(); ++i) {
            result[i] = cur.getString(0);
            cur.moveToNext();
        }

        cur.close();
        db.close();
        return result;
    }

    // for initial creation
    public synchronized void storeTacky(Tacky t, TackyHead head, TackyBody body, TackyExpression exp) {
        ContentValues cv = new ContentValues();
        cv.put(TACKY_NAME, t.getName());
        cv.put(TACKY_DAYOFBIRTH, t.getDayOfBirth().toMillis(false));
        cv.put(TACKY_HAPPYLVL, t.getHappinessStateLevel());
        cv.put(TACKY_HAPPYGAIN, t.getHappinessGain());
        cv.put(TACKY_ENERGYLVL, t.getEnergyLevel());
        cv.put(TACKY_ENERGYGAIN, t.getEnergyGain());
        cv.put(TACKY_SATISFIEDLVL, t.getSatisfiedLevel());
        cv.put(TACKY_SATISFIEDGAIN, t.getSatisfiedGain());
        cv.put(TACKY_CURRENTROOM, t.getRoomName());
        cv.put(TACKY_HEAD, head.getDatabaseId());
        cv.put(TACKY_BODY, body.getDatabaseId());
        cv.put(TACKY_EXPRESSION, exp.getDatabaseId());

        storeRoom(t);

        SQLiteDatabase db = this.getWritableDatabase();
        db.replace(TACKY_TABLE, null, cv); // replace entry if one already exists
        db.close();
    }

    // for incremental updates of Tacky
    public synchronized void updateTacky(Tacky t) {
        ContentValues cv = new ContentValues();
        cv.put(TACKY_HAPPYLVL, t.getHappinessStateLevel());
        cv.put(TACKY_HAPPYGAIN, t.getHappinessGain());
        cv.put(TACKY_ENERGYLVL, t.getEnergyLevel());
        cv.put(TACKY_ENERGYGAIN, t.getEnergyGain());
        cv.put(TACKY_SATISFIEDLVL, t.getSatisfiedLevel());
        cv.put(TACKY_SATISFIEDGAIN, t.getSatisfiedGain());
        cv.put(TACKY_CURRENTROOM, t.getRoomName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TACKY_TABLE, cv, TACKY_NAME + "=?", new String[]{t.getName()});
        db.close();
    }

    // for incremental updates of Tacky
    public synchronized void updateTackyWithoutRoom(Tacky t) {
        ContentValues cv = new ContentValues();
        cv.put(TACKY_HAPPYLVL, t.getHappinessStateLevel());
        cv.put(TACKY_HAPPYGAIN, t.getHappinessGain());
        cv.put(TACKY_ENERGYLVL, t.getEnergyLevel());
        cv.put(TACKY_ENERGYGAIN, t.getEnergyGain());
        cv.put(TACKY_SATISFIEDLVL, t.getSatisfiedLevel());
        cv.put(TACKY_SATISFIEDGAIN, t.getSatisfiedGain());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TACKY_TABLE, cv, TACKY_NAME + "=?", new String[]{t.getName()});
        db.close();
    }

    public void deleteTacky(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{name};
        db.delete(TACKY_TABLE, TACKY_NAME + "=?", args);
        db.delete(ROOM_TABLE, ROOM_OWNER + "=?", args); // remove rooms owned by this Tacky
        db.close();
    }

    public Tacky initializeTacky(String name, TackyHead head, TackyBody body, TackyExpression expression){
        Room main = new Room("Main Room", "green_yellow_blue_tile", Room.RoomType.MAIN);
        Room kitchen = new Room("Kitchen", "kitchen_background", Room.RoomType.KITCHEN);
        Room bedroom = new Room("Bedroom", "bedroom_background2", Room.RoomType.BEDROOM);
        Room bathroom = new Room("Bathroom", "bathroom_background1", Room.RoomType.BATHROOM);
        Room outdoors = new Room("Outdoors", "background4", Room.RoomType.OUTDOORS);

        Tacky tacky = new Tacky(name, main, head.getDatabaseId(), body.getDatabaseId(), expression.getDatabaseId());

        storeTacky(tacky, head, body, expression);
        storeRoom(kitchen, tacky);
        storeRoom(bedroom, tacky);
        storeRoom(bathroom, tacky);
        storeRoom(outdoors, tacky);

        return tacky;
    }

    public Room getRoom(String name, String owner) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(ROOM_TABLE, new String[]{ROOM_VISUAL, ROOM_TYPE},
                ROOM_NAME + "=? AND " + ROOM_OWNER + "=?",
                new String[]{name, owner}, null, null, null);
        cur.moveToFirst();
        Room result = null;

        if (cur.getCount() == 1) {
            String vis = cur.getString(cur.getColumnIndex(ROOM_VISUAL));
            int type = cur.getInt(cur.getColumnIndex(ROOM_TYPE));
            result = new Room(name, vis, type);
        }

        cur.close();
        db.close();
        return result;
    }

    public Room getRoom(String name, Tacky owner) {
        return this.getRoom(name, owner.getName());
    }

    public Room[] getRooms(Tacky t) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(ROOM_TABLE, new String[]{ROOM_NAME, ROOM_VISUAL, ROOM_TYPE},
                ROOM_OWNER + "=?", new String[]{t.getName()}, null, null, null);
        cur.moveToFirst();
        Room[] result = new Room[cur.getCount()];

        for (int i = 0; !cur.isAfterLast(); ++i) {
            String name = cur.getString(cur.getColumnIndex(ROOM_NAME));
            String vis = cur.getString(cur.getColumnIndex(ROOM_VISUAL));
            int type = cur.getInt(cur.getColumnIndex(ROOM_TYPE));
            result[i] = new Room(name, vis, type);
            cur.moveToNext();
        }

        cur.close();
        db.close();
        return result;
    }

    public synchronized  void storeRoom(Tacky owner){
        ContentValues cv = new ContentValues();
        cv.put(ROOM_NAME, owner.getRoomName());
        cv.put(ROOM_OWNER, owner.getName());
        cv.put(ROOM_VISUAL, owner.getRoomVisualization());
        cv.put(ROOM_TYPE, owner.getRoomType().ordinal());

        SQLiteDatabase db = this.getWritableDatabase();
        db.replace(ROOM_TABLE, null, cv); // replace entry if one already exists
        db.close();
    }

    public synchronized void storeRoom(Room r, Tacky owner) {
        ContentValues cv = new ContentValues();
        cv.put(ROOM_NAME, r.getName());
        cv.put(ROOM_OWNER, owner.getName());
        cv.put(ROOM_VISUAL, r.getVisualization());
        cv.put(ROOM_TYPE, r.getRoomType().ordinal());

        SQLiteDatabase db = this.getWritableDatabase();
        db.replace(ROOM_TABLE, null, cv); // replace entry if one already exists
        db.close();
    }

    public void deleteRoom(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ROOM_TABLE, ROOM_NAME + "=?", new String[]{name});
        db.close();
    }

    public List<String> getBackgrounds(){
        List<String> backgrounds = new ArrayList<String>();
        backgrounds.add("green_yellow_blue_tile");
        backgrounds.add("soccer");
        backgrounds.add("background1");
        backgrounds.add("background2");
        backgrounds.add("background3");
        backgrounds.add("background4");
        backgrounds.add("kitchen_background");
        backgrounds.add("bathroom_background1");
        backgrounds.add("bathroom_background2");
        backgrounds.add("bedroom_background1");
        backgrounds.add("bedroom_background2");
        return backgrounds;
    }

    public Food[] getFoods() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(FOOD_TABLE, null, null, null, null, null, null);
        cur.moveToFirst();

        Food[] result = new Food[cur.getCount()];
        for (int i = 0; !cur.isAfterLast(); ++i) {
            String name = cur.getString(cur.getColumnIndex(FOOD_NAME));
            String visual = cur.getString(cur.getColumnIndex(FOOD_VISUAL));
            int energyVal = cur.getInt(cur.getColumnIndex(FOOD_ENERGY));
            int totalUses = cur.getInt(cur.getColumnIndex(FOOD_USES));
            result[i] = new Food(name, visual, energyVal, totalUses);
            cur.moveToNext();
        }

        cur.close();
        db.close();
        return result;
    }

    public void initializeFoodTable(SQLiteDatabase db) {
        Food f1 = new Food("Bread", "brood", 10, -1);
        Food f2 = new Food("Orange juice", "orangejuice", 5, -1);
        Food f3 = new Food("Yoghurt", "yoghurt", 3, -1);
        // onCreate requires that we use the db object it provides
        // Otherwise we get 'getWritableDatabase called recursively' error
        storeFood(f1, db);
        storeFood(f2, db);
        storeFood(f3, db);
    }

    public void storeFood(Food f) {
        SQLiteDatabase db = this.getWritableDatabase();
        this.storeFood(f, db);
        db.close();
    }

    // needed for onCreate, see void initializeFoodTable()
    private void storeFood(Food f, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(FOOD_NAME, f.getName());
        cv.put(FOOD_VISUAL, f.getVisualization());
        cv.put(FOOD_ENERGY, f.getEnergyValue());
        cv.put(FOOD_USES, f.getTotalUses());

        db.replace(FOOD_TABLE, null, cv); // replace entry if one already exists
    }

    public void deleteFood(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FOOD_TABLE, FOOD_NAME + "=?", new String[]{name});
        db.close();
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

    public TackyHead[] getHeads() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(HEAD_TABLE, null, null, null, null, null, null);
        cur.moveToFirst();

        TackyHead[] result = new TackyHead[cur.getCount()];
        for (int i = 0; !cur.isAfterLast(); ++i) {
            int dbid = cur.getInt(cur.getColumnIndex(HEAD_ID));
            String bonnet = cur.getString(cur.getColumnIndex(HEAD_BONNET));
            String normal = cur.getString(cur.getColumnIndex(HEAD_NORMAL));
            String up = cur.getString(cur.getColumnIndex(HEAD_UP));
            String down = cur.getString(cur.getColumnIndex(HEAD_DOWN));

            result[i] = new TackyHead(normal, bonnet, up, down, dbid);
            cur.moveToNext();
        }

        cur.close();
        db.close();
        return result;
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

    private TackyLocation[] dbLocations = {
            new TackyLocation("Atomium", "atomium", Outdoors.OutdoorsType.PARK, 50.894722, 4.341111, 100),
            new TackyLocation("Castle of Gaasbeek", "gaasbeek_park", Outdoors.OutdoorsType.PARK, 50.799968, 4.201902, 100)
    };


    public TackyLocation getNearbyLocation(Location currentLocation) {

        TackyLocation l = null;

        for (TackyLocation location : dbLocations) {
            if (location.isSameLocation(currentLocation)) {
                l = location; // return first location in range
                break;
                }
        }

        return l; // no suitable location found
    }

}