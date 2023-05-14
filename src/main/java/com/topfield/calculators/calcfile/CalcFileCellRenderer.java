/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.settings.FrameSettings;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Murali
 */
public class CalcFileCellRenderer extends DefaultTableCellRenderer {
    
   private CalcCurd calcCurd = new CalcCurd();
    
   public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
   {
   
       setOpaque(true);
       setBackground(getColour(table,row,column));
       
  
      return super.getTableCellRendererComponent(table, value, isSelected,
                                                 hasFocus, row, column);
   }
   
   
   public Color getColour(JTable jCalc,int row,int colum){
   
   Color col= null;
   
       if (calcCurd.isFunction(jCalc,row)) {
           if (colum ==0 ||colum ==1 ||colum ==4 ||colum ==5 || colum == 10 || colum == 11) {
             col = new Color(191, 201, 202);
           }else{
             col = FrameSettings.getButtonSelColor();
           }  
       } else {
           if (colum ==0 ||colum ==1 ||colum ==4 ||colum ==5 || colum == 10 || colum == 11) {
              col = new Color(240, 243, 244);
            } else {
             col = Color.white;
           }
             
       }
       
  
   
      /* if (FMEAServices.getSystemLevels(sunSysCode)==1) {
           col = FrameSettings.getButtonSelColor();
       }else if (FMEAServices.getSystemLevels(sunSysCode)==2) {
           col = new Color(153, 163, 164);
       }else if ( FMEAServices.getSystemLevels(sunSysCode)==3 ) {
           col = new Color(215, 219, 221);
       }else {
           col = Color.white;
       }*/
   
   return col;
   }
   
  
   
   
}
