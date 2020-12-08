package com.example.mygame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.math.BigDecimal;

public class GameLoop extends Thread {

    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double averageUPS;
    private double averageFPS;
    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E-3/MAX_UPS;
    private int secondsRunning;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
        this.secondsRunning = 0;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public double getSecondsRunning() {
        return secondsRunning;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime, elapsedTime, sleepTime;

        Canvas canvas = null;
        //gameLoop
        startTime = System.currentTimeMillis();
        while(isRunning){

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;

                    game.draw(canvas);
                }
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    frameCount++;
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


//CORRECT GAME LOOP IMPLEMENTATION, CAUSES LAG
/*
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)(updateCount* UPS_PERIOD - elapsedTime);
            if(sleepTime > 0){
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            while(sleepTime < 0 && updateCount < MAX_UPS-1){
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long)(updateCount* UPS_PERIOD - elapsedTime);
            }
*/

            elapsedTime = System.currentTimeMillis() - startTime;
            secondsRunning += elapsedTime/1000;
            if(elapsedTime >= 1000){
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();

            }
        }
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
