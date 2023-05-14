/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author Murali
 */
public class NumberConversion {

    public static int NVL(String value, int valueIfNull) {
        int number = valueIfNull;
        try {
            if (value != null) {
                number = Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            number = valueIfNull;
        }
        return number;
    }
    


    public static double NVLDouble(String value, double valueIfNull) {
        double number = valueIfNull;
        try {
            if (value != null && !value.contentEquals("NaN")) {  //Double.parseDouble(value) != Double.NaN  && !value.contentEquals("NaN")
                number = Double.parseDouble(value);
            }
        } catch (NumberFormatException e) {
            number = valueIfNull;
        }
        return number;
    }

    public static float NVLFloat(String value, int valueIfNull) {
        float number = valueIfNull;
        try {
            if (value != null) {
                number = Float.parseFloat(value);
            }
        } catch (NumberFormatException e) {
            number = valueIfNull;
        }
        return number;
    }

    public static int[] StringToIntArray(String[] value) {
        int[] res = new int[value.length];

        for (int i = 0; i < value.length; i++) {

            res[i] = NVL(value[i], 0);
        }

        return res;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double roundNew(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String scientificNotation(double value, int decimals) {
        String res = "0.";

        for (int i = 0; i < decimals; i++) {
            res += "#";
        }
        res += "E0";

        //System.out.println(res);

        DecimalFormat decimalFormat = new DecimalFormat(res); //"00.##E0"

        return decimalFormat.format(value);
    }

    public static String scientificNotation(String value, int decimals) {
        String res = "0.";

        for (int i = 0; i < decimals; i++) {
            res += "#";
        }
        res += "E0";

        //System.out.println(res);
        DecimalFormat decimalFormat = new DecimalFormat(res); //"00.##E0"

        return decimalFormat.format(Double.parseDouble(value));
    }

    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return (n * factorial(n - 1));
        }
    }

}
