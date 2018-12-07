package com.example.noosrat.budgettracker;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.noosrat.budgettracker.Utilities.CustomSparkAdapter;
import com.example.noosrat.budgettracker.Utilities.MerchantHelper;
import com.example.noosrat.budgettracker.Utilities.TimeAgo;
import com.robinhood.spark.SparkView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class FeedActivity extends AppCompatActivity {

    private ArrayList<Float> sumDataList = new ArrayList<>();
    private float[] yData;
    private Random random;
    private float BUDGET = 20000;
    private SparkView sparkView;
    ProgressBar budgetPercentage;

    Locale myLocale = new Locale("en", "ZA");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(myLocale);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        setContentView(R.layout.activity_feed);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.balance_action_bar);

        sparkView = (SparkView) findViewById(R.id.sparkview);
        budgetPercentage = findViewById(R.id.progressBar);
        TextView txtBalance = findViewById(R.id.balance);
        TextView txtExpense = findViewById(R.id.total_expense);
        TextView txtExpensePercentage = findViewById(R.id.percentage);
        TextView txtDaysLeft = findViewById(R.id.days_left);
        TextView txtPeriod = findViewById(R.id.time_period);
        float expense = 0;

        ArrayList<Transaction> transactionList = new ArrayList<>();
        ArrayList<FeedItem> feedItemsList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            ContentResolver cr = getContentResolver();

            ArrayList<SMS> smsLst = SpentUtilities.getSMSes(new Date(), cr, Telephony.Sms.Inbox.CONTENT_URI);

            if (smsLst != null) {

                MerchantHelper mh = new MerchantHelper();

                String today = "";

                for (int k = 0; k < smsLst.size(); k++) {
                    if (SpentUtilities.isBundledSms(smsLst.get(k).getMessage())) {
                        SMS[] bundledSMSes = SpentUtilities.smsCleaner(smsLst.get(k));

                        for (int m = 0; m < bundledSMSes.length; m++) {
                            Transaction transaction = new Transaction(bundledSMSes[m]);
                            if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO) {
                                transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                if (TimeAgo.getTimeAgo(transaction.getDate()).equals(today)) {
                                    transactionList.add(transaction);
                                    feedItemsList.add(new FeedItem(transaction));
                                }
                                else{
                                    feedItemsList.add(new FeedItem(TimeAgo.getTimeAgo(transaction.getDate())));
                                    transactionList.add(transaction);
                                    feedItemsList.add(new FeedItem(transaction));
                                    today = TimeAgo.getTimeAgo(transaction.getDate());
                                }

                            }

                        }
                    } else {
                        Transaction transaction = new Transaction(smsLst.get(k));
                        if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO) {
                            transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                            if (TimeAgo.getTimeAgo(transaction.getDate()).equals(today)) {
                                transactionList.add(transaction);
                                feedItemsList.add(new FeedItem(transaction));

                            }
                            else{
                                feedItemsList.add(new FeedItem(TimeAgo.getTimeAgo(transaction.getDate())));
                                transactionList.add(transaction);
                                feedItemsList.add(new FeedItem(transaction));
                                today = TimeAgo.getTimeAgo(transaction.getDate());
                            }

                        }
                    }
                }
            }


            RecyclerView rv = findViewById(R.id.recyclerView);

            LinearLayoutManager llm = new LinearLayoutManager(FeedActivity.this);
            rv.setLayoutManager(llm);
            TransactionAdapter adapter = new TransactionAdapter(feedItemsList, FeedActivity.this);

            rv.setAdapter(adapter);


        }

        expense = calculateExpenses(transactionList);
        float balance = BUDGET - expense;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        txtExpense.setText(currencyFormat.format(expense));
        txtBalance.setText(currencyFormat.format(balance));

        budgetPercentage.setMax((int) BUDGET);
        budgetPercentage.setProgress((int) expense);

        int expensePercentage = Math.round(expense/BUDGET*100);
        txtExpensePercentage.setText(expensePercentage+"%");

        Calendar cal = Calendar.getInstance();

        Date today = new Date();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

        Date lastDayOfMonth = cal.getTime();


        long diffInMillies = Math.abs(lastDayOfMonth.getTime() - today.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        txtDaysLeft.setText(diff+"");

        String shortMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,myLocale);
        String duration = shortMonth +" 1 to " + shortMonth +" " +cal.getActualMaximum(Calendar.DATE);

        txtPeriod.setText(duration);

    }

    public float calculateExpenses(ArrayList<Transaction> transactionList) {

        float sum = 0;

        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i)!=null){
                sum = sum + transactionList.get(i).getNumberAmount();
                sumDataList.add(15000 - sum);
            }

        }
        sparkView.setAdapter(new CustomSparkAdapter(sumDataList));
        sparkView.setLineColor(Color.WHITE);
        sparkView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        sparkView.setLineWidth(3);

        return sum;
    }
}
