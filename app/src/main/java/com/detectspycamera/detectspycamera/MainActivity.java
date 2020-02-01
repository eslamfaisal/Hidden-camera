package com.detectspycamera.detectspycamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "eslamfaisal";
    private Button camera_detect;
    private Button donate;
    private Button m_detect;
    private Button tips;
    private AdsManager adsManager;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        adsManager = new AdsManager(this, adContainerView);
        adsManager.loadMobUpBanner();
        adsManager.loadMoPubInterstitial();


        this.m_detect = findViewById(R.id.m_detect);
        this.camera_detect = findViewById(R.id.camera_detect);
        this.tips = findViewById(R.id.tips);
        this.donate = findViewById(R.id.donate);
        this.m_detect.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, CameraFinderSelection.class));
            }
        });
        this.camera_detect.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Infrared_Detection.class));
            }
        });
        this.tips.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Location.class));
            }
        });
        this.donate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps")));
            }
        });
        // Step 1 - Create an AdView and set the ad unit ID on it.

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.share) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", getString(R.string.share_test)+"\n https://play.google.com/store/apps/details?id=com.detectspycamera.detectspycamera");
            startActivity(Intent.createChooser(intent, "Select Service "));
            return true;
        }
        if (itemId == R.id.i_camera) {
            startActivity(new Intent(this, Infrared_Detection.class));
        }
        return super.onOptionsItemSelected(menuItem);
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
