package com.example.or.makeitwhite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class BestGameActivityEver extends AppCompatActivity {

    RelativeLayout theLayout;
    View item1;
    View item2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        theLayout = (RelativeLayout) findViewById(R.id.theLayout);

        View view = getLayoutInflater().inflate(R.layout.fragment_item_layout, theLayout, false);

        theLayout.addView(view);
        view.getLayoutParams().width = 100;
        view.getLayoutParams().height = 100;

        view.setX(200);
        view.setY(200);

    }
}
