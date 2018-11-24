package com.example.noosrat.budgettracker;

public class Transaction {

    public static String[] TRANSACTION_TYPES = {"EFT", "DEBIT ORDER", "PAYMENT", "DEPOSIT", "INFO", "PURCHASE"};
    public static String[] CARD_TYPES = {"DEBIT", "CREDIT", "NOCARD"};
    public static String[] CARD_CODES = {"CHEQ5962", "CCRD0019"};

    static final int TRANSACTION_TYPE_CASH = 101;
    static final int TRANSACTION_TYPE_EFT = 102;
    static final int TRANSACTION_TYPE_DEBIT_ORDER = 103;
    static final int TRANSACTION_TYPE_PAYMENT = 104;
    static final int TRANSACTION_TYPE_DEPOSIT = 105;
    static final int TRANSACTION_TYPE_INFO = 106;
    static final int TRANSACTION_TYPE_PURCHASE = 107;

    static final int CARD_TYPE_DEBIT = 201;
    static final int CARD_TYPE_CREDIT = 202;
    static final int CARD_TYPE_NOCARD = 203;

    int transactionType;
    String amount;
    String recipient;
    String message;
    int card;
    String bank;

    public Transaction() {
    }

    public Transaction(String message) {

        this.message = message;
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
            else{
                FNB transaction = new FNB(message);

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

    public float getNumberAmount() {
        return Math.abs(Float.parseFloat (amount.replace(",","").substring(1)));
    }

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


        return "";
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

    public String getCard() {

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
}
