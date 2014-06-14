package com.nextgen.tacky.display;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by maes on 14/06/14.
 */
public class DisplayFactory {

    private static Map<String, Bitmap> bitmaps;
    private Context context;

    public DisplayFactory(Context context){
        this.context = context;
        if(bitmaps == null)
            bitmaps = new Hashtable<String, Bitmap>();
    }

    public Bitmap getImage(DisplayItem displayItem){
        if(bitmaps.containsKey(displayItem.getDisplayItemName()))
            return bitmaps.get(displayItem.getDisplayItemName());
        else {
            Bitmap bitmap = decodeImage(displayItem.getDisplayItemName());
            bitmaps.put(displayItem.getDisplayItemName(), bitmap);
            return bitmap;
        }
    }

    public Bitmap getImage(DisplayItem displayItem, Matrix matrix){
        Bitmap bitmap = this.getImage(displayItem);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private Bitmap decodeImage(String name) {
        int imageResource = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), imageResource);
    }

}
