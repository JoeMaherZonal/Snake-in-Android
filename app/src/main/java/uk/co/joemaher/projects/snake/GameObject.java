package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
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
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.x = newY;
    }

    public void setWidth(int newWidth){
        this.x = newWidth;
    }

    public void setHeight(int newHeight){
        this.x = newHeight;
    }

    public int getX(){
        return this.x;
    }

    public int getY(int newY){
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
}
