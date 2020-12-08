package com.example.mygame.entityObjects;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.mygame.GameLoop;
import com.example.mygame.Joystick;
import com.example.mygame.MainActivity;
import com.example.mygame.R;

public class Player extends EntityCircle {


    public static final double SPEED_PPS = 400.0;
    public static final int MAX_HEALTH = 5;
    private static final double MAX_SPEED = SPEED_PPS / GameLoop.MAX_UPS;
    private final Joystick joystick;
    private HealthBar healthBar;
    public int health;


    public Player(Context context, Joystick joystick,double positionX, double positionY, double radius){

        super(positionX, positionY, ContextCompat.getColor(context, R.color.player), radius);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.health = MAX_HEALTH;

    }



    public void update() {

        velocityX = 2*joystick.getActuatorX()*MAX_SPEED;
        velocityY = 2*joystick.getActuatorY()*MAX_SPEED;

        if(this.getPositionX() > MainActivity.getScreenWidth() ) {
            this.setPositionX(MainActivity.getScreenWidth());
        }
        else if(this.getPositionX() < 0){
            this.setPositionX(0);
        }
        else{
            positionX += velocityX;
        }

        if(this.getPositionY() > MainActivity.getScreenHeight() ) {
            this.setPositionY(MainActivity.getScreenHeight());
        }
        else if(this.getPositionY() < 0){
            this.setPositionY(0);
        }
        else{
            positionY += velocityY;
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        healthBar.draw(canvas);
    }


    public float getHealth() {
        return health;
    }


}
