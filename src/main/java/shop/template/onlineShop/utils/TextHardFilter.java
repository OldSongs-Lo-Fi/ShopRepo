package shop.template.onlineShop.utils;

import java.util.ArrayList;

public class TextHardFilter {

    public static final String[] HARD_WORDS = {"сука", "їбат", "траха", "залуп", "пізда", "блять", "курва",
    "пидор", "хуил", "уебищ", "шлюх", "пизд", "сосуха", "лох", "лошара"};


    public static void filterTextUnsecured(String text){
        String textToCheck = text.toLowerCase();

        for (String hardWord: HARD_WORDS
             ) {
            if (textToCheck.contains(hardWord)){
                throw new RuntimeException("");
            }
        }
    }
}
