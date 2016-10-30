package com.example.or.makeitwhite;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    FrameLayout r;


    public ColorFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        active = true;
        comm = (Communicator)getActivity();
        v = inflater.inflate(R.layout.fragment_color, container, false);
        r = (FrameLayout)v.getParent();
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
        blueColors = new String[]{"#0099cc",
        "#0089b7",
        "#007aa3",
        "#006b8e",
        "#005b7a",
        "#004c66",
        "#003d51",
        "#002d3d",
        "#001e28",
        "#000f14"};
        redColors = new String[]{"#9c2929",
        "#8c2424",
        "#7c2020",
        "#6d1c1c",
        "#5d1818",
        "#4e1414",
        "#3e1010",
        "#2e0c0c",
        "#1f0808",
        "#0f0404"};
        yellowColors = new String[]{"#ffe54c",
        "#e5ce44",
        "#ccb73c",
        "#b2a035",
        "#99892d",
        "#7f7226",
        "#665b1e",
        "#4c4416",
        "#332d0f",
        "#191607"};
        greenColors = new String[]{"#13d601",
        "#11c000",
        "#0fab00",
        "#0d9500",
        "#0b8000",
        "#096b00",
        "#075500",
        "#054000",
        "#032a00",
        "#011500"};
        position=0;
        layout = (RelativeLayout)v.findViewById(R.id.colorBackground);
        clickable = false;
        Random rnd = new Random();
        firstColor = rnd.nextInt(4);
        switch(firstColor){
            case 0:
                layout.setBackgroundColor(Color.parseColor(blueColors[0]));
                break;
            case 1:
                layout.setBackgroundColor(Color.parseColor(redColors[0]));
                break;
            case 2:
                layout.setBackgroundColor(Color.parseColor(yellowColors[0]));
                break;
            case 3:
                layout.setBackgroundColor(Color.parseColor(greenColors[0]));
                break;
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickable){
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
                    r.setBackgroundColor(0x00000000);
                    clickable = false;
                    comm.requestRandomClick();


                }
            }
        });



        return v;
    }

    public void lowerColor(String[] firstColors){
        position+=1;
        if(position==21) active = false;
        else if(position<10){
            layout.setBackgroundColor(Color.parseColor(firstColors[position]));
        }
        else if(position<21){
            layout.setBackgroundColor(Color.parseColor(whiteColors[position-10]));
        }

    }

    public void setAsClickable(){
        clickable = true;
        r = (FrameLayout)v.getParent();
        r.setBackgroundColor(Color.parseColor("#2200ff"));
    }
}
