package com.example.mpchartsample.BoomcareSample;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hansol on 2017-01-20.
 */

public class Physical_check extends AppCompatActivity {

    private BarChart mChart;
    private ArrayList<String> date = new ArrayList<>();
    int data_size;

    ArrayList<HashMap<String, String>> GroupMap;
    HashMap<String, String> ChildMap;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicalchart);
        mChart = (BarChart) findViewById(R.id.physicalChart);
        set_chart1_setting();

    }

    public void set_chart1_setting() {

        // no description text
        mChart.getAxisRight().setEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);

        XAxis xl = mChart.getXAxis();
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setCenterAxisLabels(true);
        xl.setValueFormatter(new LabelFormatter(date));

        Legend le = mChart.getLegend();
        le.setForm(Legend.LegendForm.SQUARE);
        le.setFormSize(12f);
        le.setTextSize(13f);
        le.setXEntrySpace(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            leftAxis.setTextColor(getColor(R.color.height));
        } else {
            leftAxis.setTextColor(getResources().getColor(R.color.height));
        }
        leftAxis.setAxisMaximum(120f);
        leftAxis.setAxisMinimum(40f);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = mChart.getAxisRight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rightAxis.setTextColor(getColor(R.color.weight));
        } else {
            rightAxis.setTextColor(getResources().getColor(R.color.weight));
        }
        rightAxis.setAxisMaximum(24f);
        rightAxis.setAxisMinimum(2f);
        rightAxis.setDrawGridLines(false);


        setData();
    }

    public void setData() {
        float groupSpace = 0.3f;
        float barSpace = 0.05f;
        float barWidth = 0.3f;

        GroupMap = new ArrayList<>();
        String address = "http://192.168.0.113:3232/process/getPhyData";
        StringRequest request = new StringRequest(Request.Method.POST, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("MainActivity", "onResponse(getPhyData) 호출됨 :" + response);

                            JSONArray jarr = new JSONArray(response);
                            data_size = jarr.length();
                            int height[] = new int[data_size];
                            float weight[] = new float[data_size];
                            String time[] = new String[data_size];

                            for (int i = 0; i < data_size; i++) {
                                JSONObject order = jarr.getJSONObject(i);
                                ChildMap = new HashMap<String, String>();
                                ChildMap.put("height", order.getString("height"));
                                ChildMap.put("weight", order.getString("weight"));
                                ChildMap.put("time", order.getString("time"));
                                ChildMap.put("babyOID", order.getString("babyOID"));
                                GroupMap.add(ChildMap);

                                height[i] = Integer.parseInt(order.getString("height"));
                                weight[i] = Float.parseFloat(order.getString("weight"));
                                time[i] = order.getString("time");

                            }


                            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                            ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

                            if (data_size != 0) {

                                for (int index = data_size - 1; index >= 0; index--) {

                                    yVals1.add(new BarEntry((data_size - 1) - index, height[index]));
                                    yVals2.add(new BarEntry((data_size - 1) - index, weight[index]));

                                    date.add(time[index].substring(6, 8) + "/" + time[index].substring(10, 12));
                                }

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

                                set1.setValueFormatter(new LargeValueFormatter());
                                set2.setValueFormatter(new LargeValueFormatter());

                                set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                                set2.setAxisDependency(YAxis.AxisDependency.RIGHT);

                                BarData data = new BarData(set1,set2);

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
                            }
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
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        requestQueue.add(request);
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
