package com.example.mpchartsample.BoomcareSample;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mpchartsample.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hansol on 2017-01-23.
 */

public class Bottle_total extends AppCompatActivity {

    private BarChart mChart;

    int index;
    int total_drink;
    int total_milk;

    int day_count;

    int drink_count = 0;
    private ArrayList<String> date = new ArrayList<String>();
    private String TAG = "Bottle_total";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottlechart);
        Log.e(TAG,"setBarchart_data before");
        setBarchart_data();
        Log.e(TAG,"setBarchart_data after");


    }

    public void setBarchart_data() {
        float groupSpace = 0.3f;
        float barSpace = 0.05f;
        float barWidth = 0.3f;

        Log.e(TAG,"setBarchart_data");
        String address = "http://192.168.0.113:3232/process/getBottleData";
        StringRequest request = new StringRequest(Request.Method.POST, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("MainActivity", "onResponse(getBottleData) 호출됨 :" + response);

                            JSONArray jarr = new JSONArray(response);

                            int data_size = jarr.length();
                            Log.e(TAG, "data_size " + String.valueOf(data_size));

                            total_drink = 0;
                            total_milk = 0;

                            index = 0;

                            int drink[] = new int[data_size];           // api 호출후 데이터의 크기 만큼의 배열을 생성 why - 호출된 데이터에서 각각의 데이터를 추출하여 배열에 저장하고 데이터를 차트로 만들기 위해
                            int milk[] = new int[data_size];            // test 코드에서는 entry에 바로 데이터를 저장 - total을 계산하기 위해 배열에 한번 저장해야 할 것 같음
                            String time[] = new String[data_size];

                            int day_drink = 0;
                            int day_milk = 0;
                            drink_count = 0;

                            int z = 0;
                            for (int i = data_size - 1; i >= 0; i--) {
                                Log.e(TAG, "i = "+ String.valueOf(i));
                                JSONObject order = jarr.getJSONObject(z);           //json 파일의 첫번째 객체를 배열의 마지막에 대입 why - getBottleData 에서 시간 내림차순으로 리턴하게끔 설정
                                drink[i] = Integer.parseInt(order.getString("drink"));
                                milk[i] = Integer.parseInt(order.getString("milk"));
                                total_drink = total_drink + drink[i];
                                total_milk = total_milk + milk[i];
                                if (drink[i] > 0) {
                                    drink_count++; // 모유 수유 횟수 ++
                                }

                                time[i] = order.getString("time");
                                z++;    // 반복 횟수
                            }


                            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                            ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

                            if (data_size == 0) {

                            } else if (data_size == 1) {

                                yVals1.add(new BarEntry(0, drink[0]));
                                yVals2.add(new BarEntry(0, milk[0]));
                                date.add(time[0].substring(6, 8) + "/" + time[0].substring(10, 12));
                                day_count = 1;

                            } else {

                                String check_time = time[0].substring(6, 13);
                                day_count = 1;

                                for (int j = 1; j < data_size; j++) {
                                    Log.e(TAG, "j = "+ String.valueOf(j));
                                    day_drink = day_drink + drink[j - 1];
                                    day_milk = day_milk + milk[j - 1];

                                    if (check_time.equals(time[j].substring(6, 13))) {
                                        if (j == data_size - 1) {

                                            day_drink = day_drink + drink[j];
                                            day_milk = day_milk + milk[j];
                                            yVals1.add(new BarEntry(index, day_drink));
                                            yVals2.add(new BarEntry(index, day_milk));
                                            date.add(check_time.substring(0, 2) + "/" + check_time.substring(4, 6));
                                        }
                                    } else {

                                        yVals1.add(new BarEntry(index, day_drink));
                                        yVals2.add(new BarEntry(index, day_milk));
                                        date.add(check_time.substring(0, 2) + "/" + check_time.substring(4, 6));

                                        check_time = time[j].substring(6, 13);
                                        day_drink = 0;
                                        day_milk = 0;
                                        day_count++;
                                        index++;

                                        if (j == data_size - 1) {

                                            day_drink = day_drink + drink[j];
                                            day_milk = day_milk + milk[j];
                                            yVals1.add(new BarEntry(index, day_drink));
                                            yVals2.add(new BarEntry(index, day_milk));
                                            date.add(check_time.substring(0, 2) + "/" + check_time.substring(4, 6));

                                        }
                                    }
                                }
                            }
                            mChart = (BarChart) findViewById(R.id.bottleChart);
                            mChart.getAxisRight().setEnabled(true);
                            mChart.setScaleEnabled(false);
                            mChart.setEnabled(true);
                            mChart.setTouchEnabled(true);
                            mChart.setDrawValueAboveBar(true);
                            mChart.getDescription().setEnabled(false);


                            XAxis xl = mChart.getXAxis();
//                            xl.setGranularity(0);
                            xl.setDrawAxisLine(false);
                            xl.setDrawGridLines(false);
                            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xl.setCenterAxisLabels(true);
                            xl.setLabelCount(1);
//                            xl.setValueFormatter(new ValueFormatter() {
//                                @Override
//                                public String getFormattedValue(float value) {
//                                    Log.e(TAG, String.valueOf(value));
//                                    if (value < 0) {
//                                        return "err";
//                                    }
//                                    Log.e(TAG, "data "+String.valueOf(date));
//                                    Log.e(TAG, "date.size "+String.valueOf(date.size()));
//                                    return date.get(Math.round(value/2) % date.size());
//                                }
//                            });

                            YAxis leftAxis = mChart.getAxisLeft();
                            leftAxis.setValueFormatter(new LargeValueFormatter());
                            leftAxis.setAxisMaximum(1800f);
                            leftAxis.setAxisMinimum(0f);
                            leftAxis.setDrawLabels(false);
                            leftAxis.setDrawAxisLine(false);
                            leftAxis.setDrawGridLines(true);

                            YAxis rightAxis = mChart.getAxisRight();
                            rightAxis.setValueFormatter(new LargeValueFormatter());
                            rightAxis.setAxisMaximum(200f);
                            rightAxis.setAxisMinimum(0f);
                            rightAxis.setDrawLabels(false);
                            rightAxis.setDrawAxisLine(false);
                            rightAxis.setDrawGridLines(false);

                            Legend le = mChart.getLegend();
                            le.setForm(Legend.LegendForm.SQUARE);
                            le.setFormSize(12f);
                            le.setTextSize(13f);
                            le.setXEntrySpace(10f);

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

                            set1.setValueFormatter(new LargeValueFormatter());
                            set2.setValueFormatter(new LargeValueFormatter());

                            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);

                            BarData data = new BarData(set1,set2);
                            data.setValueTextSize(10f);
                            data.setBarWidth(barWidth);

                            mChart.setData(data);

                            mChart.getXAxis().setAxisMinimum(0);                        //x축의 시작지점
                            mChart.getXAxis().setAxisMaximum(yVals2.size());            //y축의 시작지점

                            mChart.groupBars(0f, groupSpace, barSpace);
                            // 범위를 준다  앞의 숫자는 date.size 로 하고 6미만이면 6이상부터는 6으로 고정한다
                            mChart.setVisibleXRange(6f, 6f);
                            mChart.moveViewToX(data.getEntryCount() - 5);   // 2021. 01. 26 마지막데이터를 제일 오른쪽에 출력하도록 수정
                            mChart.animateY(500);
                            mChart.invalidate();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("identi", "1@1.1");
                params.put("babyname", "나");
                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}