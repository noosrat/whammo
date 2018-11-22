package com.example.noosrat.budgettracker;

public class FNB {

    public static String[] TRANSACTION_TYPES = {"PAYMENT", "DEPOSIT", "EFT", "INFO"};
    public static String[] CARD_TYPES = {"DEBIT", "CREDIT", "NOCARD"};
    public static String[] CARD_CODES = {"CHEQ5962", "CCRD0019"};

    public String getAmount() {
        return amount;
    }

    public String getReciepient() {
        return reciepient;
    }

    public int getCard() {
        return card;
    }

    public int getTransactionType() {
        return transactionType;
    }

    int transactionType;
    String amount;
    String reciepient;
    String originalMessage;
    int card;


    public FNB(String message) {
        this.originalMessage = message;
        calculateAll();

    }

    private int retrieveCard(String card_code) {
        if (card_code.equals("R")){
            return 0;
        }

        return 1;
    }

    private String retrieveLocation() {
        return null;
    }

    private Float retrieveAmount() {
        return null;
    }

    private int retrieveTransactionType(String transaction_type) {
        if (transaction_type.equals("reserved")) {
            return 0;
        }
        else if (transaction_type.equals("paid")) {
            return 1;
        }
        else if (transaction_type.equals("withdrawn")){
            return 2;
        }
        else{
            return 3;
        }
    }

    public String getMessage() {
        return originalMessage;
    }


    private void calculateAll(){

        int pos = 0;
        String message = this.originalMessage;

        if (message.charAt(0) != 'R') {
            pos = message.indexOf(")"); //end of FNB:-)
            message = message.substring(pos+2);
        }


        pos = message.indexOf(" "); //end of amount

        this.amount = message.substring(0, pos); //amount

        message = message.substring(pos+1); //everything after amount

        pos = message.indexOf(" "); //end of transaction keyword

        String transaction_type;
        if (pos > -1)
            transaction_type = message.substring(0, pos); //transaction type
        else{
            pos = message.indexOf(".");
            transaction_type = message.substring(0, pos); //transaction type
        }


        this.transactionType = retrieveTransactionType(transaction_type);


        if (this.transactionType == 0){
            pos = message.indexOf("@");

            message = message.substring(pos+2); //everything after transaction keyword
            pos = message.indexOf("from");

            this.reciepient = message.substring(0, pos); //recipient

        }
        else if (this.transactionType == 1){
            pos = message.indexOf("Eft. Ref.");

            message = message.substring(pos+"Eft. Ref.".length()); //everything after transaction keyword
            pos = message.indexOf(".");

            this.reciepient = message.substring(0, pos); //recipient

        }
        else if (this.transactionType == 2){
            this.reciepient = "CASH"; //recipient

        }
        else if (this.transactionType == 3){
            pos = message.indexOf("Ref.");

            this.reciepient = message.substring(pos+"Ref.".length()); //recipient

        }


    }
}
