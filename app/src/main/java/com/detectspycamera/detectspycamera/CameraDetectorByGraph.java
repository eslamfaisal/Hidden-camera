package com.detectspycamera.detectspycamera;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class CameraDetectorByGraph extends AppCompatActivity implements SensorEventListener, OnChartValueSelectedListener {
    private final String TAG = MainActivity.class.getSimpleName();
    int[] beep = {R.raw.beep};
    MediaPlayer mediaPlayer;
    private Gauge gauge;
    private LineChart mChart;
    private SensorManager mySensorManager;
    private TextView sensor;
    private SensorManager sensorManager;
    private TextView status;
    private TextView value;
    private AdsManager adsManager;

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
        LineDataSet lineDataSet = new LineDataSet(null, getString(R.string.dynamic_data));
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
        setContentView(R.layout.activity_camera_detector_by_graph);
        FrameLayout adContainerView = findViewById(R.id.ad_view_container);
        adsManager = new AdsManager(this, adContainerView);
        adsManager.loadMobUpBanner();
        adsManager.loadMoPubInterstitial();

        this.sensor = findViewById(R.id.sensor_graph);

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
        this.value = findViewById(R.id.m_value_graph);
        this.status = findViewById(R.id.status_graph);
        this.gauge = findViewById(R.id.showinprogress);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.mediaPlayer = MediaPlayer.create(this, this.beep[0]);
        LineChart lineChart = findViewById(R.id.mygraph_fullchart);
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
                this.status.setText(getString(R.string.nothing_detected));
            } else {
                String str =getString(R.string.etected_camera);
                if (j >= 45 && j <= 80) {
                    this.status.setText(str);
                } else if (j > 80 && j <= 120) {
                    this.status.setText(str);
                } else if (j > 120 && j <= 140) {
                    this.status.setText(str);
                } else if (j > 140) {
                    this.status.setText(getString(R.string.etected_camera));
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
