//package com.example.mpchartsample.BoomcareSample;
//
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.text.SpannableString;
//import android.util.Log;
//import android.view.ContextMenu;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.res.ResourcesCompat;
//import androidx.fragment.app.Fragment;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.mpchartsample.R;
//import com.github.mikephil.charting.animation.Easing;
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import static android.content.Context.MODE_PRIVATE;
//
///**
// * Created by hansol on 2017-01-03.
// */
//
//public class Sleep_day extends AppCompatActivity {
//
//    ArrayList<HashMap<String, String>> GroupMap;
//    HashMap<String, String> ChildMap;
//
//    private PieChart mchart;
//
//    String babyOID;
//    String Start;
//    String End;
//
//    String main_name;
//    String date_sleep;
//    TextView daypage_date;
//    ListView status;
//
//    ArrayList<Integer> colors;
//    int color_GRAY;
//    int color_MINT;
//
//    boolean sp = false;
//
//    int hole_day_sleep;
//
//    int day_time = 12;
//    int night_time = 20;
//    String last_time4;
//
//    int mYear=0, mMonth=0, mDay=0;
//    Calendar calendar = Calendar.getInstance();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sleepchart);
//        mchart = (PieChart) findViewById(R.id.sleepChart);
//
//        String strNow_korea = this_time(3);
//
//        set_piechart();
//    }
//
//    public void set_piechart() {
//
//        Log.e("set_pieChart","setPieChart");
//        GroupMap = new ArrayList<>();
//        String address = "http://192.168.0.113:3232/process/getSleepDataDay";
//        StringRequest request = new StringRequest(Request.Method.POST, address,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.e("Sleep_day", "onResponse(getSleepDataDay) 호출됨 :" + response);
//
//                            JSONArray jarr = new JSONArray(response);
//
//                            mchart.setDrawHoleEnabled(true);
//                            mchart.setHoleRadius(70f);
//                            mchart.setTransparentCircleRadius(55f);
//
//                            mchart.getDescription().setEnabled(false);
//                            mchart.setDrawCenterText(true);
//                            mchart.setDrawSliceText(true);
//                            mchart.setDrawSlicesUnderHole(false);
//
//                            mchart.setRotationAngle(-90f);
//                            mchart.setRotationEnabled(false);
//
//                            Legend l = mchart.getLegend();
//
//                            l.setForm(Legend.LegendForm.SQUARE);
//                            l.setTextSize(11f);
//
//                            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//                            ArrayList<String> xVals = new ArrayList<String>();
//
//                            ///////////////////////////////////////////////////////////////////////////////////////////////
//
//                            int data_size = jarr.length();
//                            String time_str[] = new String[data_size];
//                            String time_end[] = new String[data_size];
//
//                            hole_day_sleep = 0;
//
//                            for (int i=0; i<data_size; i++){
//                                ChildMap = new HashMap<String, String>();
//                                JSONObject order = jarr.getJSONObject(i);
//                                time_str[i] = order.getString("starttime");
//                                time_end[i] = order.getString("endtime");
//                                ChildMap.put("starttime", time_str[i]);
//                                ChildMap.put("endtime", time_end[i]);
//                                ChildMap.put("babyOID", order.getString("babyOID"));
//                                GroupMap.add(ChildMap);
//                            }
//
//                            int start_point = 0;
//                            colors = new ArrayList<Integer>();
//
//                            if(data_size==0){
//                                colors.add(color_GRAY);
//
//                                yVals1.add(new Entry(1440 , 1));
//                                xVals.add("No_Sleep");
//
//                            }else{
//
//                                if (!time_str[data_size-1].substring(0, 13).equals(time_end[data_size-1].substring(0, 13))) {
//
//                                    if (time_end[data_size-1].substring(0, 13).equals(daypage_date.getText().toString().substring(0, 13))) {
//
//                                        int hour2 = Integer.parseInt(time_end[data_size-1].substring(18, 20));
//                                        int min2 = Integer.parseInt(time_end[data_size-1].substring(21, 23));
//
//                                        int total_min2 = min2 + hour2 * 60;
//
//                                        colors.add(color_MINT);
//                                        colors.add(color_GRAY);
//                                        hole_day_sleep = hole_day_sleep + total_min2;
//
//                                        yVals1.add(new Entry(total_min2, 0));
//                                        xVals.add("");
//
//                                        start_point = total_min2;
//
//                                        if(data_size==1) {
//
//                                            yVals1.add(new Entry(1440 - total_min2, 1));
//                                            xVals.add("");
//
//                                        }
//                                        data_size--;
//
//                                    } else if (time_end[data_size-1].substring(0, 13).equals("1234567890123")) {
//
//                                        String strNow_korea = this_time(2);
//
//                                        int hour2 = Integer.parseInt(strNow_korea.substring(18, 20));
//                                        int min2 = Integer.parseInt(strNow_korea.substring(21, 23));
//                                        int total_min2 = min2 + hour2 * 60;
//
//                                        int hour1 = Integer.parseInt(time_str[data_size-1].substring(18, 20));
//                                        int min1 = Integer.parseInt(time_str[data_size-1].substring(21, 23));
//                                        int total_min1 = (min1 + hour1 * 60);
//
//                                        if(time_str[data_size-1].substring(0, 13).equals(daypage_date.getText().toString().substring(0, 13))){
//
//                                            colors.add(color_GRAY);
//                                            colors.add(color_MINT);
//
//                                            yVals1.add(new Entry(total_min1, 1));
//                                            xVals.add("");
//
//                                            yVals1.add(new Entry(total_min2 - total_min1, 0));
//                                            xVals.add("자는중");
//
//                                            hole_day_sleep = hole_day_sleep + total_min2 - total_min1;
//
//                                            if(1440-total_min2!=0) {
//
//                                                yVals1.add(new Entry(1440 - total_min2, 1));
//                                                xVals.add("");
//
//                                            }
//                                            data_size--;
//
//
//                                        }else{
//
//                                            colors.add(color_MINT);
//                                            colors.add(color_GRAY);
//
//                                            yVals1.add(new Entry(total_min2, 0));
//                                            xVals.add("자는중");
//                                            hole_day_sleep = hole_day_sleep + total_min2;
//
//                                            yVals1.add(new Entry(1440 - total_min2, 1));
//                                            xVals.add("");
//                                            data_size--;
//
//                                        }
//
//
//                                    } else if (time_str[data_size-1].substring(0, 13).equals(daypage_date.getText().toString().substring(0, 13)) && !time_end[data_size-1].equals("1234567890123")) {
//
//                                        int hour1 = Integer.parseInt(time_str[data_size-1].substring(18, 20));
//                                        int min1 = Integer.parseInt(time_str[data_size-1].substring(21, 23));
//                                        int total_min1 = (min1 + hour1 * 60);
//
//                                        colors.add(color_GRAY);
//                                        colors.add(color_MINT);
//
//                                        yVals1.add(new Entry(total_min1, 1));
//                                        xVals.add("");
//
//                                        yVals1.add(new Entry(1440 - total_min1, 0));
//                                        xVals.add("");
//                                        hole_day_sleep = hole_day_sleep + 1440 - total_min1;
//
//                                        data_size--;
//                                    }
//                                }
//
//                                for(int j = data_size-1; j>=0; j--){
//
//                                    if(colors.size()==0){
//                                        colors.add(color_GRAY);
//                                        colors.add(color_MINT);
//                                    }
//
//                                    if (j==0 && !time_str[0].substring(0, 13).equals(time_end[0].substring(0, 13)) && !time_end[j].equals("1234567890123")) {
//
//                                        int hour1 = Integer.parseInt(time_str[j].substring(18, 20));
//                                        int min1 = Integer.parseInt(time_str[j].substring(21, 23));
//                                        int total_min1 = (min1 + hour1 * 60);
//
//                                        yVals1.add(new Entry(total_min1 - start_point, 1));
//                                        xVals.add("");
//
//                                        yVals1.add(new Entry(1440 - total_min1, 0));
//                                        xVals.add("");
//                                        hole_day_sleep = hole_day_sleep + 1440 - total_min1;
//
//                                    }else if(j==0 && time_end[j].equals("1234567890123")) {
//
//                                        String strNow_korea =  this_time(2);
//
//                                        int hour2 = Integer.parseInt(strNow_korea.substring(18, 20));
//                                        int min2 = Integer.parseInt(strNow_korea.substring(21, 23));
//                                        int total_min2 = min2 + (hour2 * 60);
//
//                                        int hour1 = Integer.parseInt(time_str[j].substring(18, 20));
//                                        int min1 = Integer.parseInt(time_str[j].substring(21, 23));
//                                        int total_min1 = min1 + (hour1 * 60);
//
//                                        if(time_str[0].substring(0, 13).equals(strNow_korea.substring(0, 13))){
//
//                                            yVals1.add(new Entry(total_min1 - start_point, 1));
//                                            xVals.add("");
//
//                                            yVals1.add(new Entry(total_min2 - total_min1, 0));
//                                            xVals.add("자는중");
//                                            hole_day_sleep = hole_day_sleep + total_min2 - total_min1;
//
//                                            if (1440 - total_min2!=0) {
//                                                yVals1.add(new Entry(1440 - total_min2, 1));
//                                                xVals.add("");
//                                            }
//                                        }
//
//                                    }else{
//
//                                        int hour1 = Integer.parseInt(time_str[j].substring(18, 20));
//                                        int min1 = Integer.parseInt(time_str[j].substring(21, 23));
//                                        int total_min1 = (min1 + hour1 * 60);
//
//                                        int hour2 = Integer.parseInt(time_end[j].substring(18, 20));
//                                        int min2 = Integer.parseInt(time_end[j].substring(21, 23));
//                                        int total_min2 = min2 + hour2 * 60;
//
//                                        yVals1.add(new Entry(total_min1 - start_point, 1));
//                                        xVals.add("");
//
//                                        yVals1.add(new Entry(total_min2 - total_min1, 0));
//                                        xVals.add("");
//                                        hole_day_sleep = hole_day_sleep + total_min2 - total_min1;
//
//                                        start_point = total_min2;
//
//                                        if(j==0) {
//
//                                            yVals1.add(new Entry(1440 - total_min2, 1));
//                                            xVals.add("");
//
//                                        }
//                                    }
//                                }
//                            }
//
//
//                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//                            PieDataSet dataset = new PieDataSet(yVals1, "");
//                            dataset.setSliceSpace(0);
//                            dataset.setDrawValues(false);
//                            dataset.setSelectionShift(0);
//
//                            if (colors != null) {
//                                dataset.setColors(colors);
//                            }
//                            PieData data = new PieData(xVals, dataset);
////                            data.setValueFormatter(new ValueFormatter() {
////                                @Override
////                                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
////                                    return null;
////                                }
////                            });
//                            data.setValueTextSize(11f);
//                            data.setValueTextColor(Color.WHITE);
//
//                            mchart.setDrawSlicesUnderHole(false);
//
//                            mchart.setTransparentCircleRadius(10f);
//                            mchart.setDrawMarkerViews(false);
//                            mchart.setHighlightPerTapEnabled(false);
//                            mchart.setUnbindEnabled(false);
//                            mchart.setMinimumWidth(0);
//
//                            mchart.setData(data);
//                            mchart.animateY(1000, Easing.EaseInOutQuad);
//                            mchart.highlightValue(null);
//                            mchart.setCenterText(generateCenterSpannableText());
//                            mchart.setCenterTextSize(23f);
//
//                            mchart.invalidate();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("identi", "1@1.1");
//                params.put("babyname", "3");
//                params.put("date", "2021년 02월 26일");
//
//                return params;
//            }
//        };
//        request.setShouldCache(false);
//        Volley.newRequestQueue(this).add(request);
//
//    }
//
//    public String this_time(int type){
//
//        long now = System.currentTimeMillis();
//        // Data 객체에 시간을 저장한다.
//        Date date = new Date(now);
//        // 각자 사용할 포맷을 정하고 문자열로 만든다.
//        SimpleDateFormat sdfNow_korea = null;
//
//        if(type == 1) {
//            sdfNow_korea = new SimpleDateFormat("yyyy년 MM월 dd일 (E)");
//
//        }else if(type == 2){
//            sdfNow_korea = new SimpleDateFormat("yyyy년 MM월 dd일 (E) HH:mm:ss");
//
//        }else if (type == 3){
//            calendar.setTime(date);
//
//            mYear = calendar.get(Calendar.YEAR);
//            mMonth = calendar.get(Calendar.MONTH);
//            mDay = calendar.get(Calendar.DAY_OF_MONTH);
//            sdfNow_korea = new SimpleDateFormat("yyyy년 MM월 dd일 (E)");
//        }
//
//        String strNow_korea = sdfNow_korea.format(date);
//
//        return strNow_korea;
//    }
//
//    private SpannableString generateCenterSpannableText() {
//
//        SpannableString s;
//        int hole_hour = 0;
//        int hole_min = 0;
//
//        if (hole_day_sleep >= 60){
//            hole_hour = hole_day_sleep/60;
//            hole_min = hole_day_sleep - (hole_hour * 60);
//            s = new SpannableString(hole_hour + "시간 " + hole_min + "분");
////            s.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, Integer.toString(hole_hour).length() + Integer.toString(hole_min).length() + 4, 0);
////            s.setSpan(new RelativeSizeSpan(1.2f), 0, Integer.toString(hole_hour).length() + Integer.toString(hole_min).length() + 4, 0);
////            s.setSpan(new ForegroundColorSpan(color_MINT), 0, Integer.toString(hole_hour).length() + Integer.toString(hole_min).length() + 4, 0);
//
//        }else{
//            s = new SpannableString(Integer.toString(hole_day_sleep) + "분");
////            s.setSpan(new RelativeSizeSpan(2.0f), 0, Integer.toString(hole_day_sleep).length() + 1, 0);
////            s.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, Integer.toString(hole_day_sleep).length() + 1, 0);
////            s.setSpan(new ForegroundColorSpan(color_MINT), 0, Integer.toString(hole_day_sleep).length() + 1, 0);
//        }
//
//        return s;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//}