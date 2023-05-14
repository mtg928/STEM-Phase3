/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import com.topfield.main.InternalFrameDemo;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Murali
 */
public class BusinessException {

    public static void showBusinessException(String errorcode, String errormsg, int type) {

        JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame, errormsg, errorcode, type);

    }

    public static int showBusinessConformation( Object content,boolean flag) {
        Object[] options1 = flag ? new Object[]{"Yes", "No"} : new Object[]{"OK", "Cancel"};
        return JOptionPane.showOptionDialog(InternalFrameDemo.mainFrame, content, "Conformation ?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options1, null);

    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
