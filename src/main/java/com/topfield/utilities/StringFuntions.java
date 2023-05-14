/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author Murali
 */
public class StringFuntions {

    public static String[] convertObjToStr(Object[] obj) {

        String[] dest = new String[obj.length];
        System.arraycopy(obj, 0, dest, 0, obj.length);

        System.out.println(Arrays.toString(dest));
     return dest;
    }

    public static String replaceCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    public static String MultipleLine(String text, int spiltLength) {

        String res = "";

        for (int i = 0; i < text.length(); i += spiltLength) {
            res = res + text.substring(i, Math.min(i + spiltLength, text.length())) + "\n";
            //System.out.println(res);
        }

        return res;
    }

    public static String DoubleRound(Double d) {

        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        String res = "";
        String test = d.toString();
        int index = test.indexOf('E');

        if (index < 0) {

            res = df.format(d);
        } else if (test.length() > 8) {

            //System.out.println("test - " + test);
            //System.out.println("index -" + index);
            res = test.substring(0, 6) + test.substring((test.length() - 3));

        } else {

            res = test;

        }

        return res;
    }
    
    public static String stringCutter(String des, int limit){
    
        if (des.length()> limit) {
            des = des.substring(0,(limit-3))+"...";
        } 
    
    return des;
    }
    
    public static boolean isNotEmptyString(String val){

       return (val != null && !val.isEmpty());
    }
    
    public static String NVL (String val,String deft){

       return val == null? deft: val;
    }
    
    public static String NVL (Object val,String deft){

       return val == null? deft: val.toString();
    }

}
