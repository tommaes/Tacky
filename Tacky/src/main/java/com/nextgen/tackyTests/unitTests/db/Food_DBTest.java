package com.nextgen.tackyTests.unitTests.db;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.nextgen.tacky.basic.Food;
import com.nextgen.tacky.db.localDB.Food_DB;

import java.util.ArrayList;

/**
 * Created by maes on 6/06/14.
 */
public class Food_DBTest extends AndroidTestCase {

    private Food_DB food_db;

    private String name = "name";
    private String visual = "visual";
    private int value = 50;
    // when uses is > 1, testCanStillBeUsed() won't work anymore
    private int uses = 1;

    public void setUp(){
        RenamingDelegatingContext renamingDelegatingContext = new RenamingDelegatingContext(getContext(), "test_DB_");
        food_db = new Food_DB(renamingDelegatingContext);
    }

    public void testAddAndReadFood() throws Exception {
        Food food = new Food(name, visual, value, uses);

        // add food to database
        food_db.storeFood(food);

        // read all food from database
        ArrayList<Food> foods = food_db.getFoods();

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
        food_db.storeFood(food);

        // delete food from database
        food_db.deleteFood(food);

        // read all food from database
        ArrayList<Food> foods = food_db.getFoods();

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
