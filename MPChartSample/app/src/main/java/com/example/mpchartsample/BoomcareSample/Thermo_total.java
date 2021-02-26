
package com.example.mpchartsample.BoomcareSample;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Thermo_total extends AppCompatActivity {

    String TAG = "Thermo_total";

    private LineChart mChart;
    private ArrayList<String> date = new ArrayList<>();

    ArrayList<HashMap<String, String>> GroupMap;
    HashMap<String, String> ChildMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temptotalchart);
        mChart = (LineChart) findViewById(R.id.tempTotalChart);
        chartSetting();

    }

    private void chartSetting() {

        mChart.getDescription().setEnabled(false);
        mChart.setNoDataText("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setPinchZoom(true);
        mChart.setBackgroundColor(Color.TRANSPARENT);

        addEntry();

        Legend l = mChart.getLegend();
        l.setForm(LegendForm.CIRCLE);
        l.setTextColor(Color.BLACK);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(false);
        xl.setDrawAxisLine(false);
        xl.setValueFormatter(new LabelFormatter(date));
//        xl.setGranularityEnabled(true);
//        xl.setLabelCount(date.size());

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.TRANSPARENT);
        leftAxis.setAxisMaximum(42f);
        leftAxis.setAxisMinimum(35f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setEnabled(false);

    }

    public void addEntry() {
        GroupMap = new ArrayList<>();
        String address = "http://192.168.0.113:3232/process/getTempData";
        StringRequest request = new StringRequest(Request.Method.POST, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e(TAG, "onResponse(getTempData) 호출됨 :" + response);

                            JSONArray jarr = new JSONArray(response);

                            int count = jarr.length();
                            ArrayList<Entry> tempData = new ArrayList<Entry>();

                            if (count > 0) {

                                String[] time = new String[count];
                                float[] temp = new float[count];

                                for (int i = 0; i < count; i++) {

                                    ChildMap = new HashMap<String, String>();
                                    JSONObject order = jarr.getJSONObject(i);
                                    ChildMap.put("babyOID", order.getString("babyOID"));
                                    ChildMap.put("time", order.getString("time"));
                                    ChildMap.put("temp", order.getString("temp"));
                                    GroupMap.add(ChildMap);

                                }

                                int setInt = 0;
                                for (int i = count - 1; i >= 0; i--) {
                                    JSONObject order = jarr.getJSONObject(i);

                                    time[i] = order.getString("time").substring(6, 8) + "/" + order.getString("time").substring(10, 12); // date에 저장하고 formatter로  출력
                                    temp[i] = Float.parseFloat(order.getString("temp"));
                                    tempData.add(new Entry(setInt, temp[i]));

                                    date.add(time[i]);
                                    setInt++;
                                }

                                LineDataSet set = new LineDataSet(tempData, "온도");
                                set.setAxisDependency(AxisDependency.LEFT);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    set.setCircleColor(getColor(R.color.temp));
                                    set.setColor(getColor(R.color.temp));
                                } else {
                                    set.setCircleColor(getResources().getColor(R.color.temp));
                                    set.setColor(getResources().getColor(R.color.temp));
                                }

                                set.setLineWidth(2f);
                                set.setCircleRadius(4f);
                                set.setFillAlpha(65);
                                set.setFillColor(ColorTemplate.getHoloBlue());
                                set.setHighLightColor(Color.BLACK);
                                set.setValueTextColor(Color.BLACK);
                                set.setValueTextSize(11f);
                                set.setDrawValues(true);

                                Log.e(TAG, String.valueOf(date.size()));
                                LineData data = new LineData(set);
                                data.setValueFormatter(new LargeValueFormatter());
                                mChart.getXAxis().setAxisMinimum(0f);
                                mChart.getXAxis().setAxisMaximum(date.size());
                                mChart.notifyDataSetChanged();
                                mChart.setVisibleXRange(0f,6f);
                                mChart.moveViewToX(data.getEntryCount() - 5);
                                mChart.setData(data);
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
                params.put("babyname", "3");


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
