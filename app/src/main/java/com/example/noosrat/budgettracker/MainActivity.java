package com.example.noosrat.budgettracker;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.noosrat.budgettracker.POJO.Category;
import com.example.noosrat.budgettracker.POJO.Merchant.Merchant;
import com.example.noosrat.budgettracker.Singleton.SpentSingleton;
import com.example.noosrat.budgettracker.Utilities.MerchantHelper;
import com.example.noosrat.budgettracker.Utilities.TimeAgo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    DatabaseReference mErrorTable;
    DatabaseReference mMerchants;
    String userId;
    private FirebaseAuth mAuth;

    private ArrayList<Transaction> transactionList;
    private ArrayList<FeedItem> feedItemsList;
    private HashMap<String, Float> categorySummaryMap;
    private ArrayList<Merchant> merchantList;


    //Button btncontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        //btncontinue = findViewById(R.id.btnContinue);
        //btncontinue.setBackgroundColor(Color.GRAY);
        //btncontinue.setClickable(false);

        transactionList = new ArrayList<>();
        feedItemsList = new ArrayList<>();
        categorySummaryMap = new HashMap<>();
        merchantList = new ArrayList<>();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();


        SpentSingleton.categoryMap.put("Eating Out", new Category("Eating Out",R.drawable.ic_category_eating, "#93B93F", 10000));
        SpentSingleton.categoryMap.put("Entertainment", new Category("Entertainment",R.drawable.ic_category_entertainment, "#93B93F", 5000));
        SpentSingleton.categoryMap.put("Groceries", new Category("Groceries",R.drawable.ic_category_groceries, "#26B1C8", 20000));
        SpentSingleton.categoryMap.put("Insurance", new Category("Insurance",R.drawable.ic_category_insurance, "#ED6A5A", 40000));
        SpentSingleton.categoryMap.put("Rent", new Category("Rent",R.drawable.ic_category_rent, "#C54A86", 20000));
        SpentSingleton.categoryMap.put("Petrol",new Category("Petrol",R.drawable.ic_category_petrol, "#393D3F", 3000));
        SpentSingleton.categoryMap.put("Travel", new Category("Travel",R.drawable.ic_category_travel, "#FFC548", 10000));
        SpentSingleton.categoryMap.put("Clothing", new Category("Clothing",R.drawable.ic_category_clothing, "#FFC548", 3000));
        SpentSingleton.categoryMap.put("Car", new Category("Car",R.drawable.ic_category_car, "#26B1C8", 5000));
        SpentSingleton.categoryMap.put("Lifestyle", new Category("Lifestyle",R.drawable.ic_category_lifestyle, "#C54A86", 2000));
        SpentSingleton.categoryMap.put("Phone", new Category("Phone",R.drawable.ic_category_phone, "#ED6A5A", 1000));
        SpentSingleton.categoryMap.put("Other", new Category("Other",R.drawable.ic_category_other, "#393D3F", 1));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signInAnonymously() {
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("user sign in", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("user sign in", "signInAnonymously:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        // [END signin_anonymously]
    }

    private void updateUI(FirebaseUser user) {
        userId = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("users/"+userId);
        Log.w("user sign in", "transactionList: "+transactionList.size());
        Log.w("user sign in", "feedItemsList:"+feedItemsList.size());

        mErrorTable = FirebaseDatabase.getInstance().getReference("errors/");

        mMerchants = FirebaseDatabase.getInstance().getReference("merchants/");

        mMerchants.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Merchant merchant = childSnapshot.getValue(Merchant.class);

                    Log.i("merchantsss", childSnapshot.getKey());
                    merchantList.add(merchant);
                }

                SpentSingleton.merchantList = merchantList;

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Transaction transaction = childSnapshot.getValue(Transaction.class);
                            transactionList.add(transaction);
                        }
                        Log.w("user sign in", "completed fetching data");
                        Log.w("user sign in", "transactionList: "+transactionList.size());
                        Log.w("user sign in", "feedItemsList:"+feedItemsList.size());
                        //btncontinue.setBackgroundColor(Color.GREEN);
                        //btncontinue.setClickable(true);

                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_SMS},
                                1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

    public void onContinueClicked(View view) {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_SMS},
                1);


    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

                        ContentResolver cr = getContentResolver();

                        ArrayList<SMS> smsLst;
                        if (transactionList.size() > 0) {
                            Date lastUpdate = transactionList.get(transactionList.size()-1).getDate();
                            smsLst = SpentUtilities.getSMSes(lastUpdate, cr, Telephony.Sms.Inbox.CONTENT_URI);
                        } else {
                            smsLst = SpentUtilities.getSMSes(cr, Telephony.Sms.Inbox.CONTENT_URI);
                        }

                        Log.w("user sign in", "smsLst: "+smsLst.size());

                        if (smsLst.size() > 0) {

                            MerchantHelper mh = new MerchantHelper();

                            for (int k = smsLst.size()-1; k >= 0; k--) {
                                Log.i("orig sms", smsLst.get(k).getMessage());
                                if (SpentUtilities.isBundledSms(smsLst.get(k).getMessage())) {
                                    SMS[] bundledSMSes = SpentUtilities.smsCleaner(smsLst.get(k));

                                    for (int m = 0; m < bundledSMSes.length; m++) {

                                        try {
                                            Transaction transaction = new Transaction(bundledSMSes[m]);

                                            if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO  && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_UNKNOWN) {
                                                transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                                String transactionId = mDatabase.push().getKey();
                                                transaction.setId(transactionId);
                                                transactionList.add(transaction);
                                                mDatabase.child(transactionId).setValue(transaction);
                                            }
                                        } catch (Exception e) {
                                            String errorId = mDatabase.push().getKey();

                                            StringWriter sw = new StringWriter();
                                            e.printStackTrace(new PrintWriter(sw));
                                            String exceptionAsString = sw.toString();

                                            bundledSMSes[m].setProblem(exceptionAsString);
                                            mErrorTable.child(errorId).setValue(bundledSMSes[m]);
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    try {
                                        Transaction transaction = new Transaction(smsLst.get(k));
                                        if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_UNKNOWN) {
                                            transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                            String transactionId = mDatabase.push().getKey();
                                            transaction.setId(transactionId);
                                            transactionList.add(transaction);
                                            mDatabase.child(transactionId).setValue(transaction);
                                        }
                                    } catch (Exception e) {
                                        String errorId = mDatabase.push().getKey();

                                        StringWriter sw = new StringWriter();
                                        e.printStackTrace(new PrintWriter(sw));
                                        String exceptionAsString = sw.toString();

                                        smsLst.get(k).setProblem(exceptionAsString);
                                        mErrorTable.child(errorId).setValue(smsLst.get(k));
                                        e.printStackTrace();                                    }
                                }
                            }
                        }

                        String today = "";

                        Collections.reverse(transactionList);
                        Date budgetStartDate = SpentUtilities.getMonthStart();

                        for (Transaction transaction: transactionList) {
                            if (transaction.getDate().after(budgetStartDate)) {
                                if (TimeAgo.getTimeAgo(transaction.getDate()).equals(today)) {
                                    feedItemsList.add(new FeedItem(transaction));
                                }
                                else{
                                    feedItemsList.add(new FeedItem(TimeAgo.getTimeAgo(transaction.getDate())));
                                    feedItemsList.add(new FeedItem(transaction));

                                    today = TimeAgo.getTimeAgo(transaction.getDate());
                                }

                                if (categorySummaryMap.get(transaction.getMerchant().getCategory().getName()) == null) {
                                    categorySummaryMap.put(transaction.getMerchant().getCategory().getName(), transaction.getNumberAmount());
                                } else{
                                    categorySummaryMap.put(transaction.getMerchant().getCategory().getName(), categorySummaryMap.get(transaction.getMerchant().getCategory().getName())+transaction.getNumberAmount());
                                }
                            }
                        }

                        SpentSingleton.transactionList = transactionList;
                        SpentSingleton.feedItemsList = feedItemsList;
                        SpentSingleton.categorySummaryMap = categorySummaryMap;
                    }


                    Intent i = new Intent(MainActivity.this, PagerActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(i);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your SMSs", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
