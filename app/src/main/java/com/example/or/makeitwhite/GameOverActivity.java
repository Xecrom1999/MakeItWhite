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

public class GameOverActivity extends AppCompatActivity {

    TextView time_text;
    TextView best_score_text;
    Intent intent;
    SharedPreferences preferences;
    int bestScore;
    InterstitialAd mInterstitialAd;
    boolean f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);

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

                mInterstitialAd.show();
            }
        });

        preferences = getSharedPreferences("Data", MODE_PRIVATE);

        bestScore = preferences.getInt("bestScore", 0);

        time_text = (TextView) findViewById(R.id.score_text);
        best_score_text = (TextView) findViewById(R.id.best_score_text);

        intent = getIntent();

        int score = intent.getIntExtra("score",0);

        if (score < 10)
            Toast.makeText(getApplicationContext(), "You're gay!", Toast.LENGTH_SHORT).show();
        else if (score < 50)
            Toast.makeText(getApplicationContext(), "Less than 50? probably a woman!", Toast.LENGTH_SHORT).show();
        else if (score < 60)
            Toast.makeText(getApplicationContext(), "Not enough", Toast.LENGTH_SHORT).show();
        else if (score < 70)
            Toast.makeText(getApplicationContext(), "At least 70 points", Toast.LENGTH_SHORT).show();
        else if (score < 80)
            Toast.makeText(getApplicationContext(), "You're the best!", Toast.LENGTH_SHORT).show();
        else if (score < 90)
            Toast.makeText(getApplicationContext(), "Impossible skills!", Toast.LENGTH_SHORT).show();

        if (score > bestScore) {
            bestScore = score;
            preferences.edit().putInt("bestScore", bestScore).commit();
            Toast.makeText(getApplicationContext(), "It's a new best score Oh My God! אללה וואכבר", Toast.LENGTH_SHORT).show();
        }

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
