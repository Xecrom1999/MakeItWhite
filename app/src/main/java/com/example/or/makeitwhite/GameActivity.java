package com.example.or.makeitwhite;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements Communicator {

    ColorFragment[] fragments;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fm = getSupportFragmentManager();
        fragments = new ColorFragment[4];
        fragments[0] = (ColorFragment)fm.findFragmentById(R.id.fragment);
        fragments[1] = (ColorFragment)fm.findFragmentById(R.id.fragment2);
        fragments[2] = (ColorFragment)fm.findFragmentById(R.id.fragment3);
        fragments[3] = (ColorFragment)fm.findFragmentById(R.id.fragment4);





    }

    public void requestRandomClick(){
        boolean f = false;
        int num = 0;
        while(!f){
            Random rnd = new Random();
            num = rnd.nextInt(4);
            if(fragments[num].active) f = true;
            if(!fragments[0].active && !fragments[1].active && !fragments[2].active && !fragments[3].active){
                Intent i = new Intent(this, GameOverActivity.class);
                //
                startActivity(i);
            }
        }
        fragments[num].setAsClickable();
    }

    public void onC(View v){
        requestRandomClick();

    }
}
