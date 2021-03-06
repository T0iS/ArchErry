package com.example.mygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.mygame.entityObjects.Enemy;
import com.example.mygame.entityObjects.EntityCircle;
import com.example.mygame.entityObjects.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    //private final Enemy enemy;
    private List<Enemy> enemies = new ArrayList<>();
    private StatusText statusText;
    private String secondsRunning;
    private int previousBest;

    private boolean deathCaught = false;

    private Sound sound;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        joystick = new Joystick(275, 1000, 70, 40);
        player = new Player(getContext(), joystick, 1000, 500, 30);

        //enemy = new Enemy(getContext(), player, 500, 500, 30);

        statusText = new StatusText("DEAD", ContextCompat.getColor(context, R.color.red), 1100, 200);
        this.previousBest = Double.valueOf(readFromStorage("score.txt")).intValue();

        sound = new Sound(getContext());

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double)event.getX(), (double)event.getY())){
                    joystick.setPressed(true);
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if(joystick.getPressed()){
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
                joystick.setPressed(false);
                joystick.resetActuator();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawFPS(canvas);
        drawSeconds(canvas);
        drawBestTime(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
        //enemy.draw(canvas);
        for(Enemy e : enemies) {
            e.draw(canvas);
        }

        if(player.health <= 0){
            statusText.draw(canvas);

            if(!deathCaught) {
                deathCaught = true;
                sound.playDeadSound();
                Double s = Double.parseDouble(this.secondsRunning);

                if (this.previousBest < s.intValue()) {
                    writeToStorage("score.txt", this.secondsRunning);
                    this.previousBest = s.intValue();
                    drawBestTime(canvas);
                }
            }

        }
    }

    public void drawUPS(Canvas canvas) {

        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.white);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS" + averageUPS, 100, 160, paint);
    }

    public void drawFPS(Canvas canvas) {

        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.red);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS" + averageFPS, 100, 60, paint);
    }

    public void drawSeconds(Canvas canvas) {

        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.white);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("Time: " + secondsRunning, MainActivity.getScreenWidth()-507, 170, paint);
    }

    public void drawBestTime(Canvas canvas){

        String bestTime = String.valueOf(this.previousBest);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.yellow);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("Highscore: " + bestTime, MainActivity.getScreenWidth()-507, 115, paint);
    }

    public void update() {

        if(player.health > 0) {

            this.secondsRunning = Double.toString(gameLoop.getSecondsRunning());

            joystick.update();
            player.update();

            //enemy.update();

            if (Enemy.readyToBeSpawned()) {
                enemies.add(new Enemy(getContext(), player));
            }

            for (Enemy e : enemies) {
                e.update();
            }

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                if (EntityCircle.collides(enemyIterator.next(), player)) {
                    enemyIterator.remove();
                    player.health--;
                    sound.playHitSound();
                }
            }

        }
    }

    public void writeToStorage(String filename, String text){


        try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(text.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readFromStorage(String filename){

        try {
            FileInputStream inputStream = getContext().openFileInput(filename);
            InputStreamReader reader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();

            String text;

            while((text = bufferedReader.readLine()) != null){
                stringBuffer.append(text);
            }

            return stringBuffer.toString();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "0";
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }
    }


}
