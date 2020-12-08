package com.example.mygame.entityObjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.mygame.R;

public class HealthBar {

    private Player player;
    private float healthbarOffset = 37;
    private int width, height, innerOffset;
    private Paint borderPaint, healthPaint;

    public HealthBar(Context context, Player player){

        this.player = player;
        this.width = 77;
        this.height = 27;
        this.innerOffset = 5;

        this.borderPaint = new Paint();
        this.healthPaint = new Paint();
        this.borderPaint.setColor(ContextCompat.getColor(context, R.color.border));
        this.healthPaint.setColor(ContextCompat.getColor(context, R.color.health));

    }


    public void draw(Canvas canvas){

        float x = (float) player.getPositionX();
        float y = (float) player.getPositionY();
        float healthPercentage = ((player.getHealth() / player.MAX_HEALTH));


        float barLeft, barRight, barTop, barBottom;

        barLeft = x - width/2;
        barRight = x + width/2;
        barBottom = y - healthbarOffset;
        barTop = barBottom - height;



        canvas.drawRect(barLeft, barTop, barRight, barBottom, borderPaint);

        float healthHeight, healthWidth;

        healthHeight = height - 2*innerOffset;
        healthWidth = width - 2*innerOffset;

        canvas.drawRect(
                barLeft + innerOffset,
                (barBottom - innerOffset) - healthHeight,
                (barLeft + innerOffset) + healthWidth*healthPercentage,
                barBottom - innerOffset,
                healthPaint
        );


    }


}
