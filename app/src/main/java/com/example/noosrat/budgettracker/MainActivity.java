package com.example.noosrat.budgettracker;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    String userId;
    private FirebaseAuth mAuth;

    private ArrayList<Transaction> transactionList;
    private ArrayList<FeedItem> feedItemsList;
    private HashMap<String, Float> categorySummaryMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        transactionList = new ArrayList<>();
        feedItemsList = new ArrayList<>();
        categorySummaryMap = new HashMap<>();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();


        SpentSingleton.categoryMap.put("Eating Out", new Category("Eating Out","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Eating%20Out.png?alt=media&token=b5e8bdc3-b932-42ac-9987-d4806404c30f", "#ECEC45", 10000));
        SpentSingleton.categoryMap.put("Entertainment", new Category("Entertainment","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Entertainment.png?alt=media&token=0103d4cc-7155-44e0-ba59-e753928f8d1d", "#FF9B00", 5000));
        SpentSingleton.categoryMap.put("Groceries", new Category("Groceries","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Groceries.png?alt=media&token=f050d6c7-48b5-4d42-8a5a-065ae22f2385", "#23BC57", 20000));
        SpentSingleton.categoryMap.put("Insurance", new Category("Insurance","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Insurance.png?alt=media&token=0cb39a7f-a221-41ce-8c30-fe0412c1d05b", "#A260C1", 40000));
        SpentSingleton.categoryMap.put("Rent", new Category("Rent","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Rent.png?alt=media&token=fd2e626f-c284-4a61-bdb9-f4c883c7ecaa", "#F57BAE", 20000));
        SpentSingleton.categoryMap.put("Petrol",new Category("Petrol","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Petrol.png?alt=media&token=2779a4fb-ca4b-4cd4-8636-e584d1dfafaf", "#8FD239", 3000));
        SpentSingleton.categoryMap.put("Travel", new Category("Travel","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Travel.png?alt=media&token=ac040554-3bb7-40e8-9d3f-83e807a617ee", "#D23A3A", 10000));
        SpentSingleton.categoryMap.put("Clothing", new Category("Clothing","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Clothing.png?alt=media&token=0b36c42b-bc94-4a1e-b8ec-38a90cc8a399", "#D23979", 3000));
        SpentSingleton.categoryMap.put("Car", new Category("Car","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Car.png?alt=media&token=cff056a5-c523-4b34-b6b0-520f5aba7658", "#3952D2", 5000));
        SpentSingleton.categoryMap.put("Lifestyle", new Category("Lifestyle","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Lifestyle.png?alt=media&token=b6a08f88-b980-4ed4-b745-2c0955f6837d", "#00E2CC", 2000));
        SpentSingleton.categoryMap.put("Phone", new Category("Phone","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Phone.png?alt=media&token=e9805a87-bdc0-49cf-9903-fddb23f4d14c", "#808080", 1000));
        SpentSingleton.categoryMap.put("Other", new Category("Other","https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/Other.png?alt=media&token=6850125d-909a-4c65-a701-fa514a9a057d", "#BCBCBC", 1));
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

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Transaction transaction = childSnapshot.getValue(Transaction.class);
                    transactionList.add(transaction);
                }
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

                        ArrayList<SMS> smsLst = SpentUtilities.getSMSes(new Date(), cr, Telephony.Sms.Inbox.CONTENT_URI);

                        if (smsLst != null) {

                            MerchantHelper mh = new MerchantHelper();

                            for (int k = 0; k < smsLst.size(); k++) {
                                Log.i("orig sms", smsLst.get(k).getMessage());
                                if (SpentUtilities.isBundledSms(smsLst.get(k).getMessage())) {
                                    SMS[] bundledSMSes = SpentUtilities.smsCleaner(smsLst.get(k));

                                    for (int m = 0; m < bundledSMSes.length; m++) {
                                        Transaction transaction = new Transaction(bundledSMSes[m]);
                                        if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO  && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_UNKNOWN) {
                                            transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                            String transactionId = mDatabase.child(userId).push().getKey();
                                            transaction.setId(transactionId);
                                            transactionList.add(transaction);
                                            mDatabase.child(userId).child(transactionId).setValue(transaction);
                                        }
                                    }
                                } else {
                                    Transaction transaction = new Transaction(smsLst.get(k));
                                    if (transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_TRANSFER && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_DEPOSIT && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_INFO && transaction.getTransactionType() != Transaction.TRANSACTION_TYPE_UNKNOWN) {
                                        transaction.setMerchant(mh.getMerchant(transaction.getRecipient()));
                                        String transactionId = mDatabase.child(userId).push().getKey();
                                        transaction.setId(transactionId);
                                        transactionList.add(transaction);
                                        mDatabase.child(userId).child(transactionId).setValue(transaction);
                                    }
                                }
                            }
                        }

                        String today = "";

                        for (Transaction transaction: transactionList) {
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

                        SpentSingleton.transactionList = transactionList;
                        SpentSingleton.feedItemsList = feedItemsList;
                        SpentSingleton.categorySummaryMap = categorySummaryMap;
                    }


                    Intent i = new Intent(MainActivity.this, PagerActivity.class);

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
