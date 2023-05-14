/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//from  ww  w  . j  ava  2 s. co  m
public class PackageMatrix extends JPanel {
    
  public PackageMatrix(String allowedCalc) {
    ImageIcon correct = resize("correct");
    ImageIcon worng = resize("worng");
    
    
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(800, 380));
    
    String [] cal = {"Calc File","RBD","SIL","FMEA","FMECA","EVAC","CCF","FTA","Event Tree"
                     ,"24/7 Email IT Support","User Manual","Technical Manual"};
    //boolean [] val ={true,true,true,true,true,false,true,true,false,true,true};
    String[] columnNames = { "Services", "Allowed" };
    Object[][] data = new Object[cal.length][2];
    List<String> allowedCal = Arrays.asList(allowedCalc.split("\\#"));
    //data[0]=cal;
    
    /*  System.out.println(" allowedCalc - "+allowedCalc);
      
      for (String string : allowedCal) {
          System.out.println("string "+ string);
      }*/

    for (int i = 0; i < cal.length; i++) {
        
     if (allowedCalc.contentEquals("All")) {
           data[i][0]=cal[i];
           data[i][1]=correct;
      } else {
            data[i][0]=cal[i];
            //data[i][1]=cal[i].contentEquals(allowedCal[i])?correct:worng ;
            data[i][1] = allowedCal.contains(cal[i])?correct:worng ;
      }
        
    }
    

    
    /*Object[][] data = { {  "About",aboutIcon }, { "Add",addIcon  },
        { "Copy",copyIcon }, };*/

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model) {
      public Class getColumnClass(int column) {
        //return (column == 1 && getValueAt(1, column).getClass() ==ImageIcon.class) ? Icon.class : Object.class;
        //return getValueAt(1, column).getClass() ==ImageIcon.class? Icon.class : Object.class;
        return (column == 1) ? Icon.class : Object.class;
      }
    };
    table.setRowHeight(30);
    table.setForeground(new Color(31, 97, 141 ));
    table.setFont(table.getFont().deriveFont(18.0f));
    table.setPreferredScrollableViewportSize(table.getPreferredSize());

    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane);
  }
  
  
  public  ImageIcon resize(String name){
  
     Image image = new ImageIcon(getClass().getResource("/coachspecs/images/"+name+".png")).getImage(); // transform it 
     Image newimg = image.getScaledInstance(12, 12,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
     return new ImageIcon(newimg);
  
  }
  
 

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.add(new PackageMatrix("All"));
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

}
