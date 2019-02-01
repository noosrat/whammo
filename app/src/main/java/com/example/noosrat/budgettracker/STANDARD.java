package com.example.noosrat.budgettracker;

public class STANDARD {
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

    public String getMessage() {
        return originalMessage;
    }

    int transactionType;
    String amount;
    String reciepient;
    String originalMessage;
    int card;

    public STANDARD(String message) {
        this.originalMessage = message;
        calculateAll();

    }

    private void calculateAll() {
        int pos = 0;
        String message = this.originalMessage;


        pos = message.indexOf(":"); //end of Standard Bank:
        message = message.substring(pos + 2);

        //GETTING IN or OUT
        pos = message.indexOf("paid from Acc.");
        if (pos == -1) {
            pos = message.indexOf("paid to Acc.");
            if (pos == -1) {
                pos = message.indexOf("Debit Order");
                if (pos == -1) {
                    pos = message.indexOf("Stop Order");
                    if (pos == -1)
                        transactionType = Transaction.TRANSACTION_TYPE_INFO;
                    else {
                        transactionType = Transaction.TRANSACTION_TYPE_DEBIT_ORDER;
                        message = message.substring(message.indexOf("Stop Order ") + "Stop Order ".length());

                        //GETTING AMOUNT
                        pos = message.indexOf(" ");
                        this.amount = message.substring(0, pos);

                        String temp = message.substring(message.indexOf(" ") + " ".length());
                        this.reciepient = temp.substring(0, temp.indexOf("; "));
                    }
                } else {
                    transactionType = Transaction.TRANSACTION_TYPE_DEBIT_ORDER;
                    message = message.substring(message.indexOf("Debit Order ") + "Debit Order ".length());

                    //GETTING AMOUNT
                    pos = message.indexOf(" ");
                    this.amount = message.substring(0, pos);

                    String temp = message.substring(message.indexOf(" ") + " ".length());
                    this.reciepient = temp.substring(0, temp.indexOf("; "));
                }
            } else {
                transactionType = Transaction.TRANSACTION_TYPE_DEPOSIT;
                //GETTING AMOUNT
                pos = message.indexOf(" ");
                this.amount = message.substring(0, pos);

                String temp = message.substring(message.indexOf("from") + "from".length());
                this.reciepient = temp.substring(0, temp.indexOf(". "));
            }
        } else {
            transactionType = Transaction.TRANSACTION_TYPE_PAYMENT;
            //GETTING AMOUNT
            pos = message.indexOf(" ");
            this.amount = message.substring(0, pos);

            String temp = message.substring(message.indexOf("to") + "to".length());
            this.reciepient = temp.substring(0, temp.indexOf("ZAF"));
        }

    }
}
