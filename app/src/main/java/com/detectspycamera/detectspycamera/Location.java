package com.detectspycamera.detectspycamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;


public class Location extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private Button bd_btn;
    private Button bt_btn;
    private Button button;
    private Button changing_btn;

    private Button outside_btn;
    private AdsManager adsManager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_location);
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        adsManager = new AdsManager(this, adContainerView);
        adsManager.loadMobUpBanner();

        this.bd_btn = findViewById(R.id.bd_btn);
        this.bt_btn = findViewById(R.id.bt_btn);
        this.changing_btn = findViewById(R.id.changing_btn);
        this.outside_btn = findViewById(R.id.outside_btn);
        this.bd_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Location.this.startActivity(new Intent(Location.this, Bedroom.class));
            }
        });
        this.bt_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Location.this.startActivity(new Intent(Location.this, Bathroom.class));
            }
        });
        this.changing_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Location.this.startActivity(new Intent(Location.this, Changing_Room.class));
            }
        });
        this.outside_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Location.this.startActivity(new Intent(Location.this, Outside.class));
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
            intent.putExtra("android.intent.extra.TEXT", "Hidden Camera Detector Install and Share:\n https://play.google.com/store/apps/details?id=com.detectspycamera.detectspycamera");
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
