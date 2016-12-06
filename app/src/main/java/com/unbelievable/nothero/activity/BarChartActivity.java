package com.unbelievable.nothero.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unbelievable.nothero.R;
import com.unbelievable.nothero.View.BarChart;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        test();
    }

    private void test(){
        List<BarChart.History> list = new ArrayList<>();
        list.add(new BarChart.History("1480954524000",1000));
        list.add(new BarChart.History("1480958124000",10));
        list.add(new BarChart.History("1480961724000",200));
        list.add(new BarChart.History("1480965224000",300));
        list.add(new BarChart.History("1480968824000",2000));

        list.add(new BarChart.History("1480972324000",1000));
        list.add(new BarChart.History("1480975924000",10));
        list.add(new BarChart.History("1480979553000",200));
        list.add(new BarChart.History("1480983153000",300));
        list.add(new BarChart.History("1480986753000",2000));

        list.add(new BarChart.History("1480990324000",1000));
        list.add(new BarChart.History("1480993924000",10));
        list.add(new BarChart.History("1480997553000",200));
        list.add(new BarChart.History("1480101153000",300));
        list.add(new BarChart.History("1480104753000",2000));

        list.add(new BarChart.History("1480108353000",1000));
        list.add(new BarChart.History("1480111953000",10));
        list.add(new BarChart.History("1480115553000",200));
        list.add(new BarChart.History("1480119153000",300));
        list.add(new BarChart.History("1480122753000",2000));

        list.add(new BarChart.History("1480126353000",1000));
        list.add(new BarChart.History("1480129953000",10));
        list.add(new BarChart.History("1480133553000",200));
        list.add(new BarChart.History("1480137153000",300));
        BarChart barChart = (BarChart) findViewById(R.id.v_bar_chart);
        barChart.setData(list);
    }
}
