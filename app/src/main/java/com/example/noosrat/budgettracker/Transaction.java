package com.example.noosrat.budgettracker;

public class Transaction {

    public static String[] TRANSACTION_TYPES = {"EFT", "DEBIT ORDER", "PAYMENT", "DEPOSIT", "INFO", "PURCHASE"};
    public static String[] CARD_TYPES = {"DEBIT", "CREDIT", "NOCARD"};
    public static String[] CARD_CODES = {"CHEQ5962", "CCRD0019"};

    boolean TRANSACTION_TYPE_CASH = false;
    boolean TRANSACTION_TYPE_EFT = false;
    boolean TRANSACTION_TYPE_DEBIT_ORDER = false;
    boolean TRANSACTION_TYPE_PAYMENT = false;
    boolean TRANSACTION_TYPE_DEPOSIT = false;
    boolean TRANSACTION_TYPE_INFO = false;
    boolean TRANSACTION_TYPE_PURCHASE = false;

    boolean CARD_TYPE_DEBIT = false;
    boolean CARD_TYPE_CREDIT = false;
    boolean CARD_TYPE_NOCARD = false;

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

                TRANSACTION_TYPE_CASH = transaction.isTRANSACTION_TYPE_CASH();
                TRANSACTION_TYPE_EFT = transaction.isTRANSACTION_TYPE_EFT();
                TRANSACTION_TYPE_DEBIT_ORDER = transaction.isTRANSACTION_TYPE_DEBIT_ORDER();
                TRANSACTION_TYPE_PAYMENT = transaction.isTRANSACTION_TYPE_PAYMENT();
                TRANSACTION_TYPE_DEPOSIT = transaction.isTRANSACTION_TYPE_DEPOSIT();
                TRANSACTION_TYPE_INFO = transaction.isTRANSACTION_TYPE_INFO();
                TRANSACTION_TYPE_PURCHASE = transaction.isTRANSACTION_TYPE_PURCHASE();
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

    public String getTransactionType() {

        if (TRANSACTION_TYPE_CASH)
            return "CASH";
        if (TRANSACTION_TYPE_EFT)
            return "EFT";
        if (TRANSACTION_TYPE_DEBIT_ORDER)
            return "DEBIT ORDER";
        if (TRANSACTION_TYPE_PAYMENT)
            return "PAYMENT";
        if (TRANSACTION_TYPE_DEPOSIT)
            return "DEPOSIT";
        if (TRANSACTION_TYPE_INFO)
            return "INFO";
        if (TRANSACTION_TYPE_PURCHASE)
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

        if (CARD_TYPE_DEBIT)
            return "DEBIT";
        if (CARD_TYPE_CREDIT)
            return "CREDIT";
        if (CARD_TYPE_NOCARD)
            return "NO";

        return "";
    }

    public String getBank() {
        return bank;
    }

    public boolean isTRANSACTION_TYPE_CASH() {
        return TRANSACTION_TYPE_CASH;
    }

    public boolean isTRANSACTION_TYPE_EFT() {
        return TRANSACTION_TYPE_EFT;
    }

    public boolean isTRANSACTION_TYPE_DEBIT_ORDER() {
        return TRANSACTION_TYPE_DEBIT_ORDER;
    }

    public boolean isTRANSACTION_TYPE_PAYMENT() {
        return TRANSACTION_TYPE_PAYMENT;
    }

    public boolean isTRANSACTION_TYPE_DEPOSIT() {
        return TRANSACTION_TYPE_DEPOSIT;
    }

    public boolean isTRANSACTION_TYPE_INFO() {
        return TRANSACTION_TYPE_INFO;
    }

    public boolean isTRANSACTION_TYPE_PURCHASE() {
        return TRANSACTION_TYPE_PURCHASE;
    }
}
