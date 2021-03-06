package com.detectspycamera.detectspycamera;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.anastr.speedviewlib.Gauge;


public class meterDetector extends AppCompatActivity implements SensorEventListener {
    private final String TAG = "FBLogs";
    /* access modifiers changed from: private */

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
    private AdsManager adsManager;

    public void onAccuracyChanged(Sensor sensor2, int i) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_meter_detector);
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        adsManager = new AdsManager(this, adContainerView);
        adsManager.loadMobUpBanner();
        adsManager.loadMoPubInterstitial();

        this.sensor = findViewById(R.id.sensor_speed);

        this.mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (this.mySensorManager.getDefaultSensor(2) == null) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.diloge);
            dialog.setTitle("Sensor Info");
            dialog.show();
            this.sensor.setText(getString(R.string.does_not_suuport));
        } else {
            this.sensor.setText(getString(R.string.move_phone_near_suspected_devices));
        }
        this.value = findViewById(R.id.m_value_speed);
        this.status = findViewById(R.id.status_speed);
        this.gauge = findViewById(R.id.onlyspeed);
        this.txtx = findViewById(R.id.txtx);
        this.txty = findViewById(R.id.txty);
        this.txtz = findViewById(R.id.txtz);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
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
                this.status.setText(getString(R.string.nothing_detected));
            } else {
                String str = getString(R.string.etected_camera);
                if (sqrt >= 45 && sqrt <= 80) {
                    this.status.setText(str);
                } else if (sqrt > 80 && sqrt <= 120) {
                    this.status.setText(str);
                } else if (sqrt > 120 && sqrt <= 140) {
                    this.status.setText(str);
                } else if (sqrt > 140) {
                    this.status.setText(getString(R.string.etected_camera));
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
