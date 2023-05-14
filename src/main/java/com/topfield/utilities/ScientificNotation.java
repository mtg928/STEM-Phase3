/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 *
 * @author Murali
 */
public class ScientificNotation extends NumberFormat {

    private final DecimalFormat decimal;

    public ScientificNotation(String pattern) {
        decimal = new DecimalFormat(pattern);
    }

    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        StringBuffer sb = new StringBuffer();
        sb.append(modified(Math.abs(number) > 1.0, decimal.format(number, toAppendTo, pos).toString()));
        return sb;
    }

    private String modified(boolean large, String s) {
        return large ? s.replace("E", "E+") : s;
    }

    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        StringBuffer sb = new StringBuffer();
        sb.append(modified(true, decimal.format(number, toAppendTo, pos).toString()));
        return sb;
    }

    public Number parse(String source, ParsePosition parsePosition) {
        return decimal.parse(source, parsePosition);
    }

    public void setPositivePrefix(String newValue) {
        decimal.setPositivePrefix(newValue);
    }
    
    public String sciFormat(double number){
    
        return format(number).toString();
    }
    
    public String sciFormatString(String number){
    
        return format(NumberConversion.NVLDouble(number, 0)).toString();
    }
}
