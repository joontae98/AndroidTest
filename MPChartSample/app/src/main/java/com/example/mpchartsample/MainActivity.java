package com.example.mpchartsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpchartsample.example.BarChartActivity;
import com.example.mpchartsample.example.LineChartActivity;
import com.example.mpchartsample.example.PieChartActivity;
import com.example.mpchartsample.example.RadarChartActivity;

public class MainActivity extends AppCompatActivity {
    Button btnBarChart, btnLineChart, btnPieChart, btnRadarChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBarChart = (Button) findViewById(R.id.btn_main_barChart);
        btnLineChart = (Button) findViewById(R.id.btn_main_lineChart);
        btnPieChart = (Button) findViewById(R.id.btn_main_pieChart);
        btnRadarChart = (Button) findViewById(R.id.btn_main_radarChart);

        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BarChartActivity.class);
                startActivity(intent);
            }
        });

        btnLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LineChartActivity.class);
                startActivity(intent);
            }
        });

        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PieChartActivity.class);
                startActivity(intent);
            }
        });
        btnRadarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RadarChartActivity.class);
//                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }
}