package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
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
        for(int i = 0; i < body.size();i++){
            canvas.drawBitmap(body.get(i).getImage(), body.get(i).getX(), body.get(i).getY(), null);
        }
//        if (x < 0) {
//            canvas.drawBitmap(image, x + GameController.WIDTH, y + GameController.HEIGHT, null);
//        }
    }

    public void update() {
        long elapsed = (System.nanoTime() - this.timeSinceUpdate) / 1000000;
        Snake holder = this;
        SnakeBody bodyHolder;

        if (elapsed > speed) {
            if (currentDirection == DirectionType.UP) {
                updateBody();
                setY(getY() - 100);
            }
            if (currentDirection == DirectionType.DOWN) {
                updateBody();
                setY(getY() + 100);
            }
            if (currentDirection == DirectionType.LEFT) {
                updateBody();
                setX(getX() - 100);
            }
            if (currentDirection == DirectionType.RIGHT) {
                updateBody();
                setX(getX() + 100);
            }
            this.timeSinceUpdate = System.nanoTime();
        }
    }

    public void updateBody(){
        if(body.size() == 0){return;}
        DirectionType directionHolder = currentDirection;
        int xHolder = this.x;
        int yHolder = this.y;
        DirectionType directionHolder2 = currentDirection;
        int xHolder2;
        int yHolder2;

        for(int i = 0; i<body.size();i++){
            if(i == 0){
                xHolder = body.get(i).getX();
                yHolder = body.get(i).getY();
                directionHolder = body.get(i).getCurrentDirection();
                body.get(i).setX(this.x);
                body.get(i).setY(this.y);
                body.get(i).setCurrentDirection(this.currentDirection);
            }else {
                xHolder2 = body.get(i).getX();
                yHolder2 = body.get(i).getY();
                directionHolder2 = body.get(i).getCurrentDirection();
                body.get(i).setX(xHolder);
                body.get(i).setY(yHolder);
                body.get(i).setCurrentDirection(directionHolder);
                xHolder = xHolder2;
                yHolder = yHolder2;
                directionHolder = directionHolder2;
            }
        }
    }

    public void setDirection(DirectionType direction) {
        this.currentDirection = direction;
        alignHeadImage();
    }

    public void alignHeadImage() {
        if (this.currentDirection == DirectionType.UP) {
            this.image = upImage;
        }
        if (this.currentDirection == DirectionType.DOWN) {
            this.image = downImage;
        }
        if (this.currentDirection == DirectionType.LEFT) {
            this.image = leftImage;
        }
        if (this.currentDirection == DirectionType.RIGHT) {
            this.image = rightImage;
        }
    }

    public ArrayList<SnakeBody> getBody() {
        return this.body;
    }

    public void addToBody(Context context) {
        int x = 0;
        int y = 0;
        DirectionType direction = currentDirection;
        if (body.size() == 0) {
            switch (currentDirection) {
                case UP:
                    y = getY() + 100;
                    x = getX();
                    break;
                case DOWN:
                    y = getY() - 100;
                    x = getX();
                    break;
                case LEFT:
                    y = getY();
                    x = getX() + 100;
                    break;
                case RIGHT:
                    y = getY();
                    x = getX() - 100;
                    break;
            }
        } else {
            SnakeBody lastBodyPart = getBody().get(getBody().size() - 1);
            direction = lastBodyPart.getCurrentDirection();
                switch (direction) {
                    case UP:
                        y = lastBodyPart.getY() + 100;
                        x = lastBodyPart.getX();
                        break;
                    case DOWN:
                        y = lastBodyPart.getY() - 100;
                        x = lastBodyPart.getX();
                        break;
                    case LEFT:
                        y = lastBodyPart.getY();
                        x = lastBodyPart.getX() + 100;
                        break;
                    case RIGHT:
                        y = lastBodyPart.getY();
                        x = lastBodyPart.getX() - 100;
                        break;
                }
        }
        this.body.add(new SnakeBody(BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_body), x, y, 100, 100, direction));
    }

    public void checkforWallCollisions(){
        if(this.getY() < 200){
            this.setY(this.getY() + 1400);
            return;
        }
        if(this.getY() > 1500) {
            this.setY(this.getY() - 1400);
            return;
        }
        if(this.getX() < 200){
            this.setX(this.getX() + 1800);
            return;
        }
        if(this.getX() > 1900){
            this.setX(this.getX() - 1800);
            return;
        }
    }

    public void checkForCollisionWithItem(ItemBag itemBag, Context context){
        for(int i = 0; i < itemBag.getBag().size(); i++){
            if(collision(this, itemBag.getBag().get(i))){
                this.addToBody(context);
                itemBag.removeItem(i);
                increaseSpeed();
            }
        }
    }


    public void increaseSpeed(){
        this.speed = this.speed * 0.99;
    }

    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(), b.getRectangle()))
        {
            return true;
        }
        return false;
    }
}