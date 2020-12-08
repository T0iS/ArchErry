package com.example.mygame;

import android.graphics.Canvas;
import android.graphics.Paint;

public class StatusText {


    private String text;
    private Paint color;
    private int positionX;
    private int positionY;


    public StatusText(String text, int color, int positionX, int positionY) {

        this.text = text;
        this.positionX = positionX;
        this.positionY = positionY;

        this.color = new Paint();
        this.color.setColor(color);
        this.color.setTextSize((float)127);
    }


    public void draw(Canvas canvas){
        canvas.drawText(text, this.positionX, this.positionY, color);

    }


}
