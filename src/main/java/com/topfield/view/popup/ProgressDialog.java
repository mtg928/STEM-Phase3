/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import java.awt.BorderLayout;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class ProgressDialog extends JDialog implements Runnable {

    private final JPanel panel = new JPanel();
    private JProgressBar jProgressBar;
    private JLabel jLabel;

    public ProgressDialog(JFrame frame, int initial, String statusDescription) {
        setTitle("Progress Dialog.");
        jProgressBar = new JProgressBar();
        jProgressBar.setString("In Progress ");
 

        jProgressBar.setValue(25);
        jProgressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Reading...");
        jProgressBar.setBorder(border);

        Point point = frame.getLocation();
        panel.add(jProgressBar, BorderLayout.NORTH);
        jLabel = new JLabel(statusDescription);
        panel.add(jLabel);
        add(panel);
         
        setResizable(false);
        setAlwaysOnTop(true);
        setSize(300, 300);
        setLocation((int) point.getX() + 100, (int) point.getY() + 100);

    }

    public void setPercentage(int percent) {
        jProgressBar.setValue(percent);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}