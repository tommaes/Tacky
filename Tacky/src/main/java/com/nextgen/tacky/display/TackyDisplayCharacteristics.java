package com.nextgen.tacky.display;

import android.graphics.Bitmap;

import com.nextgen.tacky.db.LocalDatabase;

/**
 * Created by maes on 14/11/13.
 */
public interface TackyDisplayCharacteristics {

    public Bitmap getFront(LocalDatabase db);
    public Bitmap getSide(LocalDatabase db);
    public Bitmap getSleep(LocalDatabase db);
    public Bitmap getUp(LocalDatabase db);
    public Bitmap getDown(LocalDatabase db);

}
