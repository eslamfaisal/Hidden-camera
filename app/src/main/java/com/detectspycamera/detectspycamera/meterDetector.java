package com.detectspycamera.detectspycamera;

import android.app.Dialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.anastr.speedviewlib.Gauge;
import com.google.android.gms.ads.formats.NativeAd;

public class meterDetector extends BaseActivity implements SensorEventListener {
    private final String TAG = "FBLogs";
    /* access modifiers changed from: private */
    public NativeAd nativeAd;
    int[] beep = {R.raw.beep};
    MediaPlayer mediaPlayer;
    private LinearLayout adView;
    private Gauge gauge;
    private SensorManager mySensorManager;
    private TextView sensor;
    private SensorManager sensorManager;
    private TextView status;
    private TextView txtx;
    private TextView txty;
    private TextView txtz;
    private TextView value;

    public void onAccuracyChanged(Sensor sensor2, int i) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_meter_detector);
        loadAd();
        loadBigAd();
        this.sensor = findViewById(R.id.sensor_speed);
        String str = "sensor";
        this.mySensorManager = (SensorManager) getSystemService(str);
        if (this.mySensorManager.getDefaultSensor(2) == null) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.diloge);
            dialog.setTitle("Sensor Info");
            dialog.show();
            this.sensor.setText("Sorry Your Device Does Not Support This App");
        } else {
            this.sensor.setText("Move Phone Near Suspected Devices");
        }
        this.value = findViewById(R.id.m_value_speed);
        this.status = findViewById(R.id.status_speed);
        this.gauge = findViewById(R.id.onlyspeed);
        this.txtx = findViewById(R.id.txtx);
        this.txty = findViewById(R.id.txty);
        this.txtz = findViewById(R.id.txtz);
        this.sensorManager = (SensorManager) getSystemService(str);
        this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 2) {
            float f = sensorEvent.values[0];
            float f2 = sensorEvent.values[1];
            float f3 = sensorEvent.values[2];
            long sqrt = (long) Math.sqrt((double) ((f * f) + (f2 * f2) + (f3 * f3)));
            if (sqrt > 80 && sqrt < 140) {
                if (this.mediaPlayer == null) {
                    this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
                }
                this.mediaPlayer.start();
            }
            if (sqrt <= 80 && sqrt >= 140) {
                MediaPlayer mediaPlayer2 = this.mediaPlayer;
                if (mediaPlayer2 != null) {
                    mediaPlayer2.stop();
                }
            }
            if (sqrt < 45) {
                this.status.setText("NOTHING DETECTED");
            } else {
                String str = "POTENTIAL CAMERA DETECTED";
                if (sqrt >= 45 && sqrt <= 80) {
                    this.status.setText(str);
                } else if (sqrt > 80 && sqrt <= 120) {
                    this.status.setText(str);
                } else if (sqrt > 120 && sqrt <= 140) {
                    this.status.setText(str);
                } else if (sqrt > 140) {
                    this.status.setText("DETECTED HIGH CAMERA RADIATIONS");
                    if (this.mediaPlayer == null) {
                        this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
                    }
                    this.mediaPlayer.start();
                }
            }
            this.txtx.setText(String.valueOf((int) f));
            this.txty.setText(String.valueOf((int) f2));
            this.txtz.setText(String.valueOf((int) f3));
            TextView textView = this.value;
            StringBuilder sb = new StringBuilder();
            sb.append(sqrt);
            sb.append(" µT");
            textView.setText(sb.toString());
            double d = (double) sqrt;
            Double.isNaN(d);
            this.gauge.speedTo((float) ((long) ((d / 2000.0d) * 100.0d)));
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        SensorManager sensorManager2 = this.sensorManager;
        sensorManager2.registerListener(this, sensorManager2.getDefaultSensor(2), 3);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this);
    }

}
