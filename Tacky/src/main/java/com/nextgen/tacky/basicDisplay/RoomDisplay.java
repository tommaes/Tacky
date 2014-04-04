package com.nextgen.tacky.basicDisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import com.nextgen.tacky.activities.rooms.MainRoom;
import com.nextgen.tacky.basic.Room;

/**
 * Created by maes on 29/10/13.
 */
public class RoomDisplay {

    private Room room;
    private Bitmap bitmap;

    public RoomDisplay(Context context, Room r) {
        this.newRoom(context, r);
    }

    public void display(Canvas canvas, int width, int height){
        BitmapDrawable drawable = new BitmapDrawable(this.bitmap);
        drawable.setBounds(0,0,width, height);
        drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        drawable.draw(canvas);
    }

    public void newRoom(Context context, Room r) {
        this.room = r;
        int imageRescource = context.getResources().getIdentifier(this.room.getVisualization(), "drawable", MainRoom.MAIN_TACKY_PACKAGE);
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), imageRescource);
    }
}
