package com.example.mpcharttest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {
    PieChart pieChart;
    ArrayList<Integer> colors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        pieChart = (PieChart) findViewById(R.id.pieChart);

        getPieChart();
    }

    private void getPieChart() {

        pieChart.setUsePercentValues(true);         // 값을 %로 변환하여 표현하는 함수
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);         // 표 위치 함수

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);             // 가운데 원형 뚫는 함수
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(55f);       // 투명한 원반경 함수
        pieChart.setRotationEnabled(false);
        pieChart.setDrawSlicesUnderHole(false);

        List<PieEntry> yValues = new ArrayList<>();

        colors = new ArrayList<Integer>();

        colors.add(getResources().getColor(R.color.sleep_gray));
        colors.add(getResources().getColor(R.color.sleep));
        yValues.add(new PieEntry(750,""));
        yValues.add(new PieEntry(930-750,"자는중"));
        yValues.add(new PieEntry(1440-930,""));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(0);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);          // 값 출력
        pieChart.setData(data);

    }

}
