package com.example.or.makeitwhite;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8045128595154184~9448495752");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("5367E3475FFB1BB172548F52E51DC504")
                .build();
        mAdView.loadAd(adRequest);

        logo = (TextView)findViewById(R.id.make_it_white);
        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/logo_font.ttf");
        logo.setTypeface(myFont);
    }

    public void onClick(View v){
        startActivity(new Intent(this, GameActivity.class));
    }


}
