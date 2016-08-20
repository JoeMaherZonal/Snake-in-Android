package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Snake extends GameObject implements Drawabale {

    private ArrayList<SnakeBody> body;
    private DirectionType currentDirection;
    private Double speed;
    private long timeSinceUpdate;
    private Bitmap upImage;
    private Bitmap downImage;
    private Bitmap leftImage;
    private Bitmap rightImage;

    public Snake(Bitmap image, Bitmap upImage, Bitmap downImage, Bitmap leftImage, Bitmap rightImage, int x, int y, int width, int height) {
        super(image, x, y, width, height);
        this.body = new ArrayList<SnakeBody>();
        this.currentDirection = DirectionType.UP;
        this.speed = 500.0;
        this.upImage = upImage;
        this.downImage = downImage;
        this.rightImage = rightImage;
        this.leftImage = leftImage;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GameController.WIDTH, y + GameController.HEIGHT, null);
        }
    }

    public void update(){
        long elapsed = (System.nanoTime()-this.timeSinceUpdate)/1000000;

        if(elapsed>speed)
        {
            if(currentDirection == DirectionType.UP){
                setY(getY() - 100);
            }
            if(currentDirection == DirectionType.DOWN){
                setY(getY() + 100);
            }
            if(currentDirection == DirectionType.LEFT){
                setX(getX() - 100);
            }
            if(currentDirection == DirectionType.RIGHT){
                setX(getX() + 100);
            }

            this.timeSinceUpdate = System.nanoTime();
        }
    }

    public void setDirection(DirectionType direction){
        this.currentDirection = direction;
        alignHeadImage();
    }

    public void alignHeadImage(){
        if(this.currentDirection == DirectionType.UP){
            this.image = upImage;
        }
        if(this.currentDirection == DirectionType.DOWN){
            this.image = downImage;
        }
        if(this.currentDirection == DirectionType.LEFT){
            this.image = leftImage;
        }
        if(this.currentDirection == DirectionType.RIGHT){
            this.image = rightImage;
        }
    }
}