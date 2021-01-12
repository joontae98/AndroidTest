package com.example.mpcharttest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;

public class RadarChartActivity extends AppCompatActivity {

    RadarChart radarChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radarchart);
        radarChart = (RadarChart) findViewById(R.id.radarChart);
        // x축 -> 시계바늘 축, y축 -> 원형 축
        getRadarChart();
    }

    private void getRadarChart() {

        List<RadarEntry> yVals1 = new ArrayList<RadarEntry>();
        List<RadarEntry> yVals2 = new ArrayList<RadarEntry>();

        yVals1.add(new RadarEntry(100));
        yVals1.add(new RadarEntry(100));
        yVals1.add(new RadarEntry(100));
        yVals1.add(new RadarEntry(100));
        yVals1.add(new RadarEntry(100));
        yVals1.add(new RadarEntry(100));

        yVals2.add(new RadarEntry(70));
        yVals2.add(new RadarEntry(59));
        yVals2.add(new RadarEntry(91));
        yVals2.add(new RadarEntry(66));
        yVals2.add(new RadarEntry(72));
        yVals2.add(new RadarEntry(88));

        radarChart.setWebLineWidth(1f);
        radarChart.setWebColor(Color.LTGRAY);
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColorInner(Color.LTGRAY);
        radarChart.setWebAlpha(100);
        radarChart.getDescription().setEnabled(false);
        radarChart.animateXY(
                1400, 1400, Easing.EaseInOutQuad, Easing.EaseInOutQuad);

        MarkerView mv = new RadarMarkerView(this,R.layout.radar_markerview);
        mv.setChartView(radarChart);
        radarChart.setMarker(mv);

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] mActivities = new String[]{"체온", "수유", "수면", "체중", "키","배변"};

            @Override
            public String getFormattedValue(float value) {
                return mActivities[(int) value % mActivities.length];
            }
        });

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setLabelCount(4, false);      // 그물 갯수
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f); // 기존 150
        yAxis.setDrawLabels(false);

        Legend l = radarChart.getLegend();
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);

        RadarDataSet set1 = new RadarDataSet(yVals1, "표준 데이터");
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(false);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(yVals2, "이용자 데이터");
        set2.setColor(getResources().getColor(R.color.total));
        set2.setFillColor(getResources().getColor(R.color.total));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setHighlightCircleStrokeWidth(0f);
        set2.setHighlightCircleOuterRadius(5f);
        set2.setHighlightCircleStrokeColor(Color.rgb(0, 0, 0));
        set2.setHighlightCircleFillColor(Color.rgb(0, 0, 0));
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);

        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setHighlightEnabled(true);
        data.setValueFormatter(new LargeValueFormatter());

        radarChart.setData(data);
        radarChart.getData().setHighlightEnabled(true);
        radarChart.invalidate();
    }

}
