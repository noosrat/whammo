package com.example.noosrat.budgettracker;

public class FNB {

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
                        transactionType = TRANSACTION_TYPE_INFO;
                    else{
                        transactionType = TRANSACTION_TYPE_CASH;
                        this.reciepient = "CASH";
                    }
                }
                else{
                    transactionType = TRANSACTION_TYPE_EFT;
                    this.reciepient = message.substring(message.indexOf("Ref.")+"Ref.".length(),message.indexOf("Paid"));
                }
            }
            else{
                transactionType = TRANSACTION_TYPE_PAYMENT;
                this.reciepient = message.substring(message.indexOf("@ ")+"@ ".length(),message.indexOf("from"));
            }
        }
        else{
            transactionType = TRANSACTION_TYPE_DEPOSIT;
            String temp = message.substring(message.indexOf("Eft. Ref.")+ "Eft. Ref.".length());
            this.reciepient = temp.substring(0,temp.indexOf(". "));
        }

    }
}
