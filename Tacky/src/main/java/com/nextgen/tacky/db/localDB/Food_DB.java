package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.db.LocalDatabase;

import java.util.ArrayList;

/**
 * Created by maes on 19/05/14.
 */
public class Food_DB {

    private static final String FOOD_TABLE = "foods";

    private static final String FOOD_NAME = "name";
    private static final String FOOD_VISUAL = "visualization";
    private static final String FOOD_ENERGY = "energyValue";
    private static final String FOOD_USES = "totalUses";

    private static final String FOOD_SQL_CREATE = "CREATE TABLE " + FOOD_TABLE + " (" +
            FOOD_NAME + " TEXT PRIMARY KEY," +
            FOOD_VISUAL + " TEXT," +
            FOOD_ENERGY + " INTEGER," +
            FOOD_USES + " INTEGER)";

    private static final String FOOD_SQL_SELECT_ALL = "SELECT * FROM " + FOOD_TABLE;

    private static LocalDatabase db = null;

    public Food_DB(Context ctx){
        if(db == null)
            db = LocalDatabase.getDatabase(ctx);
    }

    public static void initializeTable(SQLiteDatabase db) {
        db.execSQL(FOOD_SQL_CREATE);

        // basic food
        Food f1 = new Food("Bread", "brood", 10, -1);
        Food f2 = new Food("Orange juice", "orangejuice", 5, -1);
        Food f3 = new Food("Yoghurt", "yoghurt", 3, -1);
        // onCreate requires that we use the db object it provides
        // Otherwise we get 'getWritableDatabase called recursively' error
        //storeFood(f1, db);
        //storeFood(f2, db);
        //storeFood(f3, db);
    }

    public ArrayList<Food> getFoods() {
        return db.readValues(FOOD_SQL_SELECT_ALL, new ReadCommand<Food>(){
            @Override
            public Food readItem(Cursor cursor) {
                String name = cursor.getString(cursor.getColumnIndex(FOOD_NAME));
                String visual = cursor.getString(cursor.getColumnIndex(FOOD_VISUAL));
                int energyVal = cursor.getInt(cursor.getColumnIndex(FOOD_ENERGY));
                int totalUses = cursor.getInt(cursor.getColumnIndex(FOOD_USES));

                return new Food(name, visual, energyVal, totalUses);
            }
        });
    }


    public void storeFood(Food f) {
        db.replaceValue(FOOD_TABLE, f, new StoreCommand<Food>() {
            @Override
            public ContentValues storeItem(Food item) {
                ContentValues cv = new ContentValues();
                cv.put(FOOD_NAME, item.getName());
                cv.put(FOOD_VISUAL, item.getVisualization());
                cv.put(FOOD_ENERGY, item.getEnergyValue());
                cv.put(FOOD_USES, item.getTotalUses());

                return cv;
            }
        });
    }

    public void deleteFood(Food food) {
        db.deleteValue(FOOD_TABLE,
                       FOOD_NAME + "=?",
                       new String[]{food.getName()});
    }

}
