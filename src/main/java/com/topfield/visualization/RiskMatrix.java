/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Murali
 */
public class RiskMatrix extends JPanel{

    public RiskMatrix(String [] CellText,Color [] bgColor,int col, int row) {
        
        //setLayout(new FlowLayout(FlowLayout.LEFT));
          setLayout(new GridLayout(col,row));
        
          
          for (int i = 0; i < CellText.length; i++) {
            add(new RiskMatrixCell(CellText[i],bgColor[i]));
          }
  
        
      

    }
        

    public class RiskMatrixCell extends javax.swing.JButton{

        public RiskMatrixCell(String CellText,Color bgColor) {
             setOpaque(true);
             setEnabled(false);
             setText(CellText);
             setBackground(bgColor);
           //addMouseListener(this);
        }
            
     }
       

       
    
}
