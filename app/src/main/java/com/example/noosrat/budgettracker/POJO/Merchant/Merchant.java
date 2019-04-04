package com.example.noosrat.budgettracker.POJO.Merchant;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.noosrat.budgettracker.POJO.Category;
import com.example.noosrat.budgettracker.R;
import com.google.firebase.database.Exclude;

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
    private String keyword;
    private Category category;

    public Merchant() {
    }

    public Merchant(String name) {
        this.name = name;
        this.category = new Category("Other", R.drawable.ic_category_other, "#393D3F");
    }

    public Merchant(String name, String url, int category) {
        this.name = name;
        this.icon = url;

        calculateCategory(category);
    }

    public Merchant(String name, String icon, String keyword, Category category) {
        this.name = name;
        this.icon = icon;
        this.keyword = keyword;
        this.category = category;


    }

    public String getName() {
        return name;
    }


    public String getIcon() {
        return icon;
    }

    public String getKeyword() {
        return keyword;
    }

    public Category getCategory() {
        return category;
    }




    @Exclude
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

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



    private void calculateCategory(int category){
        if (category == CATEGORY_EATING_OUT)
            this.category = new Category("Eating Out",R.drawable.ic_category_eating, "#93B93F");
        else if (category == CATEGORY_ENTERTAINMENT)
            this.category = new Category("Entertainment",R.drawable.ic_category_entertainment, "#93B93F");
        else if (category == CATEGORY_GROCERIES)
            this.category = new Category("Groceries",R.drawable.ic_category_groceries, "#26B1C8");
        else if (category == CATEGORY_INSURANCE)
            this.category = new Category("Insurance",R.drawable.ic_category_insurance, "#ED6A5A");
        else if (category == CATEGORY_RENT)
            this.category = new Category("Rent",R.drawable.ic_category_rent, "#C54A86");
        else if (category == CATEGORY_PETROL)
            this.category = new Category("Petrol",R.drawable.ic_category_petrol, "#393D3F");
        else if (category == CATEGORY_TRAVEL)
            this.category = new Category("Travel",R.drawable.ic_category_travel, "#FFC548");
        else if (category == CATEGORY_CLOTHING)
            this.category = new Category("Clothing",R.drawable.ic_category_clothing, "#FFC548");
        else if (category == CATEGORY_CAR)
            this.category = new Category("Car",R.drawable.ic_category_car, "#26B1C8");
        else if (category == CATEGORY_LIFESTYLE)
            this.category = new Category("Lifestyle",R.drawable.ic_category_lifestyle, "#C54A86");
        else if (category == CATEGORY_PHONE)
            this.category = new Category("Phone",R.drawable.ic_category_phone, "#ED6A5A");
        else
            this.category = new Category("Other",R.drawable.ic_category_other, "#393D3F");
    }


}
