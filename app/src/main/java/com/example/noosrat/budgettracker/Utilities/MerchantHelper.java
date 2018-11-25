package com.example.noosrat.budgettracker.Utilities;

import com.example.noosrat.budgettracker.POJO.Merchant.Merchant;

public class MerchantHelper {

    public MerchantHelper() {

    }

    public Merchant getMerchant(String description) {
        String lowerDesc = description.toLowerCase().replaceAll(" ","");
        switch (lowerDesc.charAt(0)) {
            case 'a':

                break;
            case 'b':
                break;
            case 'c':
                break;
            case 'd':

                if (lowerDesc.contains("discprem")) {
                    return new Merchant("Discovery Medical Aid", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/discovery.png?alt=media&token=980fbffc-b027-4e45-be8c-b2557aa3907e");
                } else if (lowerDesc.contains("discinsure")) {
                    return new Merchant("Discovery Car Insurance", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/discovery.png?alt=media&token=980fbffc-b027-4e45-be8c-b2557aa3907e");
                }
                break;
            case 'e':
                break;
            case 'f':
                break;
            case 'g':
                if (lowerDesc.contains("godaddy")) {
                    return new Merchant("GoDaddy", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/mcdonalds.png?alt=media&token=2aa095a6-5839-477a-85fe-56ed882ac66a");
                }
                break;
            case 'h':
                break;
            case 'i':
                break;
            case 'j':
                break;
            case 'k':
                break;
            case 'l':
                break;
            case 'm':
                if (lowerDesc.contains("mcdonalds")) {
                    return new Merchant("McDonalds", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/mcdonalds.png?alt=media&token=2aa095a6-5839-477a-85fe-56ed882ac66a");
                }
                break;
            case 'n':
                break;
            case 'o':
                break;
            case 'p':
                if (lowerDesc.contains("pnp")) {
                    return new Merchant("Pick n Pay", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/picknpay.jpg?alt=media&token=bd4b34a8-2fe6-4c11-9586-a58341fb9b5c");
                }
                break;
            case 'q':
                break;
            case 'r':
                break;
            case 's':
                if (lowerDesc.contains("shakeshack")) {
                    return new Merchant("Shake Shack", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/shakeshack.png?alt=media&token=7113a537-905f-4fa2-b7eb-6d1f6202f492");
                }
                break;
            case 't':
                break;
            case 'u':
                if (lowerDesc.contains("uber")) {
                    return new Merchant("Uber", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/uber.png?alt=media&token=7cb37a66-f793-497c-a12a-82d0217122c5");
                }
                break;
            case 'v':
                if (lowerDesc.contains("virginact")) {
                    return new Merchant("Virgin Active", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/virginactive.jpg?alt=media&token=576d954f-1b60-472e-a464-dad753262553");
                }
                break;
            case 'w':
                if (lowerDesc.contains("woolworths")) {
                    return new Merchant("Woolworths", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/woolworths.jpg?alt=media&token=40c0c54e-0930-4578-8661-02d37d320951");
                }
                break;
            case 'x':
                break;
            case 'y':
                break;
            case 'z':
                break;
            default:
                return new Merchant(description, "");
        }
        return new Merchant(description, "https://api.adorable.io/avatars/285/"+description+".png");

    }
}
