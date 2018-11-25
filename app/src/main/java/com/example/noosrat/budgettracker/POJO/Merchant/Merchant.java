package com.example.noosrat.budgettracker.POJO.Merchant;

public class Merchant {

    private String name;
    private String icon;

    public Merchant() {
    }

    public Merchant(String name, String url) {
        this.name = name;
        this.icon = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
