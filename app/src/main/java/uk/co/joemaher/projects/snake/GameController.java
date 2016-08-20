package uk.co.joemaher.projects.snake;


import android.content.Context;
import android.graphics.Bitmap;
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
    private Snake snake;
    private ArrayList<WallBlock> walls;
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;

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

    //create stuffs ----------------------------

    public void createWalls(){
        int x = 100;
        int y = 100;
        for(int i = 0; i < 19; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block),x ,y, 100, 100));
            x+=100;
        }
        for(int i = 0; i < 15; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block), x, y, 100, 100));
            y+=100;
        }
        for(int i = 0; i < 19; i++){
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
        snake = new Snake(BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_up),
                BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_up),
                BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_down),
                BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_left),
                BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_right),1000 ,1000, 95, 95);
        snake.addToBody(getContext());
        snake.addToBody(getContext());
        snake.addToBody(getContext());

        createWalls();
        upButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow_clicked), 2500, 800, 150, 150);
        downButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow_clicked), 2500, 1400, 150, 150);
        leftButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow_clicked), 2200, 1100, 150, 150);
        rightButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow_clicked), 2800, 1100, 150, 150);

        //game loop starts
        thread.setRunning(true);
        thread.start();

    }


    public void checkForButtonClick(MotionEvent event){
        //up
        if(event.getRawX() > 933 && event.getRawX() < 1042 && event.getRawY() > 353 && event.getRawY() < 482){
            System.out.println("Up Button Clicked");
            upButton.swapImages();
            upButton.click();
            snake.setDirection(DirectionType.UP);

        }
        //down
        if(event.getRawX() > 933 && event.getRawX() < 1042 && event.getRawY() > 625 && event.getRawY() < 748){
            System.out.println("Down Button Clicked");
            downButton.swapImages();
            downButton.click();
            snake.setDirection(DirectionType.DOWN);
        }
        //left
        if(event.getRawX() > 826 && event.getRawX() < 929 && event.getRawY() > 486 && event.getRawY() < 609){
            System.out.println("Left Button Clicked");
            leftButton.swapImages();
            leftButton.click();
            snake.setDirection(DirectionType.LEFT);
        }
        //right
        if(event.getRawX() > 1054 && event.getRawX() < 1158 && event.getRawY() > 486 && event.getRawY() < 609){
            System.out.println("Right Button Clicked");
            rightButton.swapImages();
            rightButton.click();
            snake.setDirection(DirectionType.RIGHT);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
//        Log.d("X", Float.toString(Math.round(event.getRawX())));
//        Log.d("Y", Float.toString(Math.round(event.getRawY())));
//        Log.d("WIDTH", Float.toString(getWidth()));
//        Log.d("HEIGHT", Float.toString(getHeight()));
        //do when when touched?

        checkForButtonClick(event);

        return super.onTouchEvent(event);
    }

    // update stuffs -----------------------------

    public void update(){
        checkforWallCollisions();
        updateButtons();
        snake.update();
    }

    public void updateButtons(){
        upButton.updateImage();
        downButton.updateImage();
        leftButton.updateImage();
        rightButton.updateImage();
    }


    public void checkforWallCollisions(){
        if(snake.getY() < 200){
            System.out.println("Collision!!!!");
            snake.setY(snake.getY() + 1400);
            return;
        }
       if(snake.getY() > 1500) {
           System.out.println("Collision!!!!");
           snake.setY(snake.getY() - 1400);
           return;
        }
        if(snake.getX() < 200){
            System.out.println("Collision!!!!");
            snake.setX(snake.getX() + 1800);
            return;
        }
        if(snake.getX() > 1900){
            System.out.println("Collision!!!!");
            snake.setX(snake.getX() - 1800);
            return;
        }
    }

    //draw stuffs -----------------------------------------------

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
            drawControls(canvas);
            snake.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void drawControls(Canvas canvas){
        upButton.draw(canvas);
        downButton.draw(canvas);
        rightButton.draw(canvas);
        leftButton.draw(canvas);
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