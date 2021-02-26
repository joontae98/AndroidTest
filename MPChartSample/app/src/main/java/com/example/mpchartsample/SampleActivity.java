package com.example.mpchartsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpchartsample.BoomcareSample.Bottle_total;
import com.example.mpchartsample.BoomcareSample.Nappy_total;
import com.example.mpchartsample.BoomcareSample.Physical_check;
import com.example.mpchartsample.BoomcareSample.Thermo_day;
import com.example.mpchartsample.BoomcareSample.Thermo_total;
import com.example.mpchartsample.example.LineChartActivity;
import com.example.mpchartsample.example.PieChartActivity;
import com.example.mpchartsample.example.RadarChartActivity;

public class SampleActivity extends AppCompatActivity {
    Button btnBottleChart, btnTempChart, btnTempTotalChart, btnSleepChart, btnNappyChart, btnPhysicalChart, btnCompareChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        btnBottleChart = (Button) findViewById(R.id.btn_sample_bottleChart);
        btnTempChart = (Button) findViewById(R.id.btn_sample_tempChart);
        btnTempTotalChart = (Button) findViewById(R.id.btn_sample_tempTotalChart);
        btnSleepChart = (Button) findViewById(R.id.btn_sample_sleepChart);
        btnNappyChart = (Button) findViewById(R.id.btn_sample_nappyChart);
        btnPhysicalChart = (Button) findViewById(R.id.btn_sample_physicalChart);
        btnCompareChart = (Button) findViewById(R.id.btn_sample_compareChart);

        btnBottleChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, Bottle_total.class);
//                Intent intent = new Intent(SampleActivity.this, BottleChart.class);
                startActivity(intent);
            }
        });

        btnTempChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, Thermo_day.class);
                startActivity(intent);
            }
        });

        btnTempTotalChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, Thermo_total.class);
                startActivity(intent);
            }
        });


        btnSleepChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
        btnNappyChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, Nappy_total.class);
//                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
        btnPhysicalChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, Physical_check.class);
                startActivity(intent);
            }
        });

        btnCompareChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, RadarChartActivity.class);
                startActivity(intent);
            }
        });
    }
}