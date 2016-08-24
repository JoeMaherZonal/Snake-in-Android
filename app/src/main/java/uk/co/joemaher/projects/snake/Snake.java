package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Snake extends GameObject {

    private ArrayList<SnakeBody> body;
    private DirectionType currentDirection;
    private Double speed;
    private long timeSinceUpdate;
    private Bitmap upImage;
    private Bitmap downImage;
    private Bitmap leftImage;
    private Bitmap rightImage;
    private int moveUnit;


    public Snake(Bitmap image, Bitmap upImage, Bitmap downImage, Bitmap leftImage, Bitmap rightImage, int x, int y, int width, int height, int moveUnit) {
        super(image, x, y, width, height);
        this.body = new ArrayList<SnakeBody>();
        this.currentDirection = DirectionType.UP;
        this.speed = 500.0;
        this.upImage = Bitmap.createScaledBitmap(upImage,width, height, true);
        this.downImage = Bitmap.createScaledBitmap(downImage,width, height, true);
        this.rightImage = Bitmap.createScaledBitmap(rightImage,width, height, true);
        this.leftImage = Bitmap.createScaledBitmap(leftImage,width, height, true);
        this.moveUnit = moveUnit;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
        for(int i = 0; i < body.size();i++){
            canvas.drawBitmap(body.get(i).getImage(), body.get(i).getX(), body.get(i).getY(), null);
        }
    }

    public int getMoveUnit(){
        return this.moveUnit;
    }

    public void update(Context context) {
        long elapsed = (System.nanoTime() - this.timeSinceUpdate) / 1000000;
        //controls snakes 'flow'
        if (elapsed > speed) {
            switch (this.currentDirection) {
                case UP:
                    updateBody(context);
                    setY(getY() - moveUnit);
                    break;
                case DOWN:
                    updateBody(context);
                    setY(getY() + moveUnit);
                    break;
                case LEFT:
                    updateBody(context);
                    setX(getX() - moveUnit);
                    break;
                case RIGHT:
                    updateBody(context);
                    setX(getX() + moveUnit);
                    break;
            }
            this.timeSinceUpdate = System.nanoTime();
        }
    }

    public void updateBody(Context context){
        if(body.size() == 0){return;}
        DirectionType directionHolder = currentDirection;
        int xHolder = this.x;
        int yHolder = this.y;
        Bitmap imageHolder = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_body),width, height, true);
        DirectionType directionHolder2 = currentDirection;
        int xHolder2;
        int yHolder2;
        Bitmap imageHolder2;

        for(int i = 0; i<body.size();i++){
            if(i == 0){
                xHolder = body.get(i).getX();
                yHolder = body.get(i).getY();
                directionHolder = body.get(i).getCurrentDirection();
                imageHolder = body.get(i).getImage();
                body.get(i).setX(this.x);
                body.get(i).setY(this.y);
                body.get(i).setCurrentDirection(this.currentDirection);
                body.get(i).setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_body));
            }else {
                xHolder2 = body.get(i).getX();
                yHolder2 = body.get(i).getY();
                directionHolder2 = body.get(i).getCurrentDirection();
                imageHolder2 = body.get(i).getImage();
                body.get(i).setX(xHolder);
                body.get(i).setY(yHolder);
                body.get(i).setCurrentDirection(directionHolder);
                body.get(i).setImage(imageHolder);
                xHolder = xHolder2;
                yHolder = yHolder2;
                directionHolder = directionHolder2;
                imageHolder = imageHolder2;
            }
        }
    }

    public void setDirection(DirectionType direction) {
        this.currentDirection = direction;
        alignHeadImage();
    }

    public void alignHeadImage() {
        switch (this.currentDirection) {
            case UP:
                this.image = upImage;
                break;
            case DOWN:
                this.image = downImage;
                break;
            case LEFT:
                this.image = leftImage;
                break;
            case RIGHT:
                this.image = rightImage;
                break;
        }
    }

    public ArrayList<SnakeBody> getBody() {
        return this.body;
    }

    public void removeFromBody(){
        if(body.size() > 0) {
            this.body.remove(body.size() - 1);
        }
    }

    public void addToBody(Context context) {
        int x = 0;
        int y = 0;
        DirectionType direction = currentDirection;

        if (body.size() == 0) {
            switch (currentDirection) {
                case UP:
                    y = getY() + moveUnit;
                    x = getX();
                    break;
                case DOWN:
                    y = getY() - moveUnit;
                    x = getX();
                    break;
                case LEFT:
                    y = getY();
                    x = getX() + moveUnit;
                    break;
                case RIGHT:
                    y = getY();
                    x = getX() - moveUnit;
                    break;
            }
        } else {
            SnakeBody lastBodyPart = getBody().get(getBody().size() - 1);
            direction = lastBodyPart.getCurrentDirection();
                switch (direction) {
                    case UP:
                        y = lastBodyPart.getY() + moveUnit;
                        x = lastBodyPart.getX();
                        break;
                    case DOWN:
                        y = lastBodyPart.getY() - moveUnit;
                        x = lastBodyPart.getX();
                        break;
                    case LEFT:
                        y = lastBodyPart.getY();
                        x = lastBodyPart.getX() + moveUnit;
                        break;
                    case RIGHT:
                        y = lastBodyPart.getY();
                        x = lastBodyPart.getX() - moveUnit;
                        break;
                }
        }
        this.body.add(new SnakeBody(BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_body), x, y, 50, 50, direction));
        this.body.get(0).setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_body_full));
    }


    public void checkForCollisionWithItem(ItemBag itemBag, Context context){
        for(int i = 0; i < itemBag.getBag().size(); i++) {
            if (collision(this, (GameObject) itemBag.getBag().get(i))) {
                String className = String.valueOf(itemBag.getBag().get(i).getClass().getName());
//                Log.d("Snake", className);
                switch (className) {
                    case "uk.co.joemaher.projects.snake.Apple":
                        itemBag.getBag().get(i).adjustGame(context, this);
                        itemBag.removeItem(i);
                        break;
                    case "uk.co.joemaher.projects.snake.BadApple":
                        itemBag.getBag().get(i).adjustGame(context, this);
                        itemBag.removeItem(i);
                        break;
                    case "uk.co.joemaher.projects.snake.RedApple":
                        itemBag.getBag().get(i).adjustGame(context, this);
                        itemBag.removeItem(i);
                        break;
                }
            }
        }
    }

    public void increaseSpeedCustom(Double speed){
        this.speed = this.speed * speed;
    }

    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(), b.getRectangle()))
        {
            return true;
        }
        return false;
    }

    public double getSpeed(){
        int x = (int)Math.round(this.speed);
        //some math to represent a score that looks normally progressive
        return (500 - x) / 5 + 1;
    }

    public int getScore(){
        int score = (int)(body.size() * getSpeed());
        return score;
    }
}