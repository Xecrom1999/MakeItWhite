package com.example.or.makeitwhite;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    FragmentManager fm;
    android.os.Handler handler = new android.os.Handler();
    long startTime = 0L, timeInMilliseconds = 0L, timeSwapBuff = 0L, updateTime = 0L;
    int secs;
    int mins;
    int milliseconds;
    int score;
    FrameLayout gameLayout;
    MediaPlayer gamingMusic;

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

        gamingMusic = MediaPlayer.create(this, R.raw.gaming_music);


        timer_text = (TextView) findViewById(R.id.timer_text);
        score_text = (TextView)findViewById(R.id.score_text);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/score_font.ttf");
        score_text.setTypeface(myFont);
        score = 0;

        fm = getSupportFragmentManager();
        gameLayout = (FrameLayout)findViewById(R.id.gameLayout);

    }

    public void updateScore(){
        score++;
        score_text.setText(""+score);
    }

    private void gameEnded() {
        Intent i = new Intent(this, GameOverActivity.class);
        i.putExtra("score", score);
        gamingMusic.stop();
        stopTimer();
        startActivity(i);
        finish();
    }

    public void requestRandomClick(){

        ColorFragment fragment = new ColorFragment();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int sizeX = metrics.widthPixels;
        int sizeY = metrics.heightPixels;
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.gameLayout, fragment).commit();
        int width = new Random().nextInt(dpToPx(200))+75;
        int height = new Random().nextInt(dpToPx(150))+100;
        gameLayout.getLayoutParams().width = width;
        gameLayout.getLayoutParams().height = height;
        int w = sizeX - width - dpToPx(40);
        int h = sizeY - height - dpToPx(60);
        int x = new Random().nextInt(w) + dpToPx(20);
        int y = new Random().nextInt(h) + dpToPx(40);
        gameLayout.setX(x);
        gameLayout.setY(y);

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void startGame(final View v){
        v.setClickable(false);
        gamingMusic.start();
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);
        v.startAnimation(fadeInAnimation);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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

    @Override
    protected void onPause() {
        super.onPause();

        gamingMusic.stop();
        stopTimer();
        finish();
    }
}
