package com.example.or.makeitwhite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView time_text;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        time_text = (TextView) findViewById(R.id.time_text);

        intent = getIntent();

        int score = intent.getIntExtra("score",0);

        time_text.setText("Your score: " + score);

    }


}
