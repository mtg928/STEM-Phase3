/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import com.topfield.user.UserInfo;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Murali
 */
public class WineCellRenderer extends DefaultTableCellRenderer {
    
   public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column)
   {
   
  
      if (table.getValueAt(row, 0).equals(UserInfo.getInstance().getProjectNo()+"")) {
         setBackground(Color.cyan);
      }
      else {
         setBackground(Color.white);
         //setBackground(Color.red);
      }
  
      return super.getTableCellRendererComponent(table, value, isSelected,
                                                 hasFocus, row, column);
   }
}
