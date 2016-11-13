package com.example.or.makeitwhite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView time_text;
    TextView best_score_text;
    Intent intent;
    SharedPreferences preferences;
    int bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_layout);

        preferences = getSharedPreferences("Data", MODE_PRIVATE);

        bestScore = preferences.getInt("bestScore", 0);

        time_text = (TextView) findViewById(R.id.score_text);
        best_score_text = (TextView) findViewById(R.id.best_score_text);

        intent = getIntent();

        int score = intent.getIntExtra("score",0);

        if (score > bestScore) {
            bestScore = score;
            preferences.edit().putInt("bestScore", bestScore).commit();
        }

        time_text.setText("Your score: " + score);
        best_score_text.setText("Best score: " + bestScore);
    }


}
