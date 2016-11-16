package com.example.or.makeitwhite;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements Communicator, Animation.AnimationListener {

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
    TextView count_down_text;
    TextView add_time_text;
    Animation floatAnimation;
    private int mX;
    private int mY;

    final int STARTING_TIME_IN_SECONDS = 15;
    final double TIME_TO_ADD_IN_SECONDS = 0.1;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) updateTime / 1000;
            mins = secs / 60;
            secs %= 60;
            if (secs == STARTING_TIME_IN_SECONDS) {
                gameEnded();
            }
            else {
                milliseconds = (int) updateTime % 1000;
                String time = String.format("%02d", STARTING_TIME_IN_SECONDS - 1 - secs) + "." + String.format("%02d", (99 - (milliseconds / 10)));
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


        floatAnimation = AnimationUtils.loadAnimation(this, R.anim.float_anim);
        floatAnimation.setDuration(500);

        gamingMusic = MediaPlayer.create(this, R.raw.gaming_music);
        gamingMusic.setVolume(0.1f, 0.1f);
        gamingMusic.setLooping(true);

        timer_text = (TextView) findViewById(R.id.timer_text);
        timer_text.setText(STARTING_TIME_IN_SECONDS+".00");

        score_text = (TextView)findViewById(R.id.score_text);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/score_font.ttf");
        score_text.setTypeface(myFont);
        score = 0;

        fm = getSupportFragmentManager();
        gameLayout = (FrameLayout)findViewById(R.id.game_layout);

        count_down_text = (TextView) findViewById(R.id.count_down_text);
        countDown(count_down_text, 3);

        add_time_text = (TextView) findViewById(R.id.add_time_text);

        floatAnimation.setAnimationListener(this);
    }

    private void countDown(final TextView tv, final int count) {

        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);

        tv.setVisibility(View.VISIBLE);

        String[] colors = new String[]{"#66BB6A", "#FFEE58", "#EF5350"};

        if (count == 0) {
            tv.setVisibility(View.INVISIBLE);
            startGame();
            return;
        }
        else tv.setTextColor(Color.parseColor(colors[count-1]));

        tv.setText(count+"");

        fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationEnd(Animation anim) {
                countDown(tv, count - 1);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        tv.startAnimation(fadeAnimation);

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

    public void requestRandomClick(int num) {

        if (num != 0) {

            float addedTime = (float) (1000 * TIME_TO_ADD_IN_SECONDS * num);
            add_time_text.setText("+" + addedTime / 1000);
            add_time_text.startAnimation(floatAnimation);
            timeSwapBuff -= addedTime;
        }

            ColorFragment fragment = new ColorFragment();
            RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
            int sizeX = container.getWidth();
            int sizeY = container.getHeight();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.game_layout, fragment).commit();
            int width = new Random().nextInt(dpToPx(120)) + dpToPx(35);
            gameLayout.getLayoutParams().width = width;
            gameLayout.getLayoutParams().height = width;
            int w = sizeX - width - dpToPx(40);
            int h = sizeY - width - dpToPx(90);
            int x = new Random().nextInt(w) + dpToPx(30);
            int y = new Random().nextInt(h) + dpToPx(40);
            gameLayout.setX(x);
            gameLayout.setY(y);

            mX = x + width / 2 - add_time_text.getWidth() / 2;
            mY = y + width / 2 - add_time_text.getHeight() - dpToPx(25);

            if (num == 0) {
                add_time_text.setX(mX);
                add_time_text.setY(mY);
            }
        }



    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public void startGame(){
        gamingMusic.start();
        requestRandomClick(0);
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

    @Override
    public void onAnimationStart(Animation animation) {
        add_time_text.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        add_time_text.setVisibility(View.INVISIBLE);
        add_time_text.setX(mX);
        add_time_text.setY(mY);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
