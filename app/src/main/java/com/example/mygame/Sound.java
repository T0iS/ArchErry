package com.example.mygame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {


    private static SoundPool soundPool;
    private static int hitSound;
    private static int deadSound;


    public Sound(Context context){

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        hitSound = soundPool.load(context, R.raw.hit, 1);
        deadSound = soundPool.load(context, R.raw.dead, 1);


    }


    public void playHitSound(){
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playDeadSound(){
        soundPool.play(deadSound, 1.0f, 1.0f, 1, -1, 1.0f);

    }

}
