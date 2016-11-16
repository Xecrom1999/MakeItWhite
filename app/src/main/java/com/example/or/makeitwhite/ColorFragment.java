package com.example.or.makeitwhite;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    RelativeLayout layout;
    Communicator comm;
    boolean active;
    String[] blueColors;
    String[] redColors;
    String[] yellowColors;
    String[] greenColors;
    int position;
    int firstColor;
    View v;
    GradientDrawable background;
    TextView taps_left_text;
    int tapsLeft;
    MediaPlayer disappearing_sound;

    int num_of_taps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_color, container, false);

        active = true;
        comm = (Communicator)getActivity();
        position = new Random().nextInt(8)+3;
        num_of_taps = position;
        layout = (RelativeLayout)v.findViewById(R.id.colorBackground);
        Drawable background1 = layout.getBackground();
        background = (GradientDrawable) background1;
        disappearing_sound = MediaPlayer.create(getContext(), R.raw.disappears);
        disappearing_sound.setVolume(1f, 1f);
        tapsLeft = position;

        firstColor = new Random().nextInt(5);

        switch(firstColor) {
            case 0:
                //blue
                background.setColor(Color.parseColor("#00E5FF"));
                break;
            case 1:
                //red
                background.setColor(Color.parseColor("#ff0000"));
                break;
            case 2:
                //yellow
                background.setColor(Color.parseColor("#FFEB3B"));
                break;
            case 3:
                //green
                background.setColor(Color.parseColor("#76FF03"));
                break;
            case 4:
                //pink
                background.setColor(Color.parseColor("#E040FB"));
                break;

        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active) {
                    lowerColor();
                }
                comm.updateScore();
                tapsLeft -= 1;
                taps_left_text.setText(tapsLeft+"");
                }

        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taps_left_text = (TextView) view.findViewById(R.id.taps_left_text);
        Typeface myFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/taps_font.otf");
        taps_left_text.setTypeface(myFont);
        taps_left_text.setText(tapsLeft+"");
    }

    public void lowerColor(){
        position -= 1;
        if(position == 0){
            active = false;
            if (disappearing_sound.isPlaying())
                disappearing_sound.stop();
            disappearing_sound.start();
            comm.requestRandomClick(num_of_taps);
        }
        AlphaAnimation alpha = new AlphaAnimation((position+1)/10F, position/10F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        v.startAnimation(alpha);

    }


}
