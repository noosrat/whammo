package com.example.noosrat.budgettracker.Singleton;


import com.example.noosrat.budgettracker.Transaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class SpentSingleton {

    public static ArrayList<Transaction> transactionList = new ArrayList<>();
    public static float balance;
    public static Locale myLocale = new Locale("en", "ZA");
    public static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(myLocale);
}
