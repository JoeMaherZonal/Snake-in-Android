package uk.co.joemaher.projects.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Button extends GameObject {

    private long timeImageChanged;
    private Bitmap clickedImage;
    private boolean clicked;

    public Button(Bitmap mainImage, Bitmap clickedImage, int x, int y, int width, int height) {
        super(mainImage, x, y, width, height);
        this.clickedImage = clickedImage;
        this.clicked = false;
        this.timeImageChanged = System.nanoTime();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + GameController.WIDTH, y + GameController.HEIGHT, null);
        }
    }

    public void updateImage()
    {
        long elapsed = (System.nanoTime()-this.timeImageChanged)/1000000;

        if(elapsed>100 && this.clicked == true)
        {
            swapImages();
            this.clicked = false;
        }
    }

    public void unclick(){
        this.clicked = false;
    }


    public void swapImages(){
        Bitmap imageHolder = this.image;
        this.image = this.clickedImage;
        this.clickedImage = imageHolder;
        this.timeImageChanged = System.nanoTime();
    }

    @Override
    public void setImage(Bitmap image){
        this.image = image;
        this.timeImageChanged = System.nanoTime();
    }

    public void click(){
        this.clicked = true;
    }

    public boolean isClicked(){
        return this.clicked;
    }

}

