package com.nextgen.tacky.db;

import android.content.Context;

import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.db.localDB.LocalFood_DB;

import java.util.ArrayList;

/**
 * Created by maes on 14/06/14.
 */
public class Food_DB {

    private LocalFood_DB localFood_db;

    public Food_DB(Context context){
        localFood_db = new LocalFood_DB(context);
    }

    public ArrayList<Food> getFoods() {
        return localFood_db.getFoods();
    }

    public void storeFood(Food f) {
        localFood_db.storeFood(f);
    }

    public void deleteFood(Food food) {
        localFood_db.deleteFood(food);
    }
}
