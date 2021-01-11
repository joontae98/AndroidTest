package com.example.mpcharttest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnBarChart, btnLineChart, btnPieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBarChart = (Button) findViewById(R.id.btn_main_barChart);
        btnLineChart = (Button) findViewById(R.id.btn_main_lineChart);
        btnPieChart = (Button) findViewById(R.id.btn_main_pieChart);

        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BarChartActivity.class);
                startActivity(intent);
            }
        });

        btnLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LineChartActivity.class);
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
    }
}