/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.info;

import static com.topfield.info.ItemValidator.getLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class UploadHeadingValidation extends JPanel {
  
   private JPanel controlPanel;
   
   public UploadHeadingValidation(String[] ref, String [] check){
      setLayout(new BorderLayout()); 

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      JPanel panel = new JPanel();
      panel.setBackground(Color.darkGray);
      panel.setSize(300,300);
      GridLayout layout = new GridLayout(0,2);
      layout.setHgap(30);
      layout.setVgap(10);
      
      panel.setLayout(layout);       
      
       for (int i = 0; i < ref.length; i++) {
         panel.add(getLabel((i+1)+")"+ref[i]+" - "+check[i],ref[i].contentEquals(check[i])));
       }
      
      
      /*panel.add(getLabel("One - One",true));
      panel.add(getLabel("Two - Two",true)); 
      panel.add(getLabel("Three - Three",false));
      panel.add(getLabel("Four - Four",true));
      panel.add(getLabel("One - One",true));
      panel.add(getLabel("Two - Two",true)); 
      panel.add(getLabel("Three - Three",false));*/
      controlPanel.add(panel);  
      
      JScrollPane listScroller = new JScrollPane(controlPanel);
      listScroller.setPreferredSize(new Dimension(850, 250));
      listScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

      add(listScroller,BorderLayout.CENTER);
      
      
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
   
   
   public static void main(String[] args){
      
      String[] columnNames = {"Line no","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ","Created Time"};
      String[] check = {"Line no1","Subsystem Breakdown Code","Subsystem/ Component","Function","Phase","Failure mode","Failure Cause","Failure Effect:(Local System Level)",
                                 "Failure Effect:(Train Subsystem Level)","Failure Effect:(Train Level)1","Importance (B)","Failure detection","Preventive and compensating measures","Occurrence (A)","Detection measures1","Detection (E)","RPZ","Improvement measures","Responsible / Date","Undertaken measures","Importance (B)","Occurrence (A)","Detection (E)","RPZ","Created Time"};
     
       
     UploadHeadingValidation swingLayoutDemo = new UploadHeadingValidation(columnNames,check);  
          
     
       
      JFrame mainFrame = new JFrame("Java SWING Examples");
      mainFrame.setSize(1060, 500);    
      mainFrame.add(swingLayoutDemo);
      mainFrame.setVisible(true);
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      }); 
       

   }
}
