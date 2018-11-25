package com.example.noosrat.budgettracker.Utilities;

import com.robinhood.spark.SparkAdapter;

import java.util.ArrayList;

public class CustomSparkAdapter extends SparkAdapter {
    private ArrayList<Float> yData;

    public CustomSparkAdapter(ArrayList<Float> yData) {
      this.yData = yData;
    }

    @Override
    public int getCount() {
      return yData.size();
    }

    @Override
    public Object getItem(int index) {
      return yData.get(index);
    }

    @Override
    public float getY(int index) {
      return yData.get(index);
    }
}