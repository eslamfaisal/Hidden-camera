package com.detectspycamera.detectspycamera;

import android.content.Intent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends BaseActivity {
    private final String TAG = "eslamfaisal";
    Button camera_detect;
    Button donate;
    InterstitialAd mInterstitialAd;
    Button m_detect;
    Button tips;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private SensorManager mySensorManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        loadAd();

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
            intent.putExtra("android.intent.extra.TEXT", "Hidden Camera detector Install and Share:\n https://play.google.com/store/apps/details?id=com.detectspycamera.detectspycamera");
            startActivity(Intent.createChooser(intent, "Select Service "));
            return true;
        }
        if (itemId == R.id.i_camera) {
            startActivity(new Intent(this, Infrared_Detection.class));
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
