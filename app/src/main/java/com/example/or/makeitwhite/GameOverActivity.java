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

        int mins = intent.getIntExtra("mins", 0);
        int secs = intent.getIntExtra("secs", 0);
        int milliseconds = intent.getIntExtra("mills", 0);

        String time = String.format("%02d", secs) + ":" +  String.format("%03d", milliseconds);
        if (mins > 0) time = mins + ":" + time;

        time_text.setText("Your time: " + time);

    }


}
