package com.example.noosrat.budgettracker;

public class FeedItem {
    public static final int TYPE_ITEM_DATE = 0;
    public static final int TYPE_ITEM_TRANSACTION = 1;


    private Transaction transaction;
    private String displayTimePeriodGroup;
    public int itemViewType;

    public FeedItem(Transaction transaction) {
        this.transaction = transaction;
        itemViewType = TYPE_ITEM_TRANSACTION;
    }

    public FeedItem(String displayTimePeriodGroup) {
        this.displayTimePeriodGroup = displayTimePeriodGroup;
        itemViewType = TYPE_ITEM_DATE;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public String getDisplayTimePeriodGroup() {
        return displayTimePeriodGroup;
    }

}
