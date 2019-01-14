package com.example.noosrat.budgettracker.POJO.Merchant;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

public class Merchant {

    private String name;
    private String icon;

    public Merchant() {
    }

    public Merchant(String name) {
        this.name = name;
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
}
