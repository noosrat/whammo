package com.example.noosrat.budgettracker;

public class FNB {

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

    private int retrieveCard(char card_code) {
        if (card_code =='R'){
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


        pos = message.indexOf(")"); //end of FNB:-)
        message = message.substring(pos+2);



        //GETTING AMOUNT
        pos = message.indexOf(" ");
        this.amount = message.substring(0, pos);

        //GETTING IN or OUT
        pos = message.indexOf("paid to");
        if (pos == -1) {
            pos = message.indexOf("reserved for purchase");
            if (pos == -1) {
                pos = message.indexOf("Paid from");
                if (pos == -1) {
                    pos = message.indexOf("withdrawn");
                    if (pos == -1)
                        TRANSACTION_TYPE_INFO = true;
                    else{
                        TRANSACTION_TYPE_CASH = true;
                        this.reciepient = "CASH";
                    }
                }
                else{
                    TRANSACTION_TYPE_EFT = true;
                    this.reciepient = message.substring(message.indexOf("Ref.")+"Ref.".length(),message.indexOf("Paid"));
                }
            }
            else{
                TRANSACTION_TYPE_PAYMENT = true;
                this.reciepient = message.substring(message.indexOf("@ ")+"@ ".length(),message.indexOf("from"));
            }
        }
        else{
            TRANSACTION_TYPE_DEPOSIT = true;
            String temp = message.substring(message.indexOf("Eft. Ref.")+ "Eft. Ref.".length());
            this.reciepient = temp.substring(0,temp.indexOf(". "));
        }

    }
}
