package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by user on 23/08/2016.
 */
public class BadApple extends GameObject implements Edible {

    public BadApple(Bitmap image, int x, int y, int width, int height){
        super(image, x, y, width, height);
    }

    public double adjustSpeed(){
        return 0.95;
    };

    public int adjustSnakeSize(){
        return -1;
    };

    public void adjustGame(Context context, Snake snake){
        snake.increaseSpeedCustom(adjustSpeed());

        if(adjustSnakeSize() < 0){
            for(int i = adjustSnakeSize(); i < 0; i++ ){
                snake.removeFromBody();
                System.out.println("Removing to body....");
            }
        }
    }
}
