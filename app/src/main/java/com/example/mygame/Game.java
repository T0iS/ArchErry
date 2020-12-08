package com.example.mygame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.mygame.entityObjects.Enemy;
import com.example.mygame.entityObjects.EntityCircle;
import com.example.mygame.entityObjects.Player;

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

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        joystick = new Joystick(275, 1000, 70, 40);
        player = new Player(getContext(), joystick, 1000, 500, 30);

        //enemy = new Enemy(getContext(), player, 500, 500, 30);

        statusText = new StatusText("DEAD", ContextCompat.getColor(context, R.color.red), 1100, 200);
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
        //drawUPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);
        //enemy.draw(canvas);
        for(Enemy e : enemies) {
            e.draw(canvas);
        }

        if(player.health <= 0){
            statusText.draw(canvas);
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

    public void update() {

        if(player.health > 0) {

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
                }
            }
        }
    }




}
