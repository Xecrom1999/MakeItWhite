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

    int num_of_taps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_color, container, false);

        active = true;
        comm = (Communicator)getActivity();
        blueColors = new String[]{"#0D47A1", "#1565C0", "#1976D2", "#1E88E5", "#2196F3", "#42A5F5", "#64B5F6", "#90CAF9", "#BBDEFB", "#E3F2FD", "#ffffff"};
        redColors = new String[]{"#B71C1C", "#C62828", "#D32F2F", "#E53935", "#F44336", "#EF5350", "#E57373", "#EF9A9A", "#FFCDD2", "#FFEBEE", "#ffffff"};
        yellowColors = new String[]{"#F57F17", "#F9A825", "#FBC02D", "#FDD835", "#FFEB3B", "#FFEE58", "#FFF176", "#FFF59D", "#FFF9C4", "#FFFDE7", "#ffffff"};
        greenColors = new String[]{"#1B5E20", "#2E7D32", "#388E3C", "#43A047", "#4CAF50", "#66BB6A", "#81C784", "#A5D6A7", "#C8E6C9", "#E8F5E9", "#ffffff"};
        Random rnd1 = new Random();
        position = rnd1.nextInt(8);
        num_of_taps = 10 - position;
        layout = (RelativeLayout)v.findViewById(R.id.colorBackground);
        Drawable background1 = layout.getBackground();
        background = (GradientDrawable) background1;

        tapsLeft = 10 - position;

        Random rnd;
        rnd = new Random();
        firstColor = rnd.nextInt(4);

        switch(firstColor) {
            case 0:
                background.setColor(Color.parseColor(blueColors[position]));
                break;
            case 1:
                background.setColor(Color.parseColor(redColors[position]));
                break;
            case 2:
                background.setColor(Color.parseColor(yellowColors[position]));
                break;
            case 3:
                background.setColor(Color.parseColor(greenColors[position]));
                break;

        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(active) {
                    switch (firstColor) {
                        case 0:
                            lowerColor(blueColors);
                            break;
                        case 1:
                            lowerColor(redColors);
                            break;
                        case 2:
                            lowerColor(yellowColors);
                            break;
                        case 3:
                            lowerColor(greenColors);
                            break;
                    }

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

    public void lowerColor(String[] firstColors){
        position += 1;
        if(position == 10){
            active = false;
            comm.requestRandomClick(num_of_taps);
        }
        background.setColor(Color.parseColor(firstColors[position]));

    }


}
