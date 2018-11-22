package com.example.noosrat.budgettracker;

public class ABSA {

    public static String[] TRANSACTION_TYPES = {"EFT", "DEBIT ORDER", "PAYMENT", "DEPOSIT", "INFO", "PURCHASE"};
    public static String[] CARD_TYPES = {"DEBIT", "CREDIT", "NOCARD"};
    public static String[] CARD_CODES = {"CHEQ5962", "CCRD0019"};

    public String getAmount() {
        return amount;
    }

    public float getNumberAmount() {
        return Math.abs(Float.parseFloat (amount.replace(",","").substring(1)));
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


    public ABSA(String message) {
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
        if (transaction_type.equals("Pmnt")) {
            return 0;
        }
        else if (transaction_type.equals("Sch t")){
            return 1;
        }
        else if (transaction_type.equals("Dep")){
            return 3;
        }
        else if (transaction_type.equals("Pur")){
            return 5;
        }
        else {
            return 2;
        }


    }

    public String getMessage() {
        return originalMessage;
    }


    private void calculateAll(){

        int pos = this.originalMessage.indexOf(":");
        String message = this.originalMessage.substring(pos+2);

        pos = message.indexOf(","); //end of the card code

        String card_code;
        if (pos > -1)
            card_code = message.substring(0, pos); //card code

        else
            card_code = "";

        this.card = retrieveCard(card_code);

        if (this.card == 0) {
            message = message.substring(pos+2); //everything after card code

            pos = message.indexOf(","); //en of transaction type

            String transaction_type = message.substring(0, pos); //transaction type
            this.transactionType = retrieveTransactionType(transaction_type);

            if ((this.transactionType == 0) || (this.transactionType == 5)){
                message = message.substring(pos+2); //everything after transaction type

                pos = message.indexOf(",");
                message = message.substring(pos+2); //everything after other info we don't need

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos+2); //everything after recipient

                pos = message.indexOf(".")+3;
                this.amount =  message.substring(0, pos); //amount


            }
            else if (this.transactionType == 1){
                message = message.substring(pos+2); //everything after transaction type

                pos = message.indexOf(",");
                message = message.substring(pos+2); //everything after other info we don't need

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos+2); //everything after recipient

                pos = message.indexOf(".")+3;
                this.amount =  message.substring(0, pos); //amount
            }
            else if (this.transactionType == 2){
                pos = message.indexOf("reserved");
                if (pos > -1) {
                    this.reciepient = message.substring(9, pos); //recipient

                    message = message.substring(pos + "reserved".length() + 1); //everything after recipient

                    pos = message.indexOf(".") + 3;
                    this.amount = message.substring(0, pos); //amount
                }
                else
                    this.transactionType = 4;

            }
        }

        else if (this.card == 1) {
            message = message.substring(pos+2); //everything after card code

            pos = message.indexOf(","); //end of transaction type

            String transaction_type = message.substring(0, pos); //transaction type
            this.transactionType = retrieveTransactionType(transaction_type);

            if (this.transactionType == 5){

                message = message.substring(pos+2); //everything after transaction type

                pos = message.indexOf(",");
                message = message.substring(pos+2); //everything after other info we don't need

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos+2); //everything after recipient

                pos = message.indexOf(".")+3;
                this.amount =  message.substring(0, pos); //amount

            }

        }
        else {
            this.transactionType = 4;
            this.reciepient = "";
            this.amount = "";
        }


    }
}
