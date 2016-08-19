package uk.co.joemaher.projects.snake;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
    private int FPS = 10;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameController gameController;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GameController gameControllerl){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameController = gameControllerl;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameController.update();
                    this.gameController.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
//                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean b){
        running=b;
    }
}