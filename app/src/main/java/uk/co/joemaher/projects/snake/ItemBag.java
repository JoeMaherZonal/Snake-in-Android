package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 20/08/2016.
 */
public class ItemBag {
    private ArrayList<Edible> bag;

    public ItemBag(){
        this.bag = new ArrayList<Edible>();
    }

    public void addApple(Context context,int x, int y){
        this.bag.add(new Apple(BitmapFactory.decodeResource(context.getResources(), R.drawable.apple), x, y, 50, 50));
    }

    //unused at the moment, could extend with more levels.
    public void addBadApple(Context context,int x, int y){
        this.bag.add(new BadApple(BitmapFactory.decodeResource(context.getResources(), R.drawable.unsafe_wall), x, y, 50, 50));
    }

    public void addRedApple(Context context, int x, int y){
        this.bag.add(new RedApple(BitmapFactory.decodeResource(context.getResources(), R.drawable.red_apple), x, y, 50, 50));
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < bag.size(); i++){
            this.bag.get(i).draw(canvas);
        }
    }

    public ArrayList<Edible> getBag(){
        return this.bag;
    }

    public void removeItem(int index){
        this.bag.remove((index));
    }


    public void manageApples(Context context){
        int numOfApples = countApples();
        if(numOfApples < 5){
            int loopCount = 5 - numOfApples;
            for(int i = 0; i < loopCount; i++){
                addApple(context, getRandomX(), getRandomY());
            }
        }
    }

    //unused at the moment
    public void manageBadApples(Context context, Snake snake){
        int numOfBadApples = countBadApples();
        if(numOfBadApples < 3 && snake.getBody().size() > 10){
            int loopCount = 3 - numOfBadApples;
            for(int i = 0; i < loopCount; i++){
                addBadApple(context, getRandomX(), getRandomY());
            }
        }
    }

    public void manageRedApples(Context context, Snake snake){
        int numOfMice = countMice();
        if(numOfMice < 3 && snake.getBody().size() > 12){
            int loopCount = 3 - numOfMice;
            for(int i = 0; i < loopCount; i++){
                addRedApple(context, getRandomX(), getRandomY());
            }
        }
    }

    public int getRandomY(){
        Random generator = new Random();
        int y = generator.nextInt(15) + 1;
        if(y == 1){y = 2;}
        return y * 50;
    }

    public int getRandomX(){
        Random generator = new Random();
        int x = generator.nextInt(19) + 1;
        if(x == 1){x = 2;}
        return x*50;
    }

    public void update(Context context, Snake snake){
        manageApples(context);
//        manageBadApples(context, snake);
        manageRedApples(context, snake);
        checkForInvalidSpawns(snake);
    }

    public int countBadApples(){
        int count = 0;
        String badApple = "uk.co.joemaher.projects.snake.BadApple";
        for(Edible item: this.getBag()){
            String itemName = item.getClass().getName();
            if(itemName.equals(badApple)){
                count++;
            }
        }
        return count;
    }

    public int countApples(){
        int count = 0;
        for(Edible item: this.getBag()){
            if(item.getClass().getName().equals("uk.co.joemaher.projects.snake.Apple")){
                count++;
            }
        }
        return count;
    }

    public int countMice(){
        int count = 0;
        for(Edible item: this.getBag()){
            if(item.getClass().getName().equals("uk.co.joemaher.projects.snake.RedApple")){
                count++;
            }
        }
        return count;
    }


    public void checkForInvalidSpawns(Snake snake){
        for(SnakeBody bodyPart: snake.getBody()){
            for(int i = 0; i < getBag().size(); i++){
                if(snake.collision(bodyPart, (GameObject) getBag().get(i))){
                    removeItem(i);
                }
            }
        }
    }


}
