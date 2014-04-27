package com.nextgen.tacky.basicDisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

import com.nextgen.tacky.activities.rooms.MainRoom;
import com.nextgen.tacky.basic.Room;
import com.nextgen.tacky.basic.tacky.Tacky;

/**
 * Created by maes on 29/10/13.
 */
public class RoomDisplay {

    private Bitmap bitmap;
    private Tacky tacky;
    private Context context;
    private int imageResource;

    public RoomDisplay(Context context, Tacky tacky) {
        this.context = context;
        this.tacky = tacky;
        this.imageResource = context.getResources().getIdentifier(tacky.getRoomVisualization(), "drawable", MainRoom.MAIN_TACKY_PACKAGE);
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
    }

    public void display(Canvas canvas){
        int iR = context.getResources().getIdentifier(tacky.getRoomVisualization(), "drawable", MainRoom.MAIN_TACKY_PACKAGE);
        if(iR != imageResource) {
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), iR);
            this.imageResource = iR;
        }
        BitmapDrawable drawable = new BitmapDrawable(this.bitmap);
        drawable.setBounds(0,0,canvas.getWidth(), canvas.getHeight());
        drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        drawable.draw(canvas);
    }
}
