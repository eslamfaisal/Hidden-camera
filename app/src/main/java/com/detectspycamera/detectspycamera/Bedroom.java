package com.detectspycamera.detectspycamera;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Bedroom extends BaseActivity {
    ImageView bk_btn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bedroom);
        loadAd();
        loadBigAd();
        this.bk_btn = (ImageView) findViewById(R.id.bk_btn);
        this.bk_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bedroom.this.finish();
            }
        });
    }
}
