package com.example.noosrat.budgettracker.Singleton;


import com.example.noosrat.budgettracker.FeedItem;
import com.example.noosrat.budgettracker.POJO.Category;
import com.example.noosrat.budgettracker.Transaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class SpentSingleton {

    public static ArrayList<Transaction> transactionList = new ArrayList<>();
    public static ArrayList<FeedItem> feedItemsList = new ArrayList<>();
    public static HashMap<String, Float> categorySummaryMap = new HashMap<>();
    public static HashMap<String, Category> categoryMap = new HashMap<>();
    public static float balance;
    public static Locale myLocale = new Locale("en", "ZA");
    public static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(myLocale);
}
