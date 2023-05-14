/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.valid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Murali
 */
public class NumDocFilter extends DocumentFilter {

    private Pattern regexCheck = Pattern.compile("[0-9]+"); 

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }

        if (regexCheck.matcher(str).matches()) {
            super.insertString(fb, offs, str, a);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs)
            throws BadLocationException {
        if (str == null) {
            return;
        }

        if (regexCheck.matcher(str).matches()) {
            System.out.println(str+" matched "+fb.getDocument().getText(0,
                        fb.getDocument().getLength()));
            fb.replace(offset, length, str, attrs);
        }
    }

    public static void main(String[] args) throws Exception {
        String data = "0.9";
       Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        
         if (pattern.matcher(data).matches()) {
             System.out.println("matched");
        }
    }
}
