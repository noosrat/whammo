package com.example.noosrat.budgettracker.POJO;

public class Category {

    private String name;
    private String icon;
    private String colour;

    public Category(String name, String icon, String colour) {
        this.name = name;
        this.icon = icon;
        this.colour = colour;
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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
