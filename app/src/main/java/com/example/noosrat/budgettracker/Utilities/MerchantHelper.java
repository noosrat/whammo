package com.example.noosrat.budgettracker.Utilities;

import com.example.noosrat.budgettracker.POJO.Merchant.Merchant;

public class MerchantHelper {

    public MerchantHelper() {

    }

    public Merchant getMerchant(String description) {
        String lowerDesc = description.toLowerCase().replaceAll(" ","");
        switch (lowerDesc.charAt(0)) {
            case 'a':
                if (lowerDesc.contains("activesushi")) {
                    return new Merchant("Active Sushi", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/activesushi.jpeg?alt=media&token=9c457043-f71f-4714-ade6-50d5317473e5");
                }
                break;
            case 'b':
                if (lowerDesc.contains("benwei")) {
                    return new Merchant("Ben Wei", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/benwei.png?alt=media&token=b7ecb229-87be-48cd-9e6f-72a60d0fe7ac");
                } else if (lowerDesc.contains("bootlegger")) {
                    return new Merchant("Bootlegger Coffee Company", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/bootlegger.jpg?alt=media&token=0aafb469-8ef5-4604-9208-fe3f15bf10c1");
                }
                break;
            case 'c':
                if (lowerDesc.contains("checkers")) {
                    return new Merchant("Checkers", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/checkers.jpg?alt=media&token=0f6cfa63-a083-4d1b-acac-b4499f471e96");
                }
                break;
            case 'd':

                if (lowerDesc.contains("discprem")) {
                    return new Merchant("Discovery Medical Aid", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/discovery.png?alt=media&token=980fbffc-b027-4e45-be8c-b2557aa3907e");
                } else if (lowerDesc.contains("discinsure")) {
                    return new Merchant("Discovery Car Insurance", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/discovery.png?alt=media&token=980fbffc-b027-4e45-be8c-b2557aa3907e");
                }
                break;
            case 'e':
                if (lowerDesc.contains("engen")) {
                    return new Merchant("Engen", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/engen.jpg?alt=media&token=3a777aa0-3dab-4caf-a41f-6a5aef8bb6c6");
                }
                break;
            case 'f':
                if (lowerDesc.contains("flysafair")) {
                    return new Merchant("FlySafair", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/flysafair.png?alt=media&token=ff160dde-961b-441e-8770-ce64bc2b9aa9");
                } else if (lowerDesc.contains("fourandtwenty")) {
                    return new Merchant("Four & Twenty Café", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/fourandtwenty.png?alt=media&token=b9cad9da-f588-4626-8525-d9e628cbabfa");
                }
                break;
            case 'g':
                if (lowerDesc.contains("godaddy")) {
                    return new Merchant("GoDaddy", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/mcdonalds.png?alt=media&token=2aa095a6-5839-477a-85fe-56ed882ac66a");
                } else if (lowerDesc.contains("game")) {
                    return new Merchant("Game", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/game.jpg?alt=media&token=80ddd0d9-93f5-459e-b621-bafa9408ab52");
                }
                break;
            case 'h':
                break;
            case 'i':
                break;
            case 'j':
                break;
            case 'k':
                if (lowerDesc.contains("knead")) {
                    return new Merchant("Knead Bakery", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/knead.png?alt=media&token=9cf7a2fd-5b50-4819-b7dd-80f7d05d0b94");
                } else if (lowerDesc.contains("kauai")) {
                    return new Merchant("Kauai", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/kuaui.jpg?alt=media&token=b409eaa7-0103-42f8-9bce-cd045b5cabbb");
                }
                break;
            case 'l':
                break;
            case 'm':
                if (lowerDesc.contains("mcdonalds")) {
                    return new Merchant("McDonalds", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/mcdonalds.png?alt=media&token=2aa095a6-5839-477a-85fe-56ed882ac66a");
                } else if (lowerDesc.contains("momentummm")) {
                    return new Merchant("Multiply", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/multiply.png?alt=media&token=ac3f5a2d-7b48-4cbf-921f-f6d98f9c00a3");
                } else if (lowerDesc.contains("mochachos")) {
                    return new Merchant("Mochachos Mexican Fiesta", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/mochachos.png?alt=media&token=23090aa4-af08-49c6-91f1-10111ee3a396");
                } else if (lowerDesc.contains("mrdelivery")) {
                    return new Merchant("Mr Delivery Food", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/mrd.jpg?alt=media&token=564d30e8-84c7-4695-8698-156c88637c43");
                }
                break;
            case 'n':
                if (lowerDesc.contains("numetro")) {
                    return new Merchant("Nu Metro", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/numetro.png?alt=media&token=908326d1-1a00-4a85-a6d6-41db233af8be");
                } else if (lowerDesc.contains("newyorkbage")) {
                    return new Merchant("New York Bagels", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/nyb.jpg?alt=media&token=9b20a83e-df34-4e48-9c11-ce25a34c0e97");
                }
                break;
            case 'o':
                break;
            case 'p':
                if (lowerDesc.contains("pnp")) {
                    return new Merchant("Pick n Pay", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/picknpay.jpg?alt=media&token=bd4b34a8-2fe6-4c11-9586-a58341fb9b5c");
                } else if (lowerDesc.contains("profmed")) {
                    return new Merchant("Profmed", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/profmed.png?alt=media&token=5618ccd5-1bcd-4cf4-9ef3-0bf0438d8f84");
                }
                break;
            case 'q':
                break;
            case 'r':
                break;
            case 's':
                if (lowerDesc.contains("shakeshack")) {
                    return new Merchant("Shake Shack", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/shakeshack.png?alt=media&token=7113a537-905f-4fa2-b7eb-6d1f6202f492");
                } else if (lowerDesc.contains("shell")) {
                    return new Merchant("Shell", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/shell.png?alt=media&token=512a141b-3817-41c7-85c8-ae83ed64eaa2");
                } else if (lowerDesc.contains("spar")) {
                    return new Merchant("Spar", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/spar.png?alt=media&token=9f32925e-b869-4db3-a909-d8fb5a3bf937");
                } else if (lowerDesc.contains("sterkinekor")) {
                    return new Merchant("Ster Kinekor", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/sterkinekor.jpg?alt=media&token=22ccc537-fa63-40b8-8688-cc6be9b022aa");
                } else if (lowerDesc.contains("stratum")) {
                    return new Merchant("Stratum Benefits", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/stratum.jpg?alt=media&token=c3faf83d-b719-4f2a-9fa2-baae3eb0dce7");
                } else if (lowerDesc.contains("superbalist")) {
                    return new Merchant("Superbalist", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/superbalist.jpg?alt=media&token=12d9cbaa-e2f2-4ad6-aa77-74e2eb84bce4");
                }
                break;
            case 't':
                if (lowerDesc.contains("twt")) {
                    return new Merchant("Tiger Wheel and Tyre", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/twt.jpg?alt=media&token=cea4181f-137c-4afc-95b9-e76e3f8c3a19");
                }
                break;
            case 'u':
                if (lowerDesc.contains("uber")) {
                    return new Merchant("Uber", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/uber.png?alt=media&token=7cb37a66-f793-497c-a12a-82d0217122c5");
                }
                break;
            case 'v':
                if (lowerDesc.contains("virginact")) {
                    return new Merchant("Virgin Active", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/virginactive.jpg?alt=media&token=576d954f-1b60-472e-a464-dad753262553");
                } else if (lowerDesc.contains("vodacom")) {
                    return new Merchant("Vodacom", "https://firebasestorage.googleapis.com/v0/b/spent-bdda5.appspot.com/o/vodacom.jpg?alt=media&token=42a7bcdc-c51d-4084-b840-f082d6b7ad84");
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
