package com.example.mygame.entityObjects;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class EntityCircle extends GameObject {


    protected double radius;
    protected Paint paint;


    public EntityCircle(double positionX, double positionY, int color, double radius) {
        super(positionX, positionY);
        this.radius = radius;
        paint = new Paint();

        paint.setColor(color);

    }

    public void draw(Canvas canvas) {

        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);

    }




}
