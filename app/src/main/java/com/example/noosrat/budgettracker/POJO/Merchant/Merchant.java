package com.example.noosrat.budgettracker.POJO.Merchant;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.noosrat.budgettracker.POJO.Category;

public class Merchant {

    public static final int CATEGORY_EATING_OUT = 801;
    public static final int CATEGORY_ENTERTAINMENT = 802;
    public static final int CATEGORY_GROCERIES = 803;
    public static final int CATEGORY_INSURANCE = 804;
    public static final int CATEGORY_RENT = 805;
    public static final int CATEGORY_PETROL = 806;
    public static final int CATEGORY_TRAVEL = 807;
    public static final int CATEGORY_CLOTHING = 808;
    public static final int CATEGORY_CAR = 809;
    public static final int CATEGORY_LIFESTYLE = 810;
    public static final int CATEGORY_PHONE = 811;
    public static final int CATEGORY_OTHER = 812;

    private String name;
    private String icon;
    private Category category;

    public Merchant() {
    }

    public Merchant(String name) {
        this.name = name;
        this.category = new Category("Other","", "");
    }

    public Merchant(String name, String url, int category) {
        this.name = name;
        this.icon = url;

        calculateCategory(category);
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

    public TextDrawable getIconBitmap() {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT

        int color = generator.getColor("user@gmail.com");

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(name.charAt(0) + "", color);
        return drawable;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private void calculateCategory(int category){
        if (category == CATEGORY_EATING_OUT)
            this.category = new Category("Eating Out","", "");
        else if (category == CATEGORY_ENTERTAINMENT)
            this.category = new Category("Entertainment","", "");
        else if (category == CATEGORY_GROCERIES)
            this.category = new Category("Groceries","", "");
        else if (category == CATEGORY_INSURANCE)
            this.category = new Category("Insurance","", "");
        else if (category == CATEGORY_RENT)
            this.category = new Category("Rent","", "");
        else if (category == CATEGORY_PETROL)
            this.category = new Category("Petrol","", "");
        else if (category == CATEGORY_TRAVEL)
            this.category = new Category("Travel","", "");
        else if (category == CATEGORY_CLOTHING)
            this.category = new Category("Clothing","", "");
        else if (category == CATEGORY_CAR)
            this.category = new Category("Car","", "");
        else if (category == CATEGORY_LIFESTYLE)
            this.category = new Category("Lifestyle","", "");
        else if (category == CATEGORY_PHONE)
            this.category = new Category("Phone","", "");
        else
            this.category = new Category("Other","", "");
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
