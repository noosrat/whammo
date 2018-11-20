package com.example.noosrat.budgettracker;

public class SMS {

    String[] TRANSACTION_TYPES = {"EFT", "DEBIT ORDER", "PAYMENT", "DEPOSIT", "INFO"};
    String[] CARD_TYPES = {"DEBIT", "CREDIT", "NOCARD"};
    String[] CARD_CODES = {"CHEQ5962", "CCRD0019"};

    public String getAmount() {
        return amount;
    }

    public String getReciepient() {
        return reciepient;
    }

    public int getCard() {
        return card;
    }

    int transactionType;
    String amount;
    String reciepient;
    String originalMessage;
    int card;


    public SMS(String message) {
        this.originalMessage = message;
        calculateAll();

    }

    private int retrieveCard(String card_code) {
        if (card_code.equals(CARD_CODES[0])){
            return 0;
        }
        else if (card_code.equals(CARD_CODES[1])){
            return 1;
        }

        return 2;
    }

    private String retrieveLocation() {
        return null;
    }

    private Float retrieveAmount() {
        return null;
    }

    private int retrieveTransactionType(String transaction_type) {
        if (transaction_type == "Pmnt") {
            return 0;
        }
        else if (transaction_type == "Sch t"){
            return 1;
        }
        else {
            return 2;
        }


    }

    public String getMessage() {
        return originalMessage;
    }


    public void calculateAll(){

        int pos = this.originalMessage.indexOf(":");
        String message = this.originalMessage.substring(pos+2);

        pos = message.indexOf(","); //end of the card code

        String card_code = message.substring(0, pos); //card code
        this.card = retrieveCard(card_code);

        if (this.card == 0) {
            message = message.substring(pos); //everything after card code

            pos = message.indexOf(","); //en of transaction type

            String transaction_type = message.substring(0, pos); //transaction type
            this.transactionType = retrieveTransactionType(transaction_type);

            if (this.transactionType == 0){
                message = message.substring(pos); //everything after transaction type

                pos = message.indexOf(",");
                message = message.substring(pos); //everything after other info we don't need

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos); //everything after recipient

                pos = message.indexOf(",");
                this.amount =  message.substring(0, pos); //amount


            }
            else if (this.transactionType == 1){
                message = message.substring(pos); //everything after transaction type

                pos = message.indexOf(",");
                message = message.substring(pos); //everything after other info we don't need

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos); //everything after recipient

                pos = message.indexOf(",");
                this.amount =  message.substring(0, pos); //amount
            }
            else if (this.transactionType == 2){
                message = message.substring(pos); //everything after transaction type

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos); //everything after recipient

                pos = message.indexOf(",");
                this.amount =  message.substring(0, pos); //amount

            }
        }

        else if (this.card == 1) {
            message = message.substring(pos);

            this.transactionType = 5;
            this.reciepient = "not done yet";
            this.amount = "not done yet";
        }
        else {
            this.transactionType = 4;
            this.transactionType = 5;
            this.reciepient = "";
            this.amount = "";
        }


    }
}
