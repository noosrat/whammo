package com.example.noosrat.budgettracker.POJO;

public class Category {

    private String name;
    private int icon;
    private String colour;
    private int budget;

    public Category() {
    }

    public Category(String name, int icon, String colour) {
        this.name = name;
        this.icon = icon;
        this.colour = colour;
    }

    public Category(String name, int icon, String colour, int budget) {
        this.name = name;
        this.icon = icon;
        this.colour = colour;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
