package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nextgen.tacky.db.generic.ReadCommand;
import com.nextgen.tacky.db.generic.StoreCommand;
import com.nextgen.tacky.display.TackyHead;

import java.util.ArrayList;

/**
 * Created by maes on 23/05/14.
 */
public class TackyHead_DB {

    private static LocalDatabase db = null;
    private Context context;

    private static final String[] colors = {"green", "orange", "pink", "red", "turquoise", "yellow"};

    private static final String HEAD_TABLE = "heads";

    private static final String HEAD_ID = "id";
    private static final String HEAD_BONNET = "bonnet";
    private static final String HEAD_NORMAL = "normal";
    private static final String HEAD_UP = "up";
    private static final String HEAD_DOWN = "down";

    private static final String HEAD_SQL_CREATE = "CREATE TABLE " + HEAD_TABLE + " (" +
            HEAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            HEAD_BONNET + " TEXT," +
            HEAD_NORMAL + " TEXT," +
            HEAD_UP + " TEXT," +
            HEAD_DOWN + " TEXT)";

    private static final String HEAD_SQL_SELECT_ALL = "SELECT * FROM " + HEAD_TABLE;

    private static final String HEAD_SQL_SELECT_HEAD = "SELECT * FROM " + HEAD_TABLE +
                                                       " WHERE " + HEAD_ID + " = '%s'";

    public TackyHead_DB(Context ctx){
        if(db == null)
            db = LocalDatabase.getDatabase(ctx);
        context = ctx;
    }

    public static void initializeTable(LocalDatabase db) {
        db.addTable(HEAD_SQL_CREATE);

        for (String color : colors) {
            final String normal = "head_normal_" + color;
            final String bonnet = "bonnet_head_normal_" + color;
            final String up = "head_up_" + color;
            final String down = "head_down_" + color;
            TackyHead tackyHead = new TackyHead(normal, bonnet, up, down, 0);
            db.insertValue(HEAD_TABLE, tackyHead, new StoreCommand<TackyHead, ContentValues>() {
                @Override
                public ContentValues storeItem(TackyHead item) {
                    ContentValues cv = new ContentValues();
                    cv.put(HEAD_BONNET, bonnet);
                    cv.put(HEAD_NORMAL, normal);
                    cv.put(HEAD_UP, up);
                    cv.put(HEAD_DOWN, down);

                    return cv;
                }
            });
        }

    }

    public ArrayList<TackyHead> getHeads() {
        return db.readValues(HEAD_SQL_SELECT_ALL, new ReadCommand<TackyHead, Cursor>() {
            @Override
            public TackyHead readItem(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndex(HEAD_ID));
                String bonnet = cursor.getString(cursor.getColumnIndex(HEAD_BONNET));
                String normal = cursor.getString(cursor.getColumnIndex(HEAD_NORMAL));
                String up = cursor.getString(cursor.getColumnIndex(HEAD_UP));
                String down = cursor.getString(cursor.getColumnIndex(HEAD_DOWN));

                return new TackyHead(normal, bonnet, up, down, id);
            }
        });
    }

    public TackyHead getHead(int id)  {
        String query = String.format(HEAD_SQL_SELECT_HEAD, String.valueOf(id));
        return db.readValue(query, new ReadCommand<TackyHead, Cursor>() {
            @Override
            public TackyHead readItem(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndex(HEAD_ID));
                String bonnet = cursor.getString(cursor.getColumnIndex(HEAD_BONNET));
                String normal = cursor.getString(cursor.getColumnIndex(HEAD_NORMAL));
                String up = cursor.getString(cursor.getColumnIndex(HEAD_UP));
                String down = cursor.getString(cursor.getColumnIndex(HEAD_DOWN));
                return new TackyHead(normal, bonnet, up, down, id);
            }
        });
    }

    public Bitmap decodeImage(String name) {
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), imageResource);
    }

    public static void dropTable(LocalDatabase db, String dropTable) {
        db.dropTable(dropTable + HEAD_TABLE);
    }
}
