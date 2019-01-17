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
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private ArrayList<Float> sumDataList = new ArrayList<>();
    private float[] yData;
    private Random random;
    private float BUDGET = 20000;
    //private SparkView sparkView;
    ProgressBar budgetPercentage;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        final FragmentActivity c = getActivity();

        budgetPercentage = view.findViewById(R.id.progressBar);
        TextView txtBalance = view.findViewById(R.id.balance);
        TextView txtExpense = view.findViewById(R.id.total_expense);
        TextView txtExpensePercentage = view.findViewById(R.id.percentage);
        TextView txtDaysLeft = view.findViewById(R.id.days_left);
        TextView txtPeriod = view.findViewById(R.id.time_period);
        float expense = 0;

        expense = calculateExpenses(SpentSingleton.transactionList);
        float balance = BUDGET - expense;

        txtExpense.setText(SpentSingleton.currencyFormat.format(expense));
        txtBalance.setText(SpentSingleton.currencyFormat.format(balance));

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

        String shortMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,SpentSingleton.myLocale);
        String duration = shortMonth +" 1 to " + shortMonth +" " +cal.getActualMaximum(Calendar.DATE);

        txtPeriod.setText(duration);

        RecyclerView rv = view.findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(c);
        rv.setLayoutManager(llm);
        CategoryAdapter adapter = new CategoryAdapter(SpentSingleton.categorySummaryMap, c);

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
