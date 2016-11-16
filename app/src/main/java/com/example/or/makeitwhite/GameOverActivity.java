package com.example.or.makeitwhite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class GameOverActivity extends AppCompatActivity {

    TextView time_text;
    TextView best_score_text;
    TextView sumOfTaps;
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

        time_text = (TextView) findViewById(R.id.score_text);
        best_score_text = (TextView) findViewById(R.id.best_score_text);
        sumOfTaps = (TextView)findViewById(R.id.all_taps);

        intent = getIntent();

        int score = intent.getIntExtra("score",0);

        if (score > bestScore) {
            bestScore = score;
            preferences.edit().putInt("bestScore", bestScore).commit();
            Toast.makeText(getApplicationContext(), "It's a new best score Oh My God! אללה וואכבר", Toast.LENGTH_SHORT).show();
        }
        int lastSumOfTaps = preferences.getInt("sumOfTaps", 0);
        preferences.edit().putInt("sumOfTaps", lastSumOfTaps + score).commit();
        sumOfTaps.setText("You have tapped " + (lastSumOfTaps+score) + " times");
        time_text.setText("Your score: " + score);
        best_score_text.setText("Best score: " + bestScore);
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
