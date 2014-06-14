package com.nextgen.tackyTests.unitTests.db;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.db.localDB.LocalFood_DB;

import java.util.ArrayList;

/**
 * Created by maes on 6/06/14.
 */
public class LocalFood_DBTest extends AndroidTestCase {

    private LocalFood_DB localFood_db;

    private String name = "name";
    private String visual = "visual";
    private int value = 50;
    // when uses is > 1, testCanStillBeUsed() won't work anymore
    private int uses = 1;

    public void setUp(){
        RenamingDelegatingContext renamingDelegatingContext = new RenamingDelegatingContext(getContext(), "test_DB_");
        localFood_db = new LocalFood_DB(renamingDelegatingContext);
    }

    public void testAddAndReadFood() throws Exception {
        Food food = new Food(name, visual, value, uses);

        // add food to database
        localFood_db.storeFood(food);

        // read all food from database
        ArrayList<Food> foods = localFood_db.getFoods();

        boolean contains = false;
        for(Food f : foods){
            if(f.getName().equals(name)) {
                contains = true;
                break;
            }
        }
        assertTrue(contains);
    }

    public void testAddAndRemoveFood() throws Exception {
        Food food = new Food(name, visual, value, uses);

        // add food to database
        localFood_db.storeFood(food);

        // delete food from database
        localFood_db.deleteFood(food);

        // read all food from database
        ArrayList<Food> foods = localFood_db.getFoods();

        boolean contains = false;
        for(Food f : foods){
            if(f.getName().equals(name)) {
                contains = true;
                break;
            }
        }
        assertFalse(contains);
    }


}
