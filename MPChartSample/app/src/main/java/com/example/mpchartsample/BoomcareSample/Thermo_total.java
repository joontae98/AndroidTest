
package com.example.mpchartsample.BoomcareSample;

import android.graphics.Color;
import android.graphics.Typeface;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Thermo_total extends AppCompatActivity {

    String TAG = "Thermo_total";

    private LineChart mChart;
    TextView avg_temp;

    String main_name;
    TextView high_temp;
    TextView low_temp;
    TextView count_temp;

    Typeface tfs;

    ArrayList<HashMap<String, String>> GroupMap;
    HashMap<String, String> ChildMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temptotalchart);


        mChart = (LineChart) findViewById(R.id.tempChart);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        //mChart.setAutoScaleMinMaxEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.TRANSPARENT);

        LineData data = new LineData();

        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setForm(LegendForm.CIRCLE);
        l.setTextColor(Color.BLACK);

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.BLACK);
        xl.setDrawGridLines(false);
        xl.setDrawAxisLine(false);
        xl.setTypeface(tfs);


        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
        leftAxis.setTextColor(Color.TRANSPARENT);
        leftAxis.setAxisMaxValue(42f);
        leftAxis.setAxisMinValue(35f);
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
        String address = "http://192.168.0.113:3232/process/getTempData";
        StringRequest request = new StringRequest(Request.Method.POST, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e(TAG, "onResponse(getTempData) 호출됨 :" + response);

                            JSONArray jarr = new JSONArray(response);

                            int count = jarr.length();

                            if (count>0) {

                                LineData data = mChart.getData();

                                if (data != null) {
                                    ILineDataSet set = data.getDataSetByIndex(0);
                                    // set.addEntry(...); // can be called as well

                                    if (set == null) {
                                        set = createSet();
                                        data.addDataSet(set);
                                    }
                                    String[] Xvalue = new String[count];
                                    float[] Yvalue = new float[count];

                                    for (int i = 0; i < count; i++) {

                                        ChildMap = new HashMap<String, String>();
                                        JSONObject order = jarr.getJSONObject(i);
                                        ChildMap.put("babyOID",order.getString("babyOID"));
                                        ChildMap.put("time",order.getString("time"));
                                        ChildMap.put("temp",order.getString("temp"));
                                        GroupMap.add(ChildMap);



                                    }

                                    for (int i = count-1; i >= 0; i--) {
                                        JSONObject order = jarr.getJSONObject(i);

                                        Xvalue[i] = order.getString("time").substring(6,8) +"/"+order.getString("time").substring(10,12);
                                        Yvalue[i] = Float.parseFloat(order.getString("temp"));

//                                        data.addXValue(Xvalue[i]);              // x축의 값 정하는 부분인거 같음
                                        data.addEntry(new Entry((Yvalue[i]), set.getEntryCount()), 0);

                                    }
                                    mChart.notifyDataSetChanged();
                                    mChart.setVisibleXRangeMaximum(5);
//                                    mChart.moveViewToX(data.getXValCount() - 6);
                                }
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
        Volley.newRequestQueue(this).add(request);

    }


    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Temperature");
        set.setAxisDependency(AxisDependency.LEFT);
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
//        set.setCircleColorHole(Color.WHITE);
        set.setDrawValues(true);
        set.setValueTypeface(tfs);
        return set;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
