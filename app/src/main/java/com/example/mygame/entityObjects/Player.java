package com.example.mygame.entityObjects;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.mygame.GameLoop;
import com.example.mygame.Joystick;
import com.example.mygame.R;

public class Player extends EntityCircle {


    public static final double SPEED_PPS = 400.0;
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




}
