package com.detectspycamera.detectspycamera;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Bedroom extends AppCompatActivity {
    ImageView bk_btn;
    AdsManager adsManager;
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bedroom);
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        adsManager = new AdsManager(this, adContainerView);
        adsManager.loadMobUpBanner();

        this.bk_btn = (ImageView) findViewById(R.id.bk_btn);
        this.bk_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bedroom.this.finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adsManager.moPubView != null) {
            adsManager.moPubView.destroy();
        }
        if (adsManager.mInterstitial != null) {
            adsManager.mInterstitial.destroy();
        }
    }
}
