package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nextgen.tacky.db.generic.ReadCommand;
import com.nextgen.tacky.db.generic.StoreCommand;
import com.nextgen.tacky.display.Expression;
import com.nextgen.tacky.display.TackyExpression;

import java.util.ArrayList;

/**
 * Created by maes on 29/05/14.
 */
public class TackyExpression_DB {

    private static LocalDatabase db = null;
    private Context context;

    private static final String[] colors = {"green", "orange", "pink", "red", "turquoise", "yellow"};

    private static final String EXPRESSION_TABLE = "expressions";

    private static final String EXPRESSION_ID = "id";
    private static final String EXPRESSION_FRONT_HAPPY = "fronthappy";
    private static final String EXPRESSION_FRONT_NORMAL = "frontnormal";
    private static final String EXPRESSION_FRONT_UNHAPPY = "frontunhappy";
    private static final String EXPRESSION_SIDE_HAPPY = "sidehappy";
    private static final String EXPRESSION_SIDE_NORMAL = "sidenormal";
    private static final String EXPRESSION_SIDE_UNHAPPY = "sideunhappy";
    private static final String EXPRESSION_SLEEP = "sleep";
    private static final String EXPRESSION_SLEEP_SMILE = "sleepsmile";

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

    private static final String EXPRESSION_SQL_SELECT_ALL = "SELECT * FROM " + EXPRESSION_TABLE;

    private static final String EXPRESSION_SQL_SELECT_EXPRESSION = "SELECT * FROM " + EXPRESSION_TABLE +
                                                                   " WHERE " + EXPRESSION_ID + " = '%s'";

    public TackyExpression_DB(Context ctx){
        if(db == null)
            db = LocalDatabase.getDatabase(ctx);
        context = ctx;
    }

    public static void initializeTable(LocalDatabase db) {
        db.addTable(EXPRESSION_SQL_CREATE);

        for (String color : colors) {
            final String fhappy = "front_happy_" + color;
            final String fnormal = "front_normal_" + color;
            final String funhappy = "front_unhappy_" + color;
            final String shappy = "side_happy_" + color;
            final String snormal = "side_normal_" + color;
            final String sunhappy = "side_unhappy_" + color;
            final String sleep = "sleep_normal_" + color;
            final String sleepsmile = "sleep_normal_smile_" + color;
            db.insertValue(EXPRESSION_TABLE, null, new StoreCommand<TackyExpression, ContentValues>() {
                @Override
                public ContentValues storeItem(TackyExpression item) {
                    ContentValues cv = new ContentValues();
                    cv.put(EXPRESSION_FRONT_HAPPY, fhappy);
                    cv.put(EXPRESSION_FRONT_NORMAL, fnormal);
                    cv.put(EXPRESSION_FRONT_UNHAPPY, funhappy);
                    cv.put(EXPRESSION_SIDE_HAPPY, shappy);
                    cv.put(EXPRESSION_SIDE_NORMAL, snormal);
                    cv.put(EXPRESSION_SIDE_UNHAPPY, sunhappy);
                    cv.put(EXPRESSION_SLEEP, sleep);
                    cv.put(EXPRESSION_SLEEP_SMILE, sleepsmile);
                    return cv;
                }
            });
        }

    }

    public ArrayList<TackyExpression> getExpressions() {
        return db.readValues(EXPRESSION_SQL_SELECT_ALL, new ReadCommand<TackyExpression, Cursor>() {
            @Override
            public TackyExpression readItem(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndex(EXPRESSION_ID));
                String frontHappy = cursor.getString(cursor.getColumnIndex(EXPRESSION_FRONT_HAPPY));
                String frontNormal = cursor.getString(cursor.getColumnIndex(EXPRESSION_FRONT_NORMAL));
                String frontSad = cursor.getString(cursor.getColumnIndex(EXPRESSION_FRONT_UNHAPPY));
                String sideHappy = cursor.getString(cursor.getColumnIndex(EXPRESSION_SIDE_HAPPY));
                String sideNormal = cursor.getString(cursor.getColumnIndex(EXPRESSION_SIDE_NORMAL));
                String sideSad = cursor.getString(cursor.getColumnIndex(EXPRESSION_SIDE_UNHAPPY));
                String sleep = cursor.getString(cursor.getColumnIndex(EXPRESSION_SLEEP));

                Expression front = new Expression(frontHappy, frontNormal, frontSad);
                Expression side = new Expression(sideHappy, sideNormal, sideSad);
                Expression sleepExpression = new Expression(sleep);

                return new TackyExpression(front, side, sleepExpression, id);
            }
        });
    }

    public TackyExpression getExpression(int id)  {
        String query = String.format(EXPRESSION_SQL_SELECT_EXPRESSION, String.valueOf(id));
        return db.readValue(query, new ReadCommand<TackyExpression, Cursor>() {
            @Override
            public TackyExpression readItem(Cursor cursor) {
                int id = cursor.getInt(cursor.getColumnIndex(EXPRESSION_ID));
                String frontHappy = cursor.getString(cursor.getColumnIndex(EXPRESSION_FRONT_HAPPY));
                String frontNormal = cursor.getString(cursor.getColumnIndex(EXPRESSION_FRONT_NORMAL));
                String frontSad = cursor.getString(cursor.getColumnIndex(EXPRESSION_FRONT_UNHAPPY));
                String sideHappy = cursor.getString(cursor.getColumnIndex(EXPRESSION_SIDE_HAPPY));
                String sideNormal = cursor.getString(cursor.getColumnIndex(EXPRESSION_SIDE_NORMAL));
                String sideSad = cursor.getString(cursor.getColumnIndex(EXPRESSION_SIDE_UNHAPPY));
                String sleep = cursor.getString(cursor.getColumnIndex(EXPRESSION_SLEEP));

                Expression front = new Expression(frontHappy, frontNormal, frontSad);
                Expression side = new Expression(sideHappy, sideNormal, sideSad);
                Expression sleepExpression = new Expression(sleep);

                return new TackyExpression(front, side, sleepExpression, id);
            }
        });
    }

    public Bitmap decodeImage(String name) {
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), imageResource);
    }

    public static void dropTable(LocalDatabase db, String dropTable) {
        db.dropTable(dropTable + EXPRESSION_TABLE);
    }
}
