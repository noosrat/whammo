package com.example.noosrat.budgettracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
    }

    public void onContinueClicked(View view) {
        Intent i = new Intent(MainActivity.this, FeedActivity.class);

        startActivity(i);
    }
}