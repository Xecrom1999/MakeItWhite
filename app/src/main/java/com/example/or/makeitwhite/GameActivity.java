package com.example.or.makeitwhite;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Random;
import java.util.logging.Handler;

public class GameActivity extends AppCompatActivity implements Communicator {

    TextView timer_text;
    ColorFragment[] fragments;
    FragmentManager fm;
    android.os.Handler handler = new android.os.Handler();
    long startTime = 0L, timeInMilliseconds = 0L, timeSwapBuff = 0L, updateTime = 0L;
    int secs;
    int mins;
    int milliseconds;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) updateTime / 1000;
            mins = (int) secs / 60;
            secs %= 60;
            milliseconds = (int) updateTime % 1000;
            String time = String.format("%02d", secs) + ":" +  String.format("%03d", milliseconds);
            if (mins > 0) time = mins + ":" + time;
            timer_text.setText(time);
            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timer_text = (TextView) findViewById(R.id.timer_text);

        fm = getSupportFragmentManager();
        fragments = new ColorFragment[4];
        fragments[0] = (ColorFragment)fm.findFragmentById(R.id.fragment);
        fragments[1] = (ColorFragment)fm.findFragmentById(R.id.fragment2);
        fragments[2] = (ColorFragment)fm.findFragmentById(R.id.fragment3);
        fragments[3] = (ColorFragment)fm.findFragmentById(R.id.fragment4);
    }

    public void requestRandomClick(){
        boolean f = false;
        int num = 0;
        while(!f){
            Random rnd = new Random();
            num = rnd.nextInt(4);
            if(fragments[num].active) f = true;
            if(!fragments[0].active && !fragments[1].active && !fragments[2].active && !fragments[3].active){
                Intent i = new Intent(this, GameOverActivity.class);
                i.putExtra("mins", mins);
                i.putExtra("secs", secs);
                i.putExtra("mills", milliseconds);
                startActivity(i);
                stopTimer();
                f = true;
            }
        }
        fragments[num].setAsClickable();
    }

    public void startGame(View v){
        requestRandomClick();
        startTimer();
    }

    private void startTimer() {
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimerThread, 0);
    }

    private void stopTimer() {
        timeSwapBuff += timeInMilliseconds;
        handler.removeCallbacks(updateTimerThread);
    }


}
