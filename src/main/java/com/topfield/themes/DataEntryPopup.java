/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import com.google.common.collect.ObjectArrays;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author Murali
 */
public class DataEntryPopup {
    
    
    public static void showMultipleInputMessageDialog(String[] choices,int col,int row,JTable table) {
        
    String[] intial = {"Please Choose"};    
        
    JPanel jPanel = new JPanel();
    JButton jButton = new JButton("Click");
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    
    String [] joined = ObjectArrays.concat(intial, choices, String.class);

        JComboBox petList = new JComboBox(joined);
        if(joined.length <=1){
          petList.setEnabled(false);
        }
        JTextArea textField1 = new JTextArea(5, 40);
         //textField1.setEditable(true);
         textField1.setLineWrap(true);
         textField1.setWrapStyleWord(true);
         textField1.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
         textField1.setText(table.getValueAt(row, col).toString());
         //textField1.setSelectionStart(0);
         //textField1.setSelectionEnd(100);
        
        // this.setLayout(gridBagLayout1);
        //add(jPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        jPanel.setLayout(gridBagLayout2);
        jPanel.setBorder(BorderFactory.createEtchedBorder());
        jButton.setMinimumSize(new Dimension(100, 25));
        jButton.setMaximumSize(new Dimension(100, 25));
        jButton.setPreferredSize(new Dimension(100, 25));
        //jPanel.add(jButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
       // jPanel.add(textArea, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));



        Object[] inputFields = {"Enter the value", new JScrollPane(textField1),
                "Choose the value", petList};

        int option = JOptionPane.showConfirmDialog(null, inputFields, "Enter or choose", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            //String text = textField1.getText() + "\n" + (petList. ? "Checked" : "Unchecked") + "\n" + textField2.getText() + "\n";
            
            if(petList.getSelectedItem().equals(intial[0])){
                table.setValueAt(textField1.getText(), row, col); 
            }else{
                table.setValueAt(petList.getSelectedItem(), row, col);
            } 
            
        }
    }
    
    
    
}
