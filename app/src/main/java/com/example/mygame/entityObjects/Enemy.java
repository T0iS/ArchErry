package com.example.mygame.entityObjects;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.mygame.GameLoop;
import com.example.mygame.R;

public class Enemy extends EntityCircle {

    private static final double SPEED_PPS = Player.SPEED_PPS * 0.6;
    private static final double MAX_SPEED = SPEED_PPS / GameLoop.MAX_UPS;
    private static final int SECONDS_TO_SPAWN_ENEMY = 3; //Enemy spawns per minute

    private static final double SPAWN_RATE = GameLoop.MAX_UPS * SECONDS_TO_SPAWN_ENEMY;


    private static double updatesRemaining = SPAWN_RATE;

    private Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(positionX, positionY,  ContextCompat.getColor(context, R.color.enemy) , radius);
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(
                Math.random()*1000,
                Math.random()*1000,
                ContextCompat.getColor(context, R.color.enemy),
                30
        );
        this.player = player;
    }

    public static boolean readyToBeSpawned() {

        if(updatesRemaining <= 0){
            updatesRemaining += SPAWN_RATE;
            return true;
        }
        else {
            updatesRemaining--;
            return false;
        }
    }

    @Override
    public void update() {

        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;


        double distanceToPlayer = GameObject.distanceBetween(this, player);

        if (distanceToPlayer > 0) {
            double directionX = distanceToPlayerX / distanceToPlayer;
            double directionY = distanceToPlayerY / distanceToPlayer;

            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        }
        else{
            velocityX = 0;
            velocityY = 0;
        }
        positionX += velocityX;
        positionY += velocityY;


    }
}
