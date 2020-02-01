package com.detectspycamera.detectspycamera;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Changing_Room extends AppCompatActivity {
    ImageView bk_btn;
    private AdsManager adsManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_changing__room);

        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        adsManager = new AdsManager(this, adContainerView);
        adsManager.loadMobUpBanner();
        adsManager.loadMoPubInterstitial();

        this.bk_btn = findViewById(R.id.bk_btn);
        this.bk_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Changing_Room.this.finish();
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
