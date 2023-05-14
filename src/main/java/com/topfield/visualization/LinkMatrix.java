/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Murali
 */
public class LinkMatrix extends JPanel{

    public LinkMatrix() {
        
        setLayout(new BorderLayout());
        
        JLabel headings = new JLabel("Calculator Linkage Matrix", SwingConstants.CENTER);
        headings.setFont(new Font("TimesRoman", Font.BOLD, 30));
        
        JPanel matrixLayout = new JPanel();
        matrixLayout.setLayout(new GridLayout(8,8));
        
        String [] cal = {"","SIL","FMEA","FMECA","EVACUATION","CCF","EVENT TREE","FAULT TREE"};
        
        JButton ref;
        for (int i = 0; i < cal.length; i++) {
            
            for (int j = 0; j <  cal.length; j++) {
                
                if (i==0) {
                     ref =new JButton(cal[j]);
                     matrixLayout.add(ref);
                }else if (j==0) {
                    ref =new JButton(cal[i]);
                    matrixLayout.add(ref);
                }else{
                    ref =new JButton("X");
                    ref.setBackground(Color.WHITE);
                    ref.setForeground(Color.GREEN);
                    matrixLayout.add(ref);
                }
{
                
                }
               
            }
        }
        
        add(headings,BorderLayout.NORTH);
        add(matrixLayout,BorderLayout.CENTER);
        
    }
    
    
    public static void main(String[] args) {
        
      JFrame  f = new JFrame("panel"); 
           // add panel to frame 
        f.add(new LinkMatrix()); 
  
        // set the size of frame 
        f.setSize(300, 300); 
  
        f.show();
        
    }
    
}
