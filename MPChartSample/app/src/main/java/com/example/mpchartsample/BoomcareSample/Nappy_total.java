package com.example.mpchartsample.BoomcareSample;

import android.graphics.Typeface;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Nappy_total extends AppCompatActivity {

    private BarChart mChart;
    private ArrayList<String> date = new ArrayList<>();

    int index;
    int total_type_1;
    int total_type_2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nappychart);


        mChart = (BarChart) findViewById(R.id.nappyChart);
        mChart.setScaleEnabled(false);
        mChart.setEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(8f);
        l.setTextSize(10f);
        l.setXEntrySpace(4f);

        XAxis xl = mChart.getXAxis();
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setCenterAxisLabels(true);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setValueFormatter(new LabelFormatter(date));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMaximum(20f);
        leftAxis.setAxisMinimum(0f);

        mChart.getAxisRight().setEnabled(false);

        setBarchart_data();
    }

    public void setBarchart_data() {
        float groupSpace = 0.3f;
        float barSpace = 0.05f;
        float barWidth = 0.3f;

        String address = "http://192.168.0.113:3232/process/getNappyData";
        StringRequest request = new StringRequest(Request.Method.POST, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("MainActivity", "onResponse(getNappyData) 호출됨 :" + response);

                            JSONArray jarr = new JSONArray(response);

                            int data_size = jarr.length();


                            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                            ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

                            index = 0;
                            total_type_1 = 0;
                            total_type_2 = 0;


                            if (data_size > 0) {

                                int[] type = new int[data_size];
                                String[] time = new String[data_size];

                                for (int i = 0; i < data_size; i++) {

                                    JSONObject order = jarr.getJSONObject(i);

                                    type[i] = Integer.parseInt(order.getString("type"));
                                    time[i] = order.getString("time");
                                }

                                if (data_size == 1) {

                                    date.add(time[0].substring(6, 8) + "/" + time[0].substring(10, 12));

                                    if (type[0] == 0) {
                                        yVals1.add(new BarEntry(0, 1));
                                        total_type_1++;
                                    } else if (type[0] == 1) {
                                        yVals2.add(new BarEntry(0, 1));
                                        total_type_2++;
                                    }
                                    index++;


                                } else {

                                    int barchart_type1_data = 0;
                                    int barchart_type2_data = 0;

                                    for (int i = data_size - 1; i >= 0; i--) {

                                        if (type[i] == 0) {
                                            barchart_type1_data++;
                                            total_type_1++;
                                        } else if (type[i] == 1) {
                                            barchart_type2_data++;
                                            total_type_2++;
                                        }

                                        if (i == 0) {

                                            yVals1.add(new BarEntry(index, barchart_type1_data));
                                            yVals2.add(new BarEntry(index, barchart_type2_data));
                                            date.add(time[i].substring(6, 8) + "/" + time[i].substring(10, 12));
                                            index++;

                                        } else if (!time[i].substring(0, 13).equals(time[i - 1].substring(0, 13))) {

                                            yVals1.add(new BarEntry(index, barchart_type1_data));
                                            yVals2.add(new BarEntry(index, barchart_type2_data));
                                            date.add(time[i].substring(6, 8) + "/" + time[i].substring(10, 12));
                                            index++;

                                            barchart_type1_data = 0;
                                            barchart_type2_data = 0;
                                        }
                                    }
                                }
                            }

                            BarDataSet set1, set2;

                            set1 = new BarDataSet(yVals1, "소변");
                            set2 = new BarDataSet(yVals2, "대변");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                set1.setColor(getColor(R.color.nappy_so));
                            } else {
                                set1.setColor(getResources().getColor(R.color.nappy_so));
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                set2.setColor(getColor(R.color.nappy_dae));
                            } else {
                                set2.setColor(getResources().getColor(R.color.nappy_dae));
                            }

//                            set1.setValues(yVals1);
//                            set2.setValues(yVals2);

                            BarData data = new BarData(set1, set2);


                            set1.setValueFormatter(new LargeValueFormatter());
                            set2.setValueFormatter(new LargeValueFormatter());

                            data.setValueTextSize(10f);
                            data.setBarWidth(barWidth);

                            mChart.getXAxis().setAxisMinimum(0);
                            mChart.getXAxis().setAxisMaximum(date.size());
                            mChart.setData(data);
                            mChart.setFitBars(true);
                            mChart.groupBars(0f, groupSpace, barSpace);
                            mChart.setVisibleXRange(0f, 6f);

                            mChart.moveViewToX(data.getEntryCount() - 5); // 2021. 02. 23 박준태 그래프에 마지막 데이터 나오도록 수정
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
                params.put("babyname", "7");

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

    public class LabelFormatter implements IAxisValueFormatter {
        private final ArrayList<String> mLabels;
        public LabelFormatter(ArrayList<String> labels) {
            mLabels = labels;
        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // 2021. 02. 26 박준태 value 값이 date.size 크거나 음수면 빈 문자열 반환으로 x축 라벨에 출력 안함
            if (value < 0 || value >= mLabels.size()){
                Log.e("err",String.valueOf(value)+"end");
                return "";
            }
            Log.e("value tag",String.valueOf(value)+"end");
            return mLabels.get(Math.round(value)%mLabels.size());
        }
    }
}