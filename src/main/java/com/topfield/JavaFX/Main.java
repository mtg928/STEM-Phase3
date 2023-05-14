/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.JavaFX;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

public class Main {
    
    public static void main(String []args) {
      JFrame frame = new JFrame();
  
       JOptionPane pane = new JOptionPane("Are you sure you want to import this file?n" +
                                               "(try presssing the x button!)",
                                               JOptionPane.QUESTION_MESSAGE,
                                               JOptionPane.YES_NO_CANCEL_OPTION);
      final JDialog dialog = new JDialog(frame, "Question", true);
      dialog.setContentPane(pane);
  
      dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
  
      pane.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent e) {
            String prop = e.getPropertyName();
            if (dialog.isVisible()
             && (e.getSource() == pane)
             && (prop.equals(JOptionPane.VALUE_PROPERTY) ||
                 prop.equals(JOptionPane.INPUT_VALUE_PROPERTY)))
            {
               System.out.println("Caught, JOptionPane is ready to close!");
               //dialog.setVisible(false);
            }
         }
      });
  
      dialog.pack();
      dialog.setVisible(true);
      Object value = pane.getValue();
      System.out.println("Option " + value + " selected!");
       
      System.exit(1);
   }
    
}
