package com.example.noosrat.budgettracker.Singleton;


import com.example.noosrat.budgettracker.Transaction;

import java.util.ArrayList;


public class SpentSingleton {

    public static ArrayList<Transaction> transactionList = new ArrayList<>();
    public static float balance;
}
