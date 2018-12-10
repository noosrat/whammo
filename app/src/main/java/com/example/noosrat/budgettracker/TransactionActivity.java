package com.example.noosrat.budgettracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class TransactionActivity extends AppCompatActivity {

    private TextView tvDescription;
    private TextView tvAmount;
    private TextView tvDate;
    private ImageView imgMerchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_transaction);

        String imageURL = (String) getIntent().getExtras().get("imageURL");
        String amount = (String) getIntent().getExtras().get("amount");
        String date = (String) getIntent().getExtras().get("date");
        String recipient = (String) getIntent().getExtras().get("recipient");

        tvDescription = (TextView) findViewById(R.id.recipient);
        tvAmount = (TextView) findViewById(R.id.amount);
        tvDate = (TextView) findViewById(R.id.date);
        imgMerchant = (ImageView) findViewById(R.id.imageView);

        Glide.with(this)
                .load(imageURL)
                .apply(RequestOptions.circleCropTransform())
                .into(imgMerchant);

        tvDescription.setText(recipient);
        tvAmount.setText(amount);
        tvDate.setText(date);
    }
}
