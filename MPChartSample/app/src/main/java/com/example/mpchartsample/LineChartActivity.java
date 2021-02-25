package com.example.mpchartsample;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {
    LineChart lineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        lineChart = (LineChart) findViewById(R.id.lineChart);

        getLineChart();
    }

    private void getLineChart() {

        float temp = 35.5f;

        List<Entry> tempData = new ArrayList<Entry>();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 1) {
                temp -= 0.7f;
            } else {
                temp += 0.9f;
            }
            tempData.add(new Entry(i, temp));
        }

        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(true);
        lineChart.setDragEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false); //*


        Legend l = lineChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextColor(Color.BLACK);

        XAxis xl = lineChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM); //x축의 값 밑에 출력
        xl.setGranularity(1f);     //축의 최소 간격
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(false);
        xl.setDrawAxisLine(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.TRANSPARENT);
        leftAxis.setAxisMaximum(42f);
        leftAxis.setAxisMinimum(35f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setEnabled(true);
        leftAxis.setGranularity(0.1f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setEnabled(false);

        LineDataSet set1 = new LineDataSet(tempData, "온도");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData lineData = new LineData(set1);

        lineChart.setData(lineData);

        lineChart.notifyDataSetChanged();
        lineChart.setVisibleXRangeMaximum(5);
        lineChart.moveViewToX(lineData.getEntryCount() - 6);
        lineChart.invalidate();


    }
}
