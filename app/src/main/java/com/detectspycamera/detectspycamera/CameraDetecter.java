package com.detectspycamera.detectspycamera;

import android.app.Dialog;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import com.github.anastr.speedviewlib.Gauge;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import androidx.appcompat.app.AppCompatActivity;

public class CameraDetecter extends BaseActivity implements SensorEventListener, OnChartValueSelectedListener {
    private final String TAG = MainActivity.class.getSimpleName();
    int[] beep = {R.raw.beep};
    InterstitialAd mInterstitialAd;
    MediaPlayer mediaPlayer;
    private AdView adView;
    private Gauge gauge;
    private InterstitialAd interstitialAd;
    private LineChart mChart;
    private SensorManager mySensorManager;
    private TextView sensor;
    private SensorManager sensorManager;
    private TextView status;
    private TextView value;

    public void onAccuracyChanged(Sensor sensor2, int i) {
    }

    public void onNothingSelected() {
    }

    public void onValueSelected(Entry entry, Highlight highlight) {
    }

    private void addEntry(float f) {
        LineData lineData = this.mChart.getData();
        if (lineData != null) {
            ILineDataSet iLineDataSet = lineData.getDataSetByIndex(0);
            if (iLineDataSet == null) {
                iLineDataSet = createSet();
                lineData.addDataSet(iLineDataSet);
            }
            lineData.addEntry(new Entry((float) iLineDataSet.getEntryCount(), f), 0);
            lineData.notifyDataChanged();
            this.mChart.notifyDataSetChanged();
            this.mChart.setVisibleXRangeMaximum(2000.0f);
            this.mChart.moveViewToX((float) lineData.getEntryCount());
        }
    }

    private LineDataSet createSet() {
        LineDataSet lineDataSet = new LineDataSet(null, "Dynamic Data");
        lineDataSet.setAxisDependency(AxisDependency.LEFT);
        lineDataSet.setColor(ColorTemplate.getHoloBlue());
        lineDataSet.setLineWidth(2.0f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet.setValueTextColor(-1);
        lineDataSet.setValueTextSize(9.0f);
        lineDataSet.setDrawValues(false);
        return lineDataSet;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_camera_detecter);
        loadAd();
        loadBigAd();
        this.sensor = findViewById(R.id.sensor);
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
        this.value = findViewById(R.id.m_value);
        this.status = findViewById(R.id.status);
        this.gauge = findViewById(R.id.AwesomeSpeedometer);
        this.sensorManager = (SensorManager) getSystemService(str);
        this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
        LineChart lineChart = findViewById(R.id.mygraph);
        this.mChart = lineChart;
        lineChart.setOnChartValueSelectedListener(this);
        this.mChart.getDescription().setEnabled(true);
        this.mChart.setTouchEnabled(true);
        this.mChart.setDragEnabled(true);
        this.mChart.setScaleEnabled(true);
        this.mChart.setDrawGridBackground(false);
        this.mChart.setPinchZoom(true);
        this.mChart.setBackgroundColor(Color.parseColor("#050505"));
        LineData lineData = new LineData();
        lineData.setValueTextColor(-1);
        this.mChart.setData(lineData);
        Legend legend = this.mChart.getLegend();
        legend.setForm(LegendForm.LINE);
        legend.setTextColor(-1);
        XAxis xAxis = this.mChart.getXAxis();
        xAxis.setTextColor(-1);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);
        YAxis axisLeft = this.mChart.getAxisLeft();
        axisLeft.setTextColor(-1);
        axisLeft.setAxisMaximum(100.0f);
        axisLeft.setAxisMinimum(0.0f);
        axisLeft.setDrawGridLines(true);
        this.mChart.getAxisRight().setEnabled(false);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 2) {
            float f = sensorEvent.values[0];
            float f2 = sensorEvent.values[1];
            float f3 = sensorEvent.values[2];
            double sqrt = Math.sqrt((double) ((f * f) + (f2 * f2) + (f3 * f3)));
            float f4 = (float) ((int) sqrt);
            addEntry(f4);
            this.mChart.getAxisLeft().setAxisMaximum(f4 + 40.0f);
            long j = (long) sqrt;
            if (j > 80 && j < 140) {
                if (this.mediaPlayer == null) {
                    this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
                }
                this.mediaPlayer.start();
            }
            if (j <= 80 && j >= 140) {
                MediaPlayer mediaPlayer2 = this.mediaPlayer;
                if (mediaPlayer2 != null) {
                    mediaPlayer2.stop();
                }
            }
            if (j < 45) {
                this.status.setText("NOTHING DETECTED");
            } else {
                String str = "POTENTIAL CAMERA DETECTED";
                if (j >= 45 && j <= 80) {
                    this.status.setText(str);
                } else if (j > 80 && j <= 120) {
                    this.status.setText(str);
                } else if (j > 120 && j <= 140) {
                    this.status.setText(str);
                } else if (j > 140) {
                    this.status.setText("DETECTED HIGH CAMERA RADIATIONS");
                    if (this.mediaPlayer == null) {
                        this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
                    }
                    this.mediaPlayer.start();
                }
            }
            TextView textView = this.value;
            StringBuilder sb = new StringBuilder();
            sb.append(j);
            sb.append(" µT");
            textView.setText(sb.toString());
            double d = (double) j;
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
