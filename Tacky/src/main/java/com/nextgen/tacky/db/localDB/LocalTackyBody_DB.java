package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nextgen.tacky.db.generic.ReadCommand;
import com.nextgen.tacky.db.generic.StoreCommand;
import com.nextgen.tacky.display.TackyBody;

import java.util.ArrayList;

/**
 * Created by maes on 23/05/14.
 */
public class LocalTackyBody_DB {

    private static LocalDatabase db = null;
    private Context context;

    private static final String[] colors = {"green", "orange", "pink", "red", "turquoise", "yellow"};

    private static final String BODY_TABLE = "bodies";

    private static final String BODY_ID = "id";
    private static final String BODY_VISUAL = "visualization";

    private static final String BODY_SQL_CREATE = "CREATE TABLE " + BODY_TABLE + " (" +
            BODY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BODY_VISUAL + " TEXT)";

    private static final String BODY_SQL_SELECT_ALL = "SELECT * FROM " + BODY_TABLE;

    private static final String BODY_SQL_SELECT_BODY = "SELECT * FROM " + BODY_TABLE +
                                                        " WHERE " + BODY_ID + " = '%s'";

    public LocalTackyBody_DB(Context ctx){
        if(db == null)
            db = LocalDatabase.getDatabase(ctx);
        context = ctx;
    }

    public static void initializeTable(LocalDatabase db) {
        db.addTable(BODY_SQL_CREATE);
        for (String color : colors) {
            final String visual = "body_" + color;
            db.insertValue(BODY_TABLE, null, new StoreCommand<TackyBody, ContentValues>() {
                @Override
                public ContentValues storeItem(TackyBody item) {
                    ContentValues cv = new ContentValues();
                    cv.put(BODY_VISUAL, visual);
                    return cv;
                }
            });
        }
    }

    public ArrayList<TackyBody> getBodies() {
        return db.readValues(BODY_SQL_SELECT_ALL, new ReadCommand<TackyBody, Cursor>() {
            @Override
            public TackyBody readItem(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndex(BODY_ID));
                String visual = cursor.getString(cursor.getColumnIndex(BODY_VISUAL));

                return new TackyBody(visual, id);
            }
        });
    }

    public TackyBody getBody(int id)  {
        String query = String.format(BODY_SQL_SELECT_BODY, String.valueOf(id));
        return db.readValue(query, new ReadCommand<TackyBody, Cursor>() {
            @Override
            public TackyBody readItem(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndex(BODY_ID));
                String vis = cursor.getString(cursor.getColumnIndex(BODY_VISUAL));
                return new TackyBody(vis, id);
            }
        });
    }

    public static void dropTable(LocalDatabase db, String dropTable) {
        db.dropTable(dropTable + BODY_TABLE);
    }
}
