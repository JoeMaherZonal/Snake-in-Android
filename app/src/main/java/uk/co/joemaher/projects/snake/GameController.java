package uk.co.joemaher.projects.snake;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;


public class GameController extends SurfaceView implements SurfaceHolder.Callback{

    public final static int WIDTH = 1600 ;
    public final static int HEIGHT = 1000 ;
    public int height;
    private GameThread thread;
    private Background background;
    private WallBlock block;
    private Snake snake;
    private ArrayList<WallBlock> walls;
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button pauseButton;
    private Button exitGameButton;
    private ItemBag itemBag;
    private Paint paint;
    private Button scoreText;
    private boolean gamePaused;
    private String name;

    public GameController(Context context, String name){
        super(context);
        getHolder().addCallback(this);
        this.thread = new GameThread(getHolder(), this);
        setFocusable(true);
        this.gamePaused = false;
        this.name = name;
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

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        walls = new ArrayList<WallBlock>();
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.nogrid_background),0 , 0, 1600, 1000);
        itemBag = new ItemBag();
        createButtons();
        createWalls();
        createSnake();
        setDrawableText();
        //game loop starts here
        thread.setRunning(true);
        thread.start();
    }

    public void createWalls(){
        int x = 50;
        int y = 50;
        for(int i = 0; i < 19; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block),x ,y, 50, 50));
            x+=50;
        }
        for(int i = 0; i < 15; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block), x, y, 50, 50));
            y+=50;
        }
        for(int i = 0; i < 19; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block), x, y, 50, 50));
            x-=50;
        }
        for(int i = 0; i < 15; i++){
            walls.add(new WallBlock(BitmapFactory.decodeResource(getResources(), R.drawable.blue_block), x, y, 50, 50));
            y-=50;
        }
    }

    public void setDrawableText(){
        paint = new Paint();
        paint.setColor(Color.argb(255, 249, 129, 0));
        paint.setTextSize(80);
    }

    public void createSnake(){
        snake = new Snake(BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_up),
            BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_up),
            BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_down),
            BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_left),
            BitmapFactory.decodeResource(getResources(), R.drawable.snake_head_right),500 ,500, 50, 50, 50);
    }


    public void createButtons(){
        upButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.up_arrow_clicked), 1250, 400, 150, 150);
        downButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.down_arrow_clicked), 1250, 700, 150, 150);
        leftButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow_clicked), 1100, 550, 150, 150);
        rightButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow),BitmapFactory.decodeResource(getResources(), R.drawable.right_arrow_clicked), 1400, 550, 150, 150);
        pauseButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.pause),BitmapFactory.decodeResource(getResources(), R.drawable.pause_clicked), 1400, 50, 100, 100);
        exitGameButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.exit_game),BitmapFactory.decodeResource(getResources(), R.drawable.exit_game), 1150, 50, 100, 100);
        scoreText = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.score_text),BitmapFactory.decodeResource(getResources(), R.drawable.score_text), 1100, 200, 200, 100);

    }
        //Button onclick events ---------------------

    public int calcHeight(int perc){
        int heightUnit = getHeight() /100;
        return heightUnit * perc;
    }

    public int calcWidth(int perc){
        int widthtUnit = getHeight() /100;
        return widthtUnit * perc;
    }

    public int getWidthPercentage(){
//        double xPercent = Math.floor(event.getRawX()) / getWidth() * 100;
//        double yPercent = Math.floor(event.getRawY()) / getHeight() * 100;
        return 0;
    }

    public void checkForButtonClick(MotionEvent event){
        int widthUnit = getWidth() /100;
        int heightUnit = getHeight() /100;
        double xPercent = Math.floor(event.getRawX()) / getWidth() * 100;
        double yPercent = Math.floor(event.getRawY()) / getHeight() * 100;

        //up
        if(xPercent > 76 && xPercent < 90 && yPercent > 39 && yPercent < 56){
            System.out.println("Up Button Clicked");
            upButton.swapImages();
            upButton.click();
            snake.setDirection(DirectionType.UP);
            return;
        }
        //down
        if(xPercent > 76 && xPercent < 90 && yPercent > 68 && yPercent < 85){
            System.out.println("Down Button Clicked");
            downButton.swapImages();
            downButton.click();
            snake.setDirection(DirectionType.DOWN);
            return;
        }
        //left
        if(xPercent > 66 && xPercent < 80 && yPercent > 50 && yPercent < 74){
            System.out.println("Left Button Clicked");
            leftButton.swapImages();
            leftButton.click();
            snake.setDirection(DirectionType.LEFT);
            return;
        }
        //right
        if(xPercent > 85 && xPercent <99 && yPercent > 50 && yPercent < 74){
            System.out.println("Right Button Clicked");
            rightButton.swapImages();
            rightButton.click();
            snake.setDirection(DirectionType.RIGHT);
            return;
        }

        //pause/play
        if(xPercent > 87 && xPercent <93 && yPercent > 5 && yPercent < 14){
            System.out.println("Pause/Play clicked");
            pauseButton.swapImages();
            if(!gamePaused){
                this.gamePaused = true;
            }else{
                this.gamePaused = false;
            }
            System.out.println("Paused:" + gamePaused);
        }
        //exit game
        if(xPercent > 71 && xPercent <78 && yPercent > 5 && yPercent < 14){
            forceGameOver();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        checkForButtonClick(event);
        double xPercent = Math.floor(event.getRawX()) / getWidth() * 100;
        double yPercent = Math.floor(event.getRawY()) / getHeight() * 100;

        System.out.println("X:" + xPercent + " Y:" + yPercent);

//        System.out.println("X:" + Math.floor(event.getRawX()) + " Y:" + Math.floor(event.getRawY()));
        System.out.println("Screen res: " + getHeight() + ":" + getWidth());
        return super.onTouchEvent(event);
    }

    // update game items -----------------------------

    public void update(){
        if(!gamePaused) {
            int snakesize = snake.getBody().size();
            checkForGameOver();
            checkforWallCollisions(this.walls);
            snake.checkForCollisionWithItem(itemBag, getContext());
            updateButtons();
            snake.update(getContext());
            if(snake.getBody().size() > snakesize){
                manageWalls();
            }
            itemBag.update(getContext(), snake);
        }
    }

    public void checkforWallCollisions(ArrayList<WallBlock> walls){

        for(int i = 0; i < walls.size(); i ++){

            if( collision(snake, walls.get(i)) && !walls.get(i).isSafe){
                forceGameOver();
                break;
            }

            if( collision(snake, walls.get(i)) && walls.get(i).isSafe() && snake.getY() < 100){
                snake.setY(snake.getY() + 700);
                break;
            }

            if(collision(snake, walls.get(i)) && walls.get(i).isSafe() && snake.getY() > 750) {
                snake.setY(snake.getY() - 700);
                break;
            }

            if(collision(snake, walls.get(i)) && walls.get(i).isSafe() && snake.getX() < 100){
                snake.setX(snake.getX() + 900);
                break;
            }

            if(collision(snake, walls.get(i)) && walls.get(i).isSafe() && snake.getX() > 950){
                snake.setX(snake.getX() - 900);
                break;
            }
        }
    }

    public void updateButtons(){
        upButton.updateImage();
        downButton.updateImage();
        leftButton.updateImage();
        rightButton.updateImage();
    }

    public void manageWalls(){
        int snakeLength = snake.getBody().size();
        int wallsLength = this.walls.size();
        if(snakeLength > 15){
            Random generator = new Random();
            int randNum = generator.nextInt(wallsLength);
            this.walls.get(randNum).makeUnsafe(getContext());
        }
    }

    //draw game items -----------------------------------------------

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        //scales to screen size
        final float scaleFactorX = (float)getWidth()/WIDTH;
        final float scaleFactorY = (float)getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            //Drawing everything here
            background.draw(canvas);
            drawWalls(canvas);
            drawControls(canvas);
            snake.draw(canvas);
            itemBag.draw(canvas);
            canvas.drawText("" + snake.getScore(), 1300, 280, paint);
            //canvas.drawText("Speed: " + snake.getSpeed(), 2200, 500, paint);
            //Must restore canvas to original size or it will continue to scale with each loop
            canvas.restoreToCount(savedState);
        }
    }

    public void drawControls(Canvas canvas){
        upButton.draw(canvas);
        downButton.draw(canvas);
        rightButton.draw(canvas);
        leftButton.draw(canvas);
        pauseButton.draw(canvas);
        exitGameButton.draw(canvas);
        scoreText.draw(canvas);
    }


    public void drawWalls(Canvas canvas){
        for(int i = 0; i < walls.size(); i++){
            walls.get(i).draw(canvas);
        }
    }

    public boolean collision(GameObject a, GameObject b){
        if(Rect.intersects(a.getRectangle(), b.getRectangle())){
            return true;
        }
        return false;
    }

   public void checkForGameOver(){
        for(int i = 0; i < snake.getBody().size(); i ++){
            if(collision(snake, snake.getBody().get(i))){
                gamePaused = true;
                updateHighScores();
                Intent myIntent = new Intent(getContext(), GameOver.class);
                myIntent.putExtra("score", "" + snake.getScore());
                myIntent.putExtra("name", this.name);
                getContext().startActivity(myIntent);
            }
        }
    }

    public void forceGameOver(){
        gamePaused = true;
        updateHighScores();
        Intent myIntent = new Intent(getContext(), GameOver.class);
        myIntent.putExtra("score", "" + snake.getScore());
        myIntent.putExtra("name", this.name);
        getContext().startActivity(myIntent);
    }

    public void updateHighScores(){
        SavedTextPreferences savedHighScores = new SavedTextPreferences();
        Score score = new Score(this.name, snake.getScore());
        savedHighScores.updateHighScores(getContext(), score);
    }

}
