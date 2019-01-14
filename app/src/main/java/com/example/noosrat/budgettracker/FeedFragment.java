package com.example.noosrat.budgettracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.noosrat.budgettracker.Singleton.SpentSingleton;

import java.util.ArrayList;
import java.util.Random;


public class FeedFragment extends Fragment {

    private ArrayList<Float> sumDataList = new ArrayList<>();
    private float[] yData;
    private Random random;
    private float BUDGET = 20000;
    //private SparkView sparkView;
    ProgressBar budgetPercentage;


    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }


    //@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //Remove title bar
//        setContentView(R.layout.fragment_feed);

        final FragmentActivity c = getActivity();
//
//        getSupportActionBar().setElevation(0);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.balance_action_bar);

        //sparkView = (SparkView) findViewById(R.id.sparkview);
        TextView txtBalance = view.findViewById(R.id.balance);
        TextView txtExpense = view.findViewById(R.id.total_expense);

        float expense = 0;
        expense = calculateExpenses(SpentSingleton.transactionList);
        float balance = BUDGET - expense;

        txtExpense.setText(SpentSingleton.currencyFormat.format(expense));
        txtBalance.setText(SpentSingleton.currencyFormat.format(balance));

        RecyclerView rv = view.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(c);
        rv.setLayoutManager(llm);
        TransactionAdapter adapter = new TransactionAdapter(SpentSingleton.feedItemsList, c);

        rv.setAdapter(adapter);
    }

    public float calculateExpenses(ArrayList<Transaction> transactionList) {

        float sum = 0;

        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i)!=null){
                sum = sum + transactionList.get(i).getNumberAmount();
                sumDataList.add(15000 - sum);
            }

        }
//        sparkView.setAdapter(new CustomSparkAdapter(sumDataList));
//        sparkView.setLineColor(Color.WHITE);
//        sparkView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        sparkView.setLineWidth(3);

        return sum;
    }
}
