package com.example.mpchartsample.BoomcareSample;

import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mpchartsample.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Thermo_day extends AppCompatActivity {

    String TAG = "Thermo_day";

    ArrayList<HashMap<String, String>> GroupMap;
    HashMap<String, String> ChildMap;

    private LineChart mChart;
    TextView check_date;

    String date;
    float last_temp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempchart);

        mChart = (LineChart) findViewById(R.id.tempChart);
//        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        //mChart.setAutoScaleMinMaxEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.TRANSPARENT);

        LineData data = new LineData();

        // add empty data
        mChart.setData(data);

        Legend l = mChart.getLegend();
        l.setForm(LegendForm.CIRCLE);
        l.setTextColor(Color.BLACK);

        XAxis xl = mChart.getXAxis();
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);

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
        addEntry();
    }

    public void addEntry(){
        GroupMap = new ArrayList<>();
        String address = "http://192.168.0.113:3232/process/getTempDataDay";
        StringRequest request = new StringRequest(Request.Method.POST, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("addEntry", "onResponse(getTempDataDay) 호출됨 :" + response);

                            JSONArray jarr = new JSONArray(response);

                            int getcount = jarr.length();
                            if (getcount > 0) {

                                LineData data = mChart.getData();

                                if (data != null) {
                                    ILineDataSet set = data.getDataSetByIndex(0);
                                    // set.addEntry(...); // can be called as well

                                    if (set == null) {
                                        set = createSet();
                                        data.addDataSet(set);
                                    }

                                    String[] Xvalue = new String[getcount];
                                    float[] Yvalue = new float[getcount];

                                    for (int i = 0; i < getcount; i++) {

                                        ChildMap = new HashMap<String, String>();
                                        JSONObject order = jarr.getJSONObject(i);
                                        ChildMap.put("babyOID",order.getString("babyOID"));
                                        ChildMap.put("time",order.getString("time"));
                                        ChildMap.put("temp",order.getString("temp"));
                                        GroupMap.add(ChildMap);

                                    }
                                    for (int i = getcount-1; i >= 0; i--) {
                                        JSONObject order = jarr.getJSONObject(i);

                                        Xvalue[i] = order.getString("time").substring(18, 23);
                                        Yvalue[i] = Float.parseFloat(order.getString("temp"));

//                                        data.addXValue(Xvalue[i]);              // x축의 값 정하는 부분인거 같음
                                        data.addEntry(new Entry((Yvalue[i]), set.getEntryCount()), 0);

                                    }
                                    mChart.notifyDataSetChanged();
                                    mChart.setVisibleXRangeMaximum(5);
//                                    mChart.moveViewToX(data.getXValCount() - 6);
                                    last_temp = Float.parseFloat(GroupMap.get(0).get("temp"));
                                }
                            }else{
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
                params.put("date", "2021년 02월 26일");
                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);

    }

    public void broadcastEntry(final String Xvalue, final float Yvalue){
        date = check_date.getText().toString();
        String address = "http://192.168.0.113:3232/process/getTempDataDay";
        StringRequest request = new StringRequest(Request.Method.POST, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "onResponse(getTempDataDay) 호출됨 :" + response);

                            JSONArray jarr = new JSONArray(response);

                            int getcount = jarr.length();
                            if (getcount > 0) {

                                for (int i = 0; i < getcount; i++) {

                                    ChildMap = new HashMap<String, String>();
                                    JSONObject order = jarr.getJSONObject(i);
                                    ChildMap.put("babyOID",order.getString("babyOID"));
                                    ChildMap.put("time", order.getString("time"));
                                    ChildMap.put("temp", order.getString("temp"));
                                    GroupMap.add(ChildMap);

                                }
                            }

                            LineData data = mChart.getData();

                            if (data != null) {
                                ILineDataSet set = data.getDataSetByIndex(0);
                                // set.addEntry(...); // can be called as well

                                if (set == null) {
                                    set = createSet();
                                    data.addDataSet(set);
                                }

//                                data.addXValue(Xvalue);              // x축의 값 정하는 부분인거 같음
                                data.addEntry(new Entry((Yvalue), set.getEntryCount()), 0);
                                mChart.notifyDataSetChanged();

                                //얘네들로 간격 조절 할 수 있다.
                                mChart.setVisibleXRangeMaximum(5);
//                                mChart.moveViewToX(data.getXValCount() - 6);
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
                params.put("date", "2021년 02월 26일");

                return params;
            }
        };
        request.setShouldCache(false);
        Volley.newRequestQueue(this).add(request);

    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "온도");
        set.setAxisDependency(AxisDependency.LEFT);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            set.setCircleColor(getColor(R.color.temp));
            set.setColor(getColor(R.color.temp));
        }else{
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
        return set;
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