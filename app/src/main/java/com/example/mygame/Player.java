package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player extends EntityCircle {


    private static final double SPEED_PPS = 400.0;
    private static final double MAX_SPEED = SPEED_PPS / GameLoop.MAX_UPS;
    private final Joystick joystick;


    public Player(Context context, Joystick joystick,double positionX, double positionY, double radius){

        super(positionX, positionY, ContextCompat.getColor(context, R.color.player), radius);
        this.joystick = joystick;

    }



    public void update() {
        velocityX = 2*joystick.getActuatorX()*MAX_SPEED;
        velocityY = 2*joystick.getActuatorY()*MAX_SPEED;
        positionX += velocityX;
        positionY += velocityY;
    }

    public void setPosition(double x, double y) {

        this.positionY = y;
        this.positionX = x;
    }
}
