package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by user on 20/08/2016.
 */
public class Item extends GameObject {


    public Item(Bitmap image, int x, int y, int width, int height){
        super(image, x, y, width, height);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
        if(x<0){
            canvas.drawBitmap(image, x+GameController.WIDTH, y+GameController.HEIGHT, null);
        }
    }
}
