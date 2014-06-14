package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nextgen.tacky.db.generic.ReadCommand;
import com.nextgen.tacky.db.generic.StoreCommand;

import java.util.ArrayList;

/**
 * Created by Bram on 31/10/13.
 */
public class LocalDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "tackyDB";
    private static final int DB_VERSION = 16;
    private static LocalDatabase db = null;

    private static SQLiteDatabase sqLiteDatabase = null;

    private LocalDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        LocalDatabase.sqLiteDatabase = sqLiteDatabase;

        LocalFood_DB.initializeTable(db);
        LocalRoom_DB.initializeTable(db);
        LocalTacky_DB.initializeTable(db);
        LocalTackyHead_DB.initializeTable(db);
        LocalTackyBody_DB.initializeTable(db);
        LocalTackyExpression_DB.initializeTable(db);
        LocalTackyEventHead_DB.initializeTable(db);
        LocalLocation_DB.initializeTable(db);

        LocalDatabase.sqLiteDatabase = null;
    }

    private static final String dropTable = "DROP TABLE IF EXISTS ";
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        LocalDatabase.sqLiteDatabase = sqLiteDatabase;

        LocalFood_DB.dropTable(db, dropTable);
        LocalRoom_DB.dropTable(db, dropTable);
        LocalTacky_DB.dropTable(db, dropTable);
        LocalTackyHead_DB.dropTable(db, dropTable);
        LocalTackyBody_DB.dropTable(db, dropTable);
        LocalTackyExpression_DB.dropTable(db, dropTable);
        LocalTackyEventHead_DB.dropTable(db, dropTable);
        LocalLocation_DB.dropTable(db, dropTable);

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
        assert sqlDB != null;
        sqlDB.delete(tableName, whereClause, whereArgs);
        sqlDB.close();
    }

    public <T> void insertValue(String tableName, T value, StoreCommand<T, ContentValues> storeCommand){
        if(sqLiteDatabase == null) {
            SQLiteDatabase sqlDB = db.getWritableDatabase();
            ContentValues cv = storeCommand.storeItem(value);

            assert sqlDB != null;
            sqlDB.insert(tableName, null, cv);
            sqlDB.close();
        }
        else {
            ContentValues cv = storeCommand.storeItem(value);
            sqLiteDatabase.insert(tableName, null, cv);
        }
    }

    public <T> T readValue(String query, ReadCommand<T, Cursor> readCommand){
        SQLiteDatabase db = this.getReadableDatabase();
        assert db != null;
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

    public <T> ArrayList<T> readValues(String query, ReadCommand<T, Cursor> readCommand){
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        assert sqlDB != null;
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

    public <T> void replaceValue(String tableName, T value, StoreCommand<T, ContentValues> storeCommand){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        ContentValues cv = storeCommand.storeItem(value);

        assert sqlDB != null;
        sqlDB.replace(tableName, null, cv); // replace entry if one already exists

        sqlDB.close();
    }

    public <T> void updateValue(String tableName, String whereClause, String[] whereArgs, T value, StoreCommand<T, ContentValues> storeCommand){
        SQLiteDatabase sqlDB = db.getWritableDatabase();
        ContentValues cv = storeCommand.storeItem(value);

        assert sqlDB != null;
        sqlDB.update(tableName, cv, whereClause, whereArgs);
        sqlDB.close();
    }


}