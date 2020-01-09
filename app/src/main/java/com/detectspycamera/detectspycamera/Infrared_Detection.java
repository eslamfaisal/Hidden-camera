package com.detectspycamera.detectspycamera;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Infrared_Detection extends BaseActivity implements Callback {
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    RelativeLayout button2;
    Camera camera;
    Context context;
    boolean previewing = false;
    String stringPath = "/sdcard/samplevideo.3gp";
    SurfaceHolder surfaceHolder;
    SurfaceView surfaceView;
    TextView txt;

    public void surfaceCreated(SurfaceHolder surfaceHolder2) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_infrared__detection);
        loadBigAd();
        this.context = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFormat(0);
        this.surfaceView = findViewById(R.id.camerapreview);
        this.surfaceHolder = this.surfaceView.getHolder();
        this.surfaceHolder.addCallback(this);
        this.surfaceHolder.setType(3);
        this.button2 = findViewById(R.id.button2);
        this.txt = findViewById(R.id.txt);
        if (checkPermission()) {
            this.button2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Infrared_Detection.this.show_camera();
                    Infrared_Detection.this.button2.setVisibility(View.GONE);
                    Infrared_Detection.this.txt.setVisibility(View.GONE);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean checkPermission() {
        if (VERSION.SDK_INT >= 23) {
            String str = "android.permission.CAMERA";
            if (ContextCompat.checkSelfPermission(this.context, str) != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.context, str)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
                    builder.setCancelable(true);
                    builder.setTitle("Permission Necessary.....");
                    builder.setMessage("Camera Permission Is Must To Use Feature Of Camera Service");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @TargetApi(16)
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions((Activity) Infrared_Detection.this.context, new String[]{"android.permission.CAMERA"}, Infrared_Detection.MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                        }
                    });
                    builder.create().show();
                } else {
                    ActivityCompat.requestPermissions((Activity) this.context, new String[]{str}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                }
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 123 && iArr.length > 0 && iArr[0] == 0) {
            this.button2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Infrared_Detection.this.show_camera();
                    Infrared_Detection.this.button2.setVisibility(View.GONE);
                    Infrared_Detection.this.txt.setVisibility(View.GONE);
                }
            });
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder2, int i, int i2, int i3) {
        if (this.previewing) {
            this.camera.stopPreview();
            this.previewing = false;
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder2) {
        if (this.previewing) {
            this.camera.stopPreview();
            this.camera.release();
            this.camera = null;
            this.previewing = false;
        }
    }

    public void show_camera() {
        this.camera = Camera.open();
        Camera camera2 = this.camera;
        if (camera2 != null) {
            try {
                camera2.setPreviewDisplay(this.surfaceHolder);
                this.camera.startPreview();
                this.camera.setDisplayOrientation(90);
                this.previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
