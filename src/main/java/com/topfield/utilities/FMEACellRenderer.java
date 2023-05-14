/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

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
public class FMEACellRenderer extends DefaultTableCellRenderer {
    
   public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
   {
   
       setBackground(getColour(table.getValueAt(row, 2)+""));
  
      return super.getTableCellRendererComponent(table, value, isSelected,
                                                 hasFocus, row, column);
   }
   
   public Color getColour(String sunSysCode){
   
   Color col= null;
   
   
       if (FMEAServices.getSystemLevels(sunSysCode)==1) {
           col = FrameSettings.getButtonSelColor();
       }else if (FMEAServices.getSystemLevels(sunSysCode)==2) {
           col = new Color(153, 163, 164);
       }else if ( FMEAServices.getSystemLevels(sunSysCode)==3 ) {
           col = new Color(215, 219, 221);
       }else {
           col = Color.white;
       }
   
   return col;
   }
   
  
   
   
}


