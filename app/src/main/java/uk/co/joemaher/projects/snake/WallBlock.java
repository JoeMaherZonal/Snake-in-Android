package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by user on 19/08/2016.
 */
public class WallBlock extends GameObject {

    Boolean isSafe;

    public WallBlock(Bitmap image, int x, int y, int width, int height){
        super(image, x, y, width, height);
        this.isSafe = true;
    }

    public void makeUnsafe(Context context){
        this.isSafe = false;
        super.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.unsafe_wall));
    }

    public boolean isSafe(){
        return this.isSafe;
    }

}
