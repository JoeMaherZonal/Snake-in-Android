package uk.co.joemaher.projects.snake;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;


public class GameController extends SurfaceView implements SurfaceHolder.Callback{
    public static final int WIDTH = 3200;
    public static final int HEIGHT = 1800;
    private GameThread thread;
    private Background background;
    private WallBlock block;
    private Snake snakeHead;
    private ArrayList<SnakeBody> snakeBody;
    private ArrayList<WallBlock> walls;

    public GameController(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void createWalls(){
        int x = 100;
        int y = 100;
        for(int i = 0; i < 20; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block),x ,y, 100, 100));
            x+=100;
        }
        for(int i = 0; i < 15; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block), x, y, 100, 100));
            y+=100;
        }
        for(int i = 0; i < 20; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block), x, y, 100, 100));
            x-=100;
        }
        for(int i = 0; i < 15; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block), x, y, 100, 100));
            y-=100;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        walls = new ArrayList<WallBlock>();
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background),0 ,0, 0, 0);
        block = new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block),500 ,500, 100, 100);
        snakeHead = new Snake(BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_up),1000 ,1000, 100, 100);
        snakeBody = new ArrayList<SnakeBody>();
        snakeBody.add(new SnakeBody(BitmapFactory.decodeResource(getResources(), R.drawable.snake_body),1000 ,1100, 100, 100));
        createWalls();
        //game loop starts
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        Log.d("X", Float.toString(Math.round(event.getRawX())));
        Log.d("Y", Float.toString(Math.round(event.getRawY())));
        Log.d("WIDTH", Float.toString(getWidth()));
        Log.d("HEIGHT", Float.toString(getHeight()));
        //do when when touched?
        int heady = snakeHead.getY();
        int bodyy = snakeBody.get(0).getY();
        snakeHead.setY(heady - 100);
        snakeBody.get(0).setY(bodyy - 100);
        return super.onTouchEvent(event);
    }

    public void update(){
        //update shizznizz
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        final float scaleFactorX = (float)getWidth()/WIDTH;
        final float scaleFactorY = (float)getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            //call draw on everything here
            background.draw(canvas);
            drawWalls(canvas);
            snakeHead.draw(canvas);
            snakeBody.get(0).draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void drawWalls(Canvas canvas){
        for(int i = 0; i < walls.size(); i++){
            walls.get(i).draw(canvas);
        }
    }


    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(), b.getRectangle()))
        {
            return true;
        }
        return false;
    }

}