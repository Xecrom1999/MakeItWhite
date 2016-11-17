package com.example.or.makeitwhite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    TextView logo;
    TextView best_score_text;
    TextView sumOfTaps;

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

        sumOfTaps = (TextView)findViewById(R.id.text);
        best_score_text = (TextView) findViewById(R.id.best_score_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences("Data", MODE_PRIVATE);
        int bestScore = preferences.getInt("bestScore", 0);
        best_score_text.setText(Html.fromHtml("Best score: " + "<b>" + bestScore + "</b>"));

        int taps = preferences.getInt("sumOfTaps", 0);
        sumOfTaps.setText( Html.fromHtml("You have tapped " + "<b>" + (taps) + "</b>" + " times"));
    }

    public void onClick(View v){
        startActivity(new Intent(this, GameActivity.class));
    }
}
