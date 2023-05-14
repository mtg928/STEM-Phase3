/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.info;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Murali
 */
public class ItemValidator extends JPanel {

    public ItemValidator(String [] data) {
        
        
      JPanel panel = new JPanel();
      LayoutManager layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
      panel.add(Box.createRigidArea(new Dimension(2,5)));
      panel.setLayout(layout);       
      
      
        for (int i = 0; i < data.length; i++) {
            panel.add(getLabel(data[i],true));
        }
      /*panel.add(getLabel("One - One",true));
      panel.add(getLabel("Two - Two",true)); 
      panel.add(getLabel("Three - Three",false));
      panel.add(getLabel("Four - Four",true));*/

      
      JScrollPane listScroller = new JScrollPane(panel);
      listScroller.setPreferredSize(new Dimension(850, 200));
      listScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        
      add(listScroller);
        
    }
    

    
    
   public static void main(String[] args) {
      
       String[] columnNames = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ","Created Time"}; 
       
      JFrame frame = new JFrame("Swing Tester");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new ItemValidator(columnNames));
      frame.setSize(560, 300);      
      frame.setLocationRelativeTo(null);  
      frame.setVisible(true);     

   }


    
    public static JLabel getLabel(String massage,boolean correct){
     JLabel label = new JLabel(massage);
    
        if (correct) {
            label.setForeground(new Color(17, 122, 101));
        } else {
             label.setForeground(Color.RED);
        }
     
     return label;
    }
}
