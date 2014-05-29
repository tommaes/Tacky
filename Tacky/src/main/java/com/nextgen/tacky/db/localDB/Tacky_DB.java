package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.State.MoodState;
import com.nextgen.tacky.basic.State.State;
import com.nextgen.tacky.basic.tacky.Tacky;
import com.nextgen.tacky.basic.tacky.TackyState;
import com.nextgen.tacky.db.LocalDatabase;
import com.nextgen.tacky.display.TackyBody;
import com.nextgen.tacky.display.TackyExpression;
import com.nextgen.tacky.display.TackyHead;

import java.util.ArrayList;

/**
 * Created by maes on 22/05/14.
 */
public class Tacky_DB {

    private static LocalDatabase db = null;

    public static final String TACKY_TABLE = "tackies";

    public static final String TACKY_NAME = "name";
    public static final String TACKY_DAYOFBIRTH = "dayOfBirth";
    public static final String TACKY_HAPPYLVL = "happinessLevel";
    public static final String TACKY_HAPPYGAIN = "happinessGain";
    public static final String TACKY_ENERGYLVL = "energyLevel";
    public static final String TACKY_ENERGYGAIN = "energyGain";
    public static final String TACKY_SATISFIEDLVL = "satisfiedLevel";
    public static final String TACKY_SATISFIEDGAIN = "satisfiedGain";
    public static final String TACKY_CURRENTROOM = "currentRoom";
    public static final String TACKY_HEAD = "head";
    public static final String TACKY_BODY = "body";
    public static final String TACKY_EXPRESSION = "expression";

    private static final String HEAD_TABLE = "heads";
    private static final String BODY_TABLE = "bodies";
    private static final String EXPRESSION_TABLE = "expressions";

    private static final String HEAD_ID = "id";
    private static final String BODY_ID = "id";
    private static final String EXPRESSION_ID = "id";

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
            "FOREIGN KEY(" + TACKY_CURRENTROOM + ") REFERENCES " + Room_DB.ROOM_TABLE + "(" + Room_DB.ROOM_NAME + ")," +
            "FOREIGN KEY(" + TACKY_HEAD + ") REFERENCES " + HEAD_TABLE + "(" + HEAD_ID + ")," +
            "FOREIGN KEY(" + TACKY_BODY + ") REFERENCES " + BODY_TABLE + "(" + BODY_ID + ")," +
            "FOREIGN KEY(" + TACKY_EXPRESSION + ") REFERENCES " + EXPRESSION_TABLE + "(" + EXPRESSION_ID + "))";

    private static final String SELECT_TACKY_NAMES = "SELECT " + TACKY_NAME +
                                                     " FROM " + TACKY_TABLE;

    private static final String TACKY_SQL_READ_TACKY = "SELECT * " +
                                                       "FROM " + TACKY_TABLE +
                                                       " WHERE " + TACKY_NAME + " = '%s'";

    public Tacky_DB(Context ctx){
        if(db == null)
            db = LocalDatabase.getDatabase(ctx);
    }

    public static void initializeTable(LocalDatabase db) {
        db.addTable(TACKY_SQL_CREATE);
    }

    public ArrayList<String> getTackyNames() {
        return db.readValues(SELECT_TACKY_NAMES, new ReadCommand<String>() {
            @Override
            public String readItem(Cursor cursor) {
                return cursor.getString(0);
            }
        });
    }

    public void deleteTacky(String name) {
        String[] args = new String[]{name};
        db.deleteValue(TACKY_TABLE, TACKY_NAME + "=?", args);
    }

