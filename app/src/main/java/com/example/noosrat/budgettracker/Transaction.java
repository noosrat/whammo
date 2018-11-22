package com.example.noosrat.budgettracker;

public class Transaction {

    public static String[] TRANSACTION_TYPES = {"EFT", "DEBIT ORDER", "PAYMENT", "DEPOSIT", "INFO", "PURCHASE"};
    public static String[] CARD_TYPES = {"DEBIT", "CREDIT", "NOCARD"};
    public static String[] CARD_CODES = {"CHEQ5962", "CCRD0019"};


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

    public int getTransactionType() {
        return transactionType;
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

    public int getCard() {
        return card;
    }

    public String getBank() {
        return bank;
    }
}
