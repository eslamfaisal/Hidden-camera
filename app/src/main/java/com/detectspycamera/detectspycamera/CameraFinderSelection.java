package com.detectspycamera.detectspycamera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CameraFinderSelection extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    ImageView bk_btn;
    Button btnboth;
    Button btngraph;
    Button btnspeed;
    private Button button;


    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_camera_finder_selection);

        this.btnboth = findViewById(R.id.btnspeedandgraph);
        this.btngraph = findViewById(R.id.btngraph);
        this.btnspeed = findViewById(R.id.btnspeed);
        this.bk_btn = findViewById(R.id.bk_btn_selection);
        this.bk_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CameraFinderSelection.this.finish();
            }
        });
        this.btnspeed.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(CameraFinderSelection.this, meterDetector.class));
            }
        });
        this.btngraph.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CameraFinderSelection cameraFinderSelection = CameraFinderSelection.this;
                cameraFinderSelection.startActivity(new Intent(cameraFinderSelection, CameraDetectorByGraph.class));
            }
        });
        this.btnboth.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CameraFinderSelection cameraFinderSelection = CameraFinderSelection.this;
                cameraFinderSelection.startActivity(new Intent(cameraFinderSelection, CameraDetecter.class));
            }
        });
    }

}
