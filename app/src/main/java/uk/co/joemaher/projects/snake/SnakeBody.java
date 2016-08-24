package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SnakeBody extends GameObject {

    DirectionType currentDirection;

    public SnakeBody(Bitmap image, int x, int y, int width, int height, DirectionType currentDirection) {
        super(image, x, y, width, height);
        this.currentDirection = currentDirection;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GameController.WIDTH, y + GameController.HEIGHT, null);
        }
    }

    public DirectionType getCurrentDirection(){
        return currentDirection;
    }

    public void setCurrentDirection(DirectionType newCurrentDirection){
        this.currentDirection = newCurrentDirection;
    }
}