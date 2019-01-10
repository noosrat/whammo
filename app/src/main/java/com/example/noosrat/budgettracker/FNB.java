package com.example.noosrat.budgettracker;

import android.util.Log;

public class FNB {

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


    private void calculateAll() {

        int pos = 0;
        String message = this.originalMessage;


        pos = message.indexOf(")"); //end of FNB:-)
        message = message.substring(pos + 2);



        //GETTING AMOUNT
        pos = message.indexOf(" ");
        this.amount = message.substring(0, pos);

        //GETTING IN or OUT
        pos = message.indexOf("We have noticed that");
        if (pos == -1) {
            pos = message.indexOf("paid to");
            if (pos == -1) {
                pos = message.indexOf("reserved for purchase");
                if (pos == -1) {
                    pos = message.indexOf("Paid from");
                    if (pos == -1) {
                        pos = message.indexOf("withdrawn");
                        if (pos == -1) {
                            pos = message.indexOf("paid from");
                            if (pos == -1)
                                transactionType = Transaction.TRANSACTION_TYPE_INFO;
                            else {
                                int ref = message.indexOf("Ref.");
                                if (ref == -1) {
                                    transactionType = Transaction.TRANSACTION_TYPE_APP_PURCHASE;
                                    this.reciepient = message.substring(message.indexOf("@ ") + "@ ".length(), message.indexOf(". "));
                                }
                                else {
                                    transactionType = Transaction.TRANSACTION_TYPE_EFT;
                                    String temp = message.substring(message.indexOf("Ref.") + "Ref.".length());
                                    this.reciepient = temp.substring(0, temp.indexOf(". "));
                                }
                            }
                        } else {
                            transactionType = Transaction.TRANSACTION_TYPE_CASH;
                            this.reciepient = "CASH";
                        }
                    } else {
                        int ref = message.indexOf("Ref.");
                        if (ref == -1) {
                            transactionType = Transaction.TRANSACTION_TYPE_UNKNOWN;
                            Log.i("UNKNOWN SMS TYPE",message);
                        }
                        else {
                            transactionType = Transaction.TRANSACTION_TYPE_DEBIT_ORDER;
                            this.reciepient = message.substring(message.indexOf("Ref.") + "Ref.".length(), message.indexOf("Paid"));
                        }
                    }
                } else {
                    transactionType = Transaction.TRANSACTION_TYPE_PAYMENT;
                    this.reciepient = message.substring(message.indexOf("@ ") + "@ ".length(), message.indexOf("from"));
                }
            } else {
                transactionType = Transaction.TRANSACTION_TYPE_DEPOSIT;
                String temp = message.substring(message.indexOf("Eft. Ref.") + "Eft. Ref.".length());
                this.reciepient = temp.substring(0, temp.indexOf(". "));
            }
        } else {
            transactionType = Transaction.TRANSACTION_TYPE_INFO;
        }

    }
}
