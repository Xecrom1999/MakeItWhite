package com.example.or.makeitwhite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8045128595154184~9448495752");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdView mAdView1 = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("5367E3475FFB1BB172548F52E51DC504")
                .build();
        AdRequest adRequest1 = new AdRequest.Builder()
                .addTestDevice("5367E3475FFB1BB172548F52E51DC504")
                .build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest1);
    }

    public void onClick(View v){
        startActivity(new Intent(this, GameActivity.class));
    }


}
