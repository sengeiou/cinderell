package com.cinderellavip.util;

public class PriceFormat {
    public static String getPeice(double price){
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");
        return df.format(price);

    }

    public static String getPeice(String price){
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");
        return df.format(price);

    }

}
