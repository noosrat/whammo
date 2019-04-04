package com.example.noosrat.budgettracker.Utilities;

import com.example.noosrat.budgettracker.POJO.Merchant.Merchant;
import com.example.noosrat.budgettracker.Singleton.SpentSingleton;

import java.util.Random;

public class MerchantHelper {

    public MerchantHelper() {

    }

    public Merchant getMerchant(String description) {
        String lowerDesc = description.toLowerCase().replaceAll(" ","");
        lowerDesc = lowerDesc.replaceAll("snapscan","");
        lowerDesc = lowerDesc.replaceAll("zap\\*","");
        lowerDesc = lowerDesc.replaceAll("zap","");
        lowerDesc = lowerDesc.replaceAll("paydzapper\\*","");
        lowerDesc = lowerDesc.replaceAll("purch","");

        for (int i = 0; i < SpentSingleton.merchantList.size(); i++) {
            if (lowerDesc.contains(SpentSingleton.merchantList.get(i).getKeyword())) {
                return SpentSingleton.merchantList.get(i);
            }
        }
        return new Merchant(description);
    }
}
