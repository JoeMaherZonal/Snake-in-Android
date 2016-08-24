package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by user on 23/08/2016.
 */
public class RedApple extends GameObject implements Edible {

    public RedApple(Bitmap image, int x, int y, int width, int height){
        super(image, x, y, width, height);
    }

    public double adjustSpeed(){
        return 0.98;
    };

    public int adjustSnakeSize(){
        return 2;
    };

    public void adjustGame(Context context, Snake snake){
        snake.increaseSpeedCustom(adjustSpeed());

        if(adjustSnakeSize() > 0){
            for(int i = 0; i < adjustSnakeSize(); i++ ){
                snake.addToBody(context);
            }
        }
    }
}
