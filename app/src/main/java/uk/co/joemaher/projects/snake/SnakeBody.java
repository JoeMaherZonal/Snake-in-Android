package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SnakeBody extends GameObject {

    DirectionType currentDirection;

    public SnakeBody(Bitmap image, int x, int y, int width, int height, DirectionType currentDirection) {
        super(image, x, y, width, height);
        this.currentDirection = currentDirection;
    }


    public DirectionType getCurrentDirection(){
        return currentDirection;
    }

    public void setCurrentDirection(DirectionType newCurrentDirection){
        this.currentDirection = newCurrentDirection;
    }
}