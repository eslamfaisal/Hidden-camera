package com.detectspycamera.detectspycamera;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Outside extends BaseActivity {
    ImageView bk_btn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_outside);
        loadBigAd();
        loadAd();
        this.bk_btn = findViewById(R.id.bk_btn);
        this.bk_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Outside.this.finish();
            }
        });
    }
}
