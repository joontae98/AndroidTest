package com.example.mpcharttest.BoomcareSample;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mpcharttest.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class BottleChart extends AppCompatActivity {


    BarChart barChart;
    BarData barData;
    List<BarEntry> yVals1 = new ArrayList<BarEntry>();
    List<BarEntry> yVals2 = new ArrayList<BarEntry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottlechart);
        barChart = (BarChart) findViewById(R.id.bottleChart);

        getBarChart();
    }

    public void getBarChart() {

        barChart.getAxisRight().setEnabled(true);
        barChart.setScaleEnabled(false);   // chart 확대 축소
        barChart.setEnabled(true);  //활성화 / 비활성화
        barChart.setDrawValueAboveBar(true); //valueAbove 그래프의 값을 그래프 위에 출력합니다.
        barChart.setTouchEnabled(true);    // chart 터치
//        Description di = new Description();     //그래프 설명
//        di.setText("text test");
        barChart.getDescription().setEnabled(false);

        setData();

        XAxis xl = barChart.getXAxis();
        xl.setDrawAxisLine(false);      //가로선
        xl.setDrawGridLines(false);      //가이드라인
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);     //그래프의 시작위치
        xl.setCenterAxisLabels(true);
        xl.setAxisMinimum(0);           //x축의 최소값   - 설정하지 않으면 시작값과 끝의 값이 이상한 값으로 설정됨
        xl.setAxisMaximum(yVals2.size());//x축의 최대값
        xl.setValueFormatter(new ValueFormatter() {
            private String[] mMonths = new String[]{
                    "01/01", "01/03", "01/04", "01/05", "01/06", "01/08", "01/10", "01/11", "01/12", "01/13", "01/14"
            };
            @Override
            public String getFormattedValue(float value) {
                if (value < 0){
                    Log.e("err",String.valueOf(value)+"end");
                    return "err";

                }
                Log.e("value tag",String.valueOf(value)+"end");
                return mMonths[Math.round(value)%mMonths.length];
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMaximum(1800f);      //setAxisMixValue, setAxisMinValue 함수 대신 사용
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setValueFormatter(new LargeValueFormatter());
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMaximum(200f);
        rightAxis.setAxisMinimum(0f);

        Legend le = barChart.getLegend();
        le.setForm(Legend.LegendForm.SQUARE);
        le.setFormSize(12f);
        le.setTextSize(13f);
        le.setXEntrySpace(10f);

    }

    private void setData() {

        float groupSpace = 0.3f;
        float barSpace = 0.05f;
        float barWidth = 0.3f;

        float height = 100f;
        float weight = 5f;

        for (int i = 0; i < 12; i++) {

            yVals1.add(new BarEntry(i, height));
            yVals2.add(new BarEntry(i, weight));
            height += 50f;
            weight += 2f;

        }

        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "분유 전체");
        set2 = new BarDataSet(yVals2, "모유 전체");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            set1.setColor(getColor(R.color.bottle));
        } else {
            set1.setColor(getResources().getColor(R.color.bottle));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            set2.setColor(getColor(R.color.milk));
        } else {
            set2.setColor(getResources().getColor(R.color.milk));
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
        barChart.moveViewToX(set1.getEntryCount() - 5);    //시작시 보여지는 x의 위치
        barChart.animateY(500);
        barChart.invalidate();

    }
}
