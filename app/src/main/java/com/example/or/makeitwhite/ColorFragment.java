package com.example.or.makeitwhite;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    RelativeLayout layout;
    Communicator comm;
    boolean clickable, active;
    String[] whiteColors;
    String[] blueColors;
    String[] redColors;
    String[] yellowColors;
    String[] greenColors;
    int position;
    int firstColor;
    View v;



    public ColorFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        active = true;
        comm = (Communicator)getActivity();
        v = inflater.inflate(R.layout.fragment_color, container, false);
        whiteColors = new String[]{"#000000",
        "#191919",
        "#323232",
        "#4c4c4c",
        "#666666",
        "#7f7f7f",
        "#999999",
        "#b2b2b2",
        "#cccccc",
        "#e5e5e5",
        "#ffffff"};
        blueColors = new String[]{"#0c4dff",
        "#245eff",
        "#3c70ff",
        "#5482ff",
        "#6d94ff",
        "#85a6ff",
        "#9db7ff",
        "#b6c9ff",
        "#cedbff",
        "#e6edff",
        "#ffffff"};
        redColors = new String[]{"#e72020",
        "#e93636",
        "#eb4c4c",
        "#ee6262",
        "#f07979",
        "#f38f8f",
        "#f5a5a5",
        "#f7bcbc",
        "#fad2d2",
        "#fce8e8",
        "#ffffff"};
        yellowColors = new String[]{"#d09300",
        "#d49d19",
        "#d9a832",
        "#deb34c",
        "#e2be66",
        "#e7c97f",
        "#ecd399",
        "#f0deb2",
        "#f5e9cc",
        "#faf4e5",
        "#ffffff"};
        greenColors = new String[]{"#099c21",
        "#21a537",
        "#3aaf4d",
        "#52b963",
        "#6bc379",
        "#84cd90",
        "#9cd7a6",
        "#b5e1bc",
        "#cdebd2",
        "#e6f5e8",
        "#ffffff"};
        Random rnd1 = new Random();
        position = rnd1.nextInt(8);
        layout = (RelativeLayout)v.findViewById(R.id.colorBackground);

        Random rnd;
            rnd = new Random();
            firstColor = rnd.nextInt(4);

            switch(firstColor){
                case 0:
                        layout.setBackgroundColor(Color.parseColor(blueColors[position]));
                    break;
                case 1:
                        layout.setBackgroundColor(Color.parseColor(redColors[position]));
                    break;
                case 2:
                        layout.setBackgroundColor(Color.parseColor(yellowColors[position]));
                    break;
                case 3:
                        layout.setBackgroundColor(Color.parseColor(greenColors[position]));
                    break;

            }



        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch(firstColor){
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

                GameActivity.score += 1;
                comm.updateScore();
                }

        });



        return v;
    }

    public void lowerColor(String[] firstColors){
        position += 1;
        if(position == 10){
            active = false;
            comm.requestRandomClick();
        }
        layout.setBackgroundColor(Color.parseColor(firstColors[position]));

    }


}
