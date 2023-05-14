/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.info;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Murali
 */
public class FMEAIntialValidation extends JPanel {

    public FMEAIntialValidation(String[] item,String[] ref, String [] check) {
        
     
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(560, 300));
        
        add(new ItemValidator(check),BorderLayout.NORTH);
        add(new ItemValidator(check),BorderLayout.CENTER);
        
        
        
    }
    
    
     public static void main(String[] args) {
      
     String[] columnNames = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ","Created Time"};
      String[] check = {"Line no1","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)1","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures1","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ","Created Time"};
     
        
        
      JFrame frame = new JFrame("Swing Tester");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new FMEAIntialValidation(columnNames,columnNames,check));
      //frame.setLayout(new BorderLayout());
      frame.setSize(560, 300);      
      frame.setLocationRelativeTo(null);  
      frame.setVisible(true);     

   }

    
}
