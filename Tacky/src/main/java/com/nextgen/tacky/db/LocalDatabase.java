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
import com.nextgen.tacky.db.localDB.TackyBody_DB;
import com.nextgen.tacky.db.localDB.TackyEventHead_DB;
import com.nextgen.tacky.db.localDB.TackyExpression_DB;
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
    private static final int DB_VERSION = 15;
    private static LocalDatabase db = null;

    private static SQLiteDatabase sqLiteDatabase = null;

    private LocalDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //db.execSQL(LOCATION_SQL_CREATE);
        LocalDatabase.sqLiteDatabase = sqLiteDatabase;

        Food_DB.initializeTable(db);
        Room_DB.initializeTable(db);
        Tacky_DB.initializeTable(db);
        TackyHead_DB.initializeTable(db);
        TackyBody_DB.initializeTable(db);
        TackyExpression_DB.initializeTable(db);
        TackyEventHead_DB.initializeTable(db);

        LocalDatabase.sqLiteDatabase = null;
    }

    private static final String dropTable = "DROP TABLE IF EXISTS ";
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        LocalDatabase.sqLiteDatabase = sqLiteDatabase;

        Food_DB.dropTable(db, dropTable);
        Room_DB.dropTable(db, dropTable);
        Tacky_DB.dropTable(db, dropTable);
        TackyHead_DB.dropTable(db, dropTable);
        TackyBody_DB.dropTable(db, dropTable);
        TackyExpression_DB.dropTable(db, dropTable);
        TackyEventHead_DB.dropTable(db, dropTable);

        onCreate(sqLiteDatabase);
        LocalDatabase.sqLiteDatabase = null;
    }

    public static LocalDatabase getDatabase(Context ctx){

        if(db == null)
            db = new LocalDatabase(ctx.getApplicationContext());

        return db;

    }

    public void addTable(String sqlStatement){
        sqLiteDatabase.execSQL(sqlStatement);
    }

    public void dropTable(String sqlStatement){
        sqLiteDatabase.execSQL(sqlStatement);
    }

    public void deleteValue(String tableName, String whereClause, String[] whereArgs){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        sqlDB.delete(tableName, whereClause, whereArgs);
        sqlDB.close();
    }

    public <T> void insertValue(String tableName, T value, StoreCommand<T> storeCommand){
        if(sqLiteDatabase == null) {
            SQLiteDatabase sqlDB = db.getWritableDatabase();
            ContentValues cv = storeCommand.storeItem(value);

            sqlDB.insert(tableName, null, cv);
            sqlDB.close();
        }
        else {
            ContentValues cv = storeCommand.storeItem(value);
            sqLiteDatabase.insert(tableName, null, cv);
        }
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


}