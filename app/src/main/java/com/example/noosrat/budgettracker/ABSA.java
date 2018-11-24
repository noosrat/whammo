package com.example.noosrat.budgettracker;

public class ABSA {

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


    public static String[] CARD_CODES = {"CHEQ", "CCRD"};

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
        if (card_code.startsWith(CARD_CODES[0])){
            return CARD_TYPE_DEBIT;
        }
        else if (card_code.startsWith(CARD_CODES[1])){
            return CARD_TYPE_CREDIT;
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
            return TRANSACTION_TYPE_EFT;
        }
        else if (transaction_type.equals("Sch t")){
            return TRANSACTION_TYPE_DEBIT_ORDER;
        }
        else if (transaction_type.equals("Dep")){
            return TRANSACTION_TYPE_DEPOSIT;
        }
        else if (transaction_type.equals("Pur")){
            return TRANSACTION_TYPE_PURCHASE;
        }
        else {
            return TRANSACTION_TYPE_PAYMENT;
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

        if (this.card == CARD_TYPE_DEBIT) {
            message = message.substring(pos+2); //everything after card code

            pos = message.indexOf(","); //en of transaction type

            String transaction_type = message.substring(0, pos); //transaction type
            this.transactionType = retrieveTransactionType(transaction_type);

            if ((this.transactionType == TRANSACTION_TYPE_EFT) || (this.transactionType == TRANSACTION_TYPE_PURCHASE)){
                message = message.substring(pos+2); //everything after transaction type

                pos = message.indexOf(",");
                message = message.substring(pos+2); //everything after other info we don't need

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos+2); //everything after recipient

                pos = message.indexOf(".")+3;
                this.amount =  message.substring(0, pos); //amount


            }
            else if (this.transactionType == TRANSACTION_TYPE_DEBIT_ORDER){
                message = message.substring(pos+2); //everything after transaction type

                pos = message.indexOf(",");
                message = message.substring(pos+2); //everything after other info we don't need

                pos = message.indexOf(",");
                this.reciepient =  message.substring(0, pos); //recipient

                message = message.substring(pos+2); //everything after recipient

                pos = message.indexOf(".")+3;
                this.amount =  message.substring(0, pos); //amount
            }
            else if (this.transactionType == TRANSACTION_TYPE_PAYMENT){
                pos = message.indexOf("reserved");
                if (pos > -1) {
                    this.reciepient = message.substring(9, pos); //recipient

                    message = message.substring(pos + "reserved".length() + 1); //everything after recipient

                    pos = message.indexOf(".") + 3;
                    this.amount = message.substring(0, pos); //amount
                }
                else
                    this.transactionType = TRANSACTION_TYPE_INFO;

            }
        }

        else if (this.card == CARD_TYPE_CREDIT) {
            message = message.substring(pos+2); //everything after card code

            pos = message.indexOf(","); //end of transaction type

            String transaction_type = message.substring(0, pos); //transaction type
            this.transactionType = retrieveTransactionType(transaction_type);

            if (this.transactionType == TRANSACTION_TYPE_PURCHASE){

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
            this.transactionType = TRANSACTION_TYPE_INFO;
            this.reciepient = "";
            this.amount = "";
        }


    }
}
