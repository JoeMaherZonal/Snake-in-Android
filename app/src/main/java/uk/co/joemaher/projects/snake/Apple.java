package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by user on 23/08/2016.
 */
public class Apple extends GameObject implements Edible {

    public Apple(Bitmap image, int x, int y, int width, int height){
        super(image, x, y, width, height);
    }

    public double adjustSpeed(){
        return 0.99;
    };

    public int adjustSnakeSize(){
        return 1;
    };

    public void adjustGame(Context context, Snake snake){
        snake.increaseSpeedCustom(adjustSpeed());

        if(adjustSnakeSize() > 0){
            for(int i = 0; i < adjustSnakeSize(); i++ ){
                snake.addToBody(context);
                System.out.println("Adding to body....");
            }
        }
    }
}
