package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by user on 19/08/2016.
 */
public class WallBlock extends GameObject implements Drawabale {

    public WallBlock(Bitmap image, int x, int y, int width, int height){
        super(image, x, y, width, height);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
        if(x<0){
            canvas.drawBitmap(image, x+GameController.WIDTH, y+GameController.HEIGHT, null);
        }
    }
}
