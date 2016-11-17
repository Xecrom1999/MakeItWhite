package com.example.or.makeitwhite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class GameOverActivity extends AppCompatActivity {

    TextView score_text2;
    TextView best_score_text;
    TextView sumOfTaps;
    TextView funnyComment;
    TextView gameOver;
    Intent intent;
    SharedPreferences preferences;
    int bestScore;
    InterstitialAd mInterstitialAd;
    boolean f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);

        preferences = getSharedPreferences("Data", MODE_PRIVATE);

        f = false;

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                int num = new Random().nextInt(2) + 1;

                if (num == 1) return;
                mInterstitialAd.show();
            }
        });


        bestScore = preferences.getInt("bestScore", 0);

        gameOver = (TextView)findViewById(R.id.textView2);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/game_over_font.ttf");
        gameOver.setTypeface(font2);
        score_text2 = (TextView) findViewById(R.id.score_text2);
        best_score_text = (TextView) findViewById(R.id.best_score_text);
        sumOfTaps = (TextView)findViewById(R.id.all_taps);
        funnyComment = (TextView)findViewById(R.id.funny_comment);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/funny_comment_font.ttf");
        funnyComment.setTypeface(font1);

        intent = getIntent();

        int score = intent.getIntExtra("score",0);




        if(score+100<=bestScore){
            switch(new Random().nextInt(3)){
                case 0:
                    funnyComment.setText("Really lame...What happend?! Are you on drugs?");
                    break;
                case 1:
                    funnyComment.setText("Did someone hit you in the head?!");
                    break;
                case 2:
                    funnyComment.setText("We suggest you to check if your brain is healthy");
                    break;
            }
        }
        else if(score+50<=bestScore){
            switch(new Random().nextInt(3)){
                case 0:
                    funnyComment.setText("You are a real loser. Go take some Ritalin");
                    break;
                case 1:
                    funnyComment.setText("Can't you do a little better than this? A dead man can try harder");
                    break;
                case 2:
                    funnyComment.setText("You should drink more Cofee");
                    break;
            }
        }
        else if(score+25<=bestScore){
            switch(new Random().nextInt(3)){
                case 0:
                    funnyComment.setText("You were close, you probably just forgot to take steroids today");
                    break;
                case 1:
                    funnyComment.setText("Concentrate!! Stop checking out people while you're playing");
                    break;
                case 2:
                    funnyComment.setText("You are good but you should work your arms better at the gym");
                    break;
            }
        }
        else if(score <= bestScore){
            switch(new Random().nextInt(3)){
                case 0:
                    funnyComment.setText("Go bang your head on the wall 3 times as a punishment. You were so close!!");
                    break;
                case 1:
                    funnyComment.setText("Why do you hate yourself?! Couldn't you just break the record?!");
                    break;
                case 2:
                    funnyComment.setText("You are so stupid!!! You were so close!!!");
                    break;
            }
        }
        else{
            switch(new Random().nextInt(3)){
                case 0:
                    funnyComment.setText("WOW!! You broke your record, your next step is running to be the presedent of the United State");
                    break;
                case 1:
                    funnyComment.setText("You have special skills. You should have a lot of kids in order to improve the world");
                    break;
                case 2:
                    funnyComment.setText("Are you a bird? Are you a plane? No!! You are Superman!!!");
                    break;
            }
        }

        if (score > bestScore) {
            bestScore = score;
            preferences.edit().putInt("bestScore", bestScore).commit();
        }
        int lastSumOfTaps = preferences.getInt("sumOfTaps", 0);
        preferences.edit().putInt("sumOfTaps", lastSumOfTaps + score).commit();
        sumOfTaps.setText(Html.fromHtml("You have tapped " + "<b>" + (lastSumOfTaps + score) + "</b>" + " times"));
        score_text2.setText("" + score);
        best_score_text.setText(Html.fromHtml("Best score: " + "<b>" + bestScore + "</b>"));
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    public void playAgain(View view) {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }
}
