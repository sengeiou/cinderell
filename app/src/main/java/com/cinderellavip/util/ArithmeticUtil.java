package com.cinderellavip.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArithmeticUtil {
    public static String convert(double m){
        BigDecimal bg = new BigDecimal(m).setScale(2, RoundingMode.HALF_UP);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }
}
