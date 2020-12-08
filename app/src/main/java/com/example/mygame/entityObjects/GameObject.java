package com.example.mygame.entityObjects;

import android.graphics.Canvas;

public abstract class GameObject {

    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;


    public GameObject(double positionX, double positionY){

        this.positionX = positionX;
        this.positionY = positionY;

    }

    protected static double distanceBetween(GameObject o, GameObject o1) {

        return Math.sqrt(
                Math.pow(o1.getPositionX() - o.getPositionX(), 2) +
                Math.pow(o1.getPositionY() - o.getPositionY(), 2)
        );
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    protected void setPosition(double x, double y) {

        this.positionY = y;
        this.positionX = x;
    }

    protected double getPositionX() {
        return positionX;
    }

    protected double getPositionY() {
        return positionY;
    }


    protected void setPositionX(int positionX){
        this.positionX = positionX;
    }
    protected void setPositionY(int positionY){
        this.positionY = positionY;
    }
}
