/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

/**
 *
 * @author Murali
 */
import java.awt.Color;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class CalcfileLinkageReport extends JPanel {

    private JTable table;
    private JLabel label =new JLabel("Selection Mode");
    
    public CalcfileLinkageReport() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        
        add(new JScrollPane(table));

        add(label);

    }

    public void setData(String[] headers, String[][] data) {

        table.setModel(new DefaultTableModel(data, headers));
    }
    
    public void setLabel(String text){
    
        label.setText(text);
    
    }

    public static void createAndShowGUI() {

        String[] columnNames = { "APP Name", "APP Id", "Description", "CMM ID","Description" };
        String [][] data = {
            {"Kathy", "Smith",
                "Snowboarding", "Smith",
                "Snowboarding"},
            {"John", "Doe",
                "Rowing", "Smith",
                "Snowboarding"},
            {"Sue", "Black",
                "Knitting", "Smith",
                "Snowboarding"},
            {"Jane", "White",
                "Speed reading", "Smith",
                "Snowboarding"},
            {"Joe", "Brown",
                "Pool", "Smith",
                "Snowboarding"}
        };

        //Disable boldface controls.
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Create and set up the window.
        JFrame frame = new JFrame("TableSelectionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CalcfileLinkageReport newContentPane = new CalcfileLinkageReport();
        newContentPane.setData(columnNames, data);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
