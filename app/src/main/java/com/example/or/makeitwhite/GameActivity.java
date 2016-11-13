package com.example.or.makeitwhite;

import android.content.Intent;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;
import java.util.logging.Handler;

public class GameActivity extends AppCompatActivity implements Communicator {

    TextView timer_text;
    TextView score_text;
    ColorFragment[] fragments;
    FragmentManager fm;
    android.os.Handler handler = new android.os.Handler();
    long startTime = 0L, timeInMilliseconds = 0L, timeSwapBuff = 0L, updateTime = 0L;
    int secs;
    int mins;
    int milliseconds;
    static int score;
    FrameLayout gameLayout;

    InterstitialAd mInterstitialAd;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) updateTime / 1000;
            mins = secs / 60;
            secs %= 60;
            if (secs == 15) {
                gameEnded();
                stopTimer();
            }
            else {
                milliseconds = (int) updateTime % 1000;
                String time = String.format("%02d", 14 - secs) + "." + String.format("%02d", (99 - (milliseconds / 10)));
                if (mins > 0) time = mins + ":" + time;
                timer_text.setText(time);
                handler.postDelayed(this, 0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timer_text = (TextView) findViewById(R.id.timer_text);
        score_text = (TextView)findViewById(R.id.score_text);
        score = 0;

        fm = getSupportFragmentManager();
        gameLayout = (FrameLayout)findViewById(R.id.gameLayout);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                Intent i = new Intent(getApplicationContext(), GameOverActivity.class);
                i.putExtra("score", score);
                startActivity(i);
                finish();
            }
        });

        requestNewInterstitial();
    }

    public void updateScore(){
        score_text.setText(""+score);
    }

    private void gameEnded() {

        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();

        else {
            Intent i = new Intent(this, GameOverActivity.class);
            i.putExtra("score", score);
            startActivity(i);
            finish();
        }
    }

    public void requestRandomClick(){

        ColorFragment fragment = new ColorFragment();
        Random rnd = new Random();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.gameLayout, fragment).commit();
        Random rnd1 = new Random();
        int width = rnd1.nextInt(dpToPx(200))+75;
        int height = rnd1.nextInt(dpToPx(150))+100;
        gameLayout.getLayoutParams().width = width;
        gameLayout.getLayoutParams().height = height;
        int w = size.x - gameLayout.getWidth();
        int h = size.y - gameLayout.getHeight();
        int x = rnd.nextInt(w);
        int y = rnd.nextInt(h);
        gameLayout.setX(x);
        gameLayout.setY(y);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
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
