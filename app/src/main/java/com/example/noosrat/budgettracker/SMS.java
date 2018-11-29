package com.example.noosrat.budgettracker;

import java.util.Date;

public class SMS {

    private String message;
    private Date date;

    public SMS() {
    }

    public SMS(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
