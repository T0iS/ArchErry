package com.example.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private final int baseCircleCenterX;
    private final int baseCircleCenterY;
    private int innerCircleCenterX;
    private int innerCircleCenterY;

    private final int baseCircleRad;
    private final int innerCircleRad;

    private final Paint baseCirclePaint;
    private final Paint innerCirclePaint;

    private double joystickCenterToTouch;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerX, int centerY, int baseRad, int innerRad){
        this.baseCircleCenterX = centerX;
        this.baseCircleCenterY = centerY;
        this.innerCircleCenterX = centerX;
        this.innerCircleCenterY = centerY;

        this.baseCircleRad = baseRad;
        this.innerCircleRad = innerRad;

        baseCirclePaint = new Paint();
        baseCirclePaint.setColor(Color.GRAY);
        baseCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.DKGRAY);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterX = (int) (baseCircleCenterX + actuatorX*baseCircleRad);
        innerCircleCenterY = (int) (baseCircleCenterY + actuatorY*baseCircleRad);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(baseCircleCenterX, baseCircleCenterY, baseCircleRad, baseCirclePaint);
        canvas.drawCircle(innerCircleCenterX, innerCircleCenterY, innerCircleRad, innerCirclePaint);
    }

    public boolean isPressed(double x, double y) {
        joystickCenterToTouch = Math.sqrt(
                Math.pow(baseCircleCenterX - x, 2) +
                Math.pow(baseCircleCenterY - y, 2)
        );
        return joystickCenterToTouch < baseCircleRad;
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;

    }

    public boolean getPressed( ) {
        return this.isPressed;
    }

    public void setActuator(double x, double y) {
        double deltaX = x - baseCircleCenterX;
        double deltaY = y - baseCircleCenterY;
        double deltaDistance = Math.sqrt(
                Math.pow(deltaX - x, 2) +
                Math.pow(deltaY - y, 2)
        );
        if(deltaDistance < baseCircleRad){
            actuatorX = deltaX/baseCircleRad;
            actuatorY = deltaY/baseCircleRad;
        }
        else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return actuatorX;
    }
    public double getActuatorY() {
        return actuatorY;
    }
}
