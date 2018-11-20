package com.example.noosrat.budgettracker;

public class SMS {

    String[] TRANSACTION_TYPES = {"EFT", "PAYMENT", "DEPOSIT", "INFO"};
    String[] CARD_TYPES = {"DEBIT", "CREDIT"};

    int transactionType;
    Float amount;
    String location;
    String originalMessage;
    int card;


    public SMS(String message) {
        this.originalMessage = message;
        this.transactionType = retrieveTransactionType();
        this.amount = retrieveAmount();
        this.location = retrieveLocation();
        this.card = retrieveCard();

    }

    private int retrieveCard() {
        return 0;
    }

    private String retrieveLocation() {
        return null;
    }

    private Float retrieveAmount() {
        return null;
    }

    private int retrieveTransactionType() {
        return 0;
    }
}
