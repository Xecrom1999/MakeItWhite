package com.example.or.makeitwhite;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class ClassicActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout background;
    int red;
    int green;
    int blue;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic_layout);

        preferences = getSharedPreferences("data", MODE_PRIVATE);

        red = preferences.getInt("red", 0);
        green = preferences.getInt("green", 0);
        blue = preferences.getInt("blue", 0);

        background = (RelativeLayout) findViewById(R.id.background);
        background.setBackgroundColor(Color.rgb(red,green,blue));
        background.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        red++;
        green++;
        blue++;
        background.setBackgroundColor(Color.rgb(red, green, blue));
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferences.edit().putInt("red", red).putInt("green", green).putInt("blue", blue).commit();
    }
}
