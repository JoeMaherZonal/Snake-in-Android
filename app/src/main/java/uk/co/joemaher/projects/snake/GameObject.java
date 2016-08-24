package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by user on 19/08/2016.
 */
abstract class GameObject {
    protected Bitmap image;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GameObject(Bitmap image, int x, int y, int width, int height){
        this.image = Bitmap.createScaledBitmap(image,width, height, true);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setImage(Bitmap image){
        this.image = Bitmap.createScaledBitmap(image,width, height, true);
    }

    public Bitmap getImage(){
        return this.image;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.y = newY;
    }

    public void setWidth(int newWidth){
        this.width = newWidth;
    }

    public void setHeight(int newHeight){
        this.height = newHeight;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public Rect getRectangle()
    {
        Rect gameObjectRectangle = new Rect(x, y, x+width, y+height);
        return gameObjectRectangle;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
        if(x<0){
            canvas.drawBitmap(image, x+GameController.WIDTH, y+GameController.HEIGHT, null);
        }
    }
}
