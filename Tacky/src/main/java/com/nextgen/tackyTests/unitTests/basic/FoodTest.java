package com.nextgen.tackyTests.unitTests.basic;

import android.os.Parcel;

import com.nextgen.tacky.basic.Food;

import junit.framework.TestCase;

/**
 * Created by maes on 3/05/14.
 */
public class FoodTest extends TestCase {

    private String name = "name";
    private String visual = "visual";
    private int value = 50;
    // when uses is > 1, testCanStillBeUsed() won't work anymore
    private int uses = 1;

    public void testGetName() throws Exception {

        Food food = new Food(name, visual, value, uses);
        assertEquals(name, food.getName());
    }

    public void testGetVisual() throws Exception {

        Food food = new Food(name, visual, value, uses);
        assertEquals(visual, food.getVisualization());
    }

    public void testGetEnergyValue() throws Exception {

        Food food = new Food(name, visual, value, uses);
        assertEquals(value, food.getEnergyValue());
    }

    public void testTotalUsesAndCanStillBeUsed() throws Exception {

        Food food = new Food(name, visual, value, uses);

        assertEquals(uses, food.getTotalUses());

        food.used();
        assertFalse(food.canStillBeUsed());
    }

    public void testParcel() throws Exception {

        Parcel parcel = Parcel.obtain();
        Food food = new Food(name, visual, value, uses);
        food.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Food parcelFood = Food.CREATOR.createFromParcel(parcel);

        assertEquals(name, parcelFood.getName());
        assertEquals(visual, parcelFood.getVisualization());
        assertEquals(value, parcelFood.getEnergyValue());
        assertEquals(uses, parcelFood.getTotalUses());
    }
}