    public Tacky getTacky(String name) {
        String query = String.format(TACKY_SQL_READ_TACKY, name);
        return db.readValue(query, new ReadCommand<Tacky>() {
            @Override
            public Tacky readItem(Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndex(TACKY_NAME));
                long birthday = cursor.getLong(cursor.getColumnIndex(TACKY_DAYOFBIRTH));
                double happinessLevel = cursor.getDouble(cursor.getColumnIndex(TACKY_HAPPYLVL));
                double happinessGain = cursor.getDouble(cursor.getColumnIndex(TACKY_HAPPYGAIN));
                double energyLevel = cursor.getDouble(cursor.getColumnIndex(TACKY_ENERGYLVL));
                double energyGain = cursor.getDouble(cursor.getColumnIndex(TACKY_ENERGYGAIN));
                double satisfiedLevel = cursor.getDouble(cursor.getColumnIndex(TACKY_SATISFIEDLVL));
                double satisfiedGain = cursor.getDouble(cursor.getColumnIndex(TACKY_SATISFIEDGAIN));
                MoodState happiness = new MoodState(happinessLevel, happinessGain);
                State energy = new State(energyLevel, energyGain);
                State satisfied = new State(satisfiedLevel, satisfiedGain);
                TackyState tackyState = new TackyState(happiness, energy, satisfied);
                Room r = new Room(cursor.getString(cursor.getColumnIndex(TACKY_CURRENTROOM)), "", 0);
                int headId = cursor.getInt(cursor.getColumnIndex(TACKY_HEAD));
                int bodyId = cursor.getInt(cursor.getColumnIndex(TACKY_BODY));
                int expressionId = cursor.getInt(cursor.getColumnIndex(TACKY_EXPRESSION));

                return new Tacky(name, birthday, tackyState, r, headId, bodyId, expressionId);
            }
        });
    }

    public Tacky initializeTacky(String name, TackyHead head, TackyBody body, TackyExpression expression){
        Room main = new Room("Main Room", "green_yellow_blue_tile", Room.RoomType.MAIN);
        Tacky tacky = new Tacky(name, main, head.getDatabaseId(), body.getDatabaseId(), expression.getDatabaseId());

        this.storeTacky(tacky, head, body, expression);

        return tacky;
    }

    private void storeTacky(Tacky t, final TackyHead head, final TackyBody body, final TackyExpression exp) {
        db.replaceValue(TACKY_TABLE, t, new StoreCommand<Tacky>() {
            @Override
            public ContentValues storeItem(Tacky item) {
                ContentValues cv = new ContentValues();
                cv.put(TACKY_NAME, item.getName());
                cv.put(TACKY_DAYOFBIRTH, item.getDayOfBirth().toMillis(false));
                cv.put(TACKY_HAPPYLVL, item.getHappinessStateLevel());
                cv.put(TACKY_HAPPYGAIN, item.getHappinessGain());
                cv.put(TACKY_ENERGYLVL, item.getEnergyLevel());
                cv.put(TACKY_ENERGYGAIN, item.getEnergyGain());
                cv.put(TACKY_SATISFIEDLVL, item.getSatisfiedLevel());
                cv.put(TACKY_SATISFIEDGAIN, item.getSatisfiedGain());
                cv.put(TACKY_CURRENTROOM, item.getRoomName());
                cv.put(TACKY_HEAD, head.getDatabaseId());
                cv.put(TACKY_BODY, body.getDatabaseId());
                cv.put(TACKY_EXPRESSION, exp.getDatabaseId());

                return cv;
            }
        });
    }

    public void updateTacky(Tacky t) {
        db.updateValue(TACKY_TABLE, TACKY_NAME + "=?", new String[]{t.getName()}, t, new StoreCommand<Tacky>() {

            @Override
            public ContentValues storeItem(Tacky item) {
                ContentValues cv = new ContentValues();
                cv.put(TACKY_HAPPYLVL, item.getHappinessStateLevel());
                cv.put(TACKY_HAPPYGAIN, item.getHappinessGain());
                cv.put(TACKY_ENERGYLVL, item.getEnergyLevel());
                cv.put(TACKY_ENERGYGAIN, item.getEnergyGain());
                cv.put(TACKY_SATISFIEDLVL, item.getSatisfiedLevel());
                cv.put(TACKY_SATISFIEDGAIN, item.getSatisfiedGain());
                cv.put(TACKY_CURRENTROOM, item.getRoomName());

                return cv;
            }
        });
    }

    public void updateTackyWithoutRoom(Tacky t) {
        db.updateValue(TACKY_TABLE, TACKY_NAME + "=?", new String[]{t.getName()}, t, new StoreCommand<Tacky>() {

            @Override
            public ContentValues storeItem(Tacky item) {
                ContentValues cv = new ContentValues();
                cv.put(TACKY_HAPPYLVL, item.getHappinessStateLevel());
                cv.put(TACKY_HAPPYGAIN, item.getHappinessGain());
                cv.put(TACKY_ENERGYLVL, item.getEnergyLevel());
                cv.put(TACKY_ENERGYGAIN, item.getEnergyGain());
                cv.put(TACKY_SATISFIEDLVL, item.getSatisfiedLevel());
                cv.put(TACKY_SATISFIEDGAIN, item.getSatisfiedGain());

                return cv;
            }
        });
    }

    public static void dropTable(LocalDatabase db, String dropTable) {
        db.dropTable(dropTable + TACKY_TABLE);
    }
}
