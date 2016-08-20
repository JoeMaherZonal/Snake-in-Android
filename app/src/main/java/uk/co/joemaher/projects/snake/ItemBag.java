package uk.co.joemaher.projects.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 20/08/2016.
 */
public class ItemBag {
    private ArrayList<Item> bag;

    public ItemBag(){
        this.bag = new ArrayList<Item>();
    }

    public void addRedBlock(Context context,int x, int y){
        this.bag.add(new Item(BitmapFactory.decodeResource(context.getResources(), R.drawable.red_block), x, y, 100, 100));
    }

    public void addYellowBlock(Context context,int x, int y){
        this.bag.add(new Item(BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow_block), x, y, 100, 100));
    }

    public void addPurpleBlock(Context context,int x, int y){
        this.bag.add(new Item(BitmapFactory.decodeResource(context.getResources(), R.drawable.purple_block), x, y, 100, 100));
    }

    public void addOrangeBlock(Context context,int x, int y){
        this.bag.add(new Item(BitmapFactory.decodeResource(context.getResources(), R.drawable.orange_block), x, y, 100, 100));
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < bag.size(); i++){
            this.bag.get(i).draw(canvas);
        }
    }

    public ArrayList<Item> getBag(){
        return this.bag;
    }

    public void removeItem(int index){
        this.bag.remove((index));
    }

    public void addRandomItems(Context context){
        int x = getRandomX();
        int y = getRandomY();
        Random generator = new Random();
        int randomNum = generator.nextInt(4) + 1;
        switch (randomNum) {
            case 1:
                this.addOrangeBlock(context, x, y);
                break;
            case 2:
                this.addYellowBlock(context, x, y);
                break;
            case 3:
                this.addRedBlock(context, x, y);
                break;
            case 4:
                this.addPurpleBlock(context, x, y);
                break;
        }

    }

    public int getRandomY(){
        Random generator = new Random();
        int y = generator.nextInt(15) + 1;
        if(y == 1){y = 2;}
        return y*100;
    }

    public int getRandomX(){
        Random generator = new Random();
        int x = generator.nextInt(19) + 1;
        if(x == 1){x = 2;}
        return x*100;
    }

    public void update(Context context){
        if(bag.size() < 30){
            addRandomItems(context);
        }
    }

    public void checkForInvalidSpawns(Snake snake){
        for(SnakeBody bodyPart: snake.getBody()){
            for(int i = 0; i < getBag().size(); i++){
                if(snake.collision(bodyPart, getBag().get(i))){
                    removeItem(i);
                }
            }
        }
    }


}
