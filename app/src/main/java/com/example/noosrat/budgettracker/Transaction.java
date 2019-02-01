package com.example.noosrat.budgettracker;

import com.example.noosrat.budgettracker.POJO.Merchant.Merchant;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Transaction {

    public static final String[] TRANSACTION_TYPES = {"EFT", "DEBIT ORDER", "PAYMENT", "DEPOSIT", "INFO", "PURCHASE"};
    public static final String[] CARD_TYPES = {"DEBIT", "CREDIT", "NOCARD"};
    public static final String[] CARD_CODES = {"CHEQ5962", "CCRD0019"};

    static final int TRANSACTION_TYPE_CASH = 101;
    static final int TRANSACTION_TYPE_EFT = 102;
    static final int TRANSACTION_TYPE_DEBIT_ORDER = 103;
    static final int TRANSACTION_TYPE_PAYMENT = 104;
    static final int TRANSACTION_TYPE_DEPOSIT = 105;
    static final int TRANSACTION_TYPE_INFO = 106;
    static final int TRANSACTION_TYPE_PURCHASE = 107;
    static final int TRANSACTION_TYPE_TRANSFER = 108;
    static final int TRANSACTION_TYPE_APP_PURCHASE = 109;
    static final int TRANSACTION_TYPE_UNKNOWN = 110;

    static final int CARD_TYPE_DEBIT = 201;
    static final int CARD_TYPE_CREDIT = 202;
    static final int CARD_TYPE_NOCARD = 203;

    String id;
    int transactionType;
    String amount;
    String recipient;
    String message;
    int card;
    String bank;
    Date date;
    private Merchant merchant;

    public Transaction() {
    }

    public Transaction(SMS sms) {

        this.message = sms.getMessage();
        this.date = sms.getDate();
        int pos = message.indexOf(":");
        if (pos>-1)
            this.bank = message.substring(0, pos);

        if (this.bank != null){
            if (this.bank.toUpperCase().equals("ABSA")){
                ABSA transaction = new ABSA(message);

                transactionType = transaction.getTransactionType();
                amount = transaction.getAmount();
                recipient = transaction.getReciepient();
                card = transaction.getCard();
            }
            else if (this.bank.toUpperCase().equals("FNB")){
                FNB transaction = new FNB(message);

                transactionType = transaction.getTransactionType();
                amount = transaction.getAmount();
                recipient = transaction.getReciepient();
                card = transaction.getCard();
            }
            else if (this.bank.toUpperCase().equals("STANDARD BANK")){
                STANDARD transaction = new STANDARD(message);

                transactionType = transaction.getTransactionType();
                amount = transaction.getAmount();
                recipient = transaction.getReciepient();
                card = transaction.getCard();
            }

        }
        else{
            FNB transaction = new FNB(message);

            transactionType = transaction.getTransactionType();
            amount = transaction.getAmount();
            recipient = transaction.getReciepient();
            card = transaction.getCard();
        }




    }

    @Exclude
    public float getNumberAmount() {
        return Math.abs(Float.parseFloat (amount.replace(",","").substring(1)));
    }

    @Exclude
    public String getTransactionTypeDisplay() {

        if (transactionType == TRANSACTION_TYPE_CASH)
            return "CASH";
        if (transactionType == TRANSACTION_TYPE_EFT)
            return "EFT";
        if (transactionType == TRANSACTION_TYPE_DEBIT_ORDER)
            return "DEBIT ORDER";
        if (transactionType == TRANSACTION_TYPE_PAYMENT)
            return "PAYMENT";
        if (transactionType == TRANSACTION_TYPE_DEPOSIT)
            return "DEPOSIT";
        if (transactionType == TRANSACTION_TYPE_INFO)
            return "INFO";
        if (transactionType == TRANSACTION_TYPE_PURCHASE)
            return "PURCHASE";
        if (transactionType == TRANSACTION_TYPE_APP_PURCHASE)
            return "APP PURCHASE";


        return "";
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    @Exclude
    public String getCardDisplay() {

        if (card == CARD_TYPE_DEBIT)
            return "DEBIT";
        if (card == CARD_TYPE_CREDIT)
            return "CREDIT";
        if (card == CARD_TYPE_NOCARD)
            return "NO";

        return "";
    }

    public String getBank() {
        return bank;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public int getCard() {
        return card;
    }

    public Date getDate() {
        return date;
    }
}
