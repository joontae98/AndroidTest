package com.example.mpchartsample;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {


    BarChart barChart;
    BarData barData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        barChart = (BarChart) findViewById(R.id.barChart);

        // 리스트를 이용하여 entry 타입 데이터 저장
        // chart, x, y 축 설정
        // dataset 설정
        // chart에 데이터 삽입

        getBarChart();
    }

    public void getBarChart() {
        float groupSpace = 0.06f;
        float barSpace = 0.02f;
        float barWidth = 0.45f;
        float height = 50f;
        float weight = 5f;

        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();
        for (int i = 0; i < 9; i++) {

            yVals1.add(new BarEntry(i, height));
            yVals2.add(new BarEntry(i, weight));
            height += 7f;
            weight += 2f;

        }

        barChart.getAxisRight().setEnabled(true);
        barChart.setScaleEnabled(false);   // chart 확대 축소
        barChart.setEnabled(true);  //활성화 / 비활성화
        barChart.setDrawValueAboveBar(true); //valueAbove 그래프의 값을 그래프 위에 출력합니다.
        barChart.setTouchEnabled(true);    // chart 터치

//        Description di = new Description();     //그래프 설명
//        di.setText("text test");
        barChart.getDescription().setEnabled(false);

        XAxis xl = barChart.getXAxis();
        xl.setDrawAxisLine(false);      //가로선
        xl.setDrawGridLines(false);      //가이드라인
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);     //그래프의 시작위치
        xl.setCenterAxisLabels(true);
        xl.setAxisMinimum(0);           //x축의 최소값
        xl.setAxisMaximum(yVals2.size());//x축의 최대값

        Legend le = barChart.getLegend();
        le.setForm(Legend.LegendForm.SQUARE);
        le.setFormSize(12f);
        le.setTextSize(13f);
        le.setXEntrySpace(10f);

        YAxis leftAxis = barChart.getAxisLeft();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            leftAxis.setTextColor(getColor(R.color.height));
        } else {
            leftAxis.setTextColor(getResources().getColor(R.color.height));
        }
        leftAxis.setAxisMaximum(120f);      //setAxisMixValue, setAxisMinValue 함수 대신 사용
        leftAxis.setAxisMinimum(40f);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rightAxis.setTextColor(getColor(R.color.weight));
        } else {
            rightAxis.setTextColor(getResources().getColor(R.color.weight));
        }
        rightAxis.setAxisMaximum(24f);
        rightAxis.setAxisMinimum(2f);
        rightAxis.setDrawGridLines(false);

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "키 (cm)");
        set2 = new BarDataSet(yVals2, "체중 (kg)");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            set1.setColor(getColor(R.color.height));
        } else {
            set1.setColor(getResources().getColor(R.color.height));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            set2.setColor(getColor(R.color.weight));
        } else {
            set2.setColor(getResources().getColor(R.color.weight));
        }
        // 데이터 값의 포멧 지정
        set1.setValueFormatter(new LargeValueFormatter());
        set2.setValueFormatter(new LargeValueFormatter());
        //bar의 의존
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);

        //BarData에 설정 저장
        barData = new BarData(set1, set2);
        barData.setBarWidth(barWidth);
        barData.setValueTextSize(10f);

        //Data를 barChart에 넘김
        barChart.setData(barData);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.setVisibleXRange(6, 6); //6 ~ 6 사이의 값은 스크롤 없이 출력 가능
        //데이터의 갯수를 출력하는 함수를 찾아야함
//      데이터의 갯수  = barData.getEntryCount()/barData.getDataSetCount() or set1.getEntryCount()
        barChart.moveViewToX(barData.getDataSetCount()-5);    //시작시 보여지는 x의 위치
        barChart.animateY(500);
        barChart.invalidate();
    }
}
