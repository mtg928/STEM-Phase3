/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

/*
 * (swing1.1beta3)
 * 
 */

import com.topfield.settings.FrameSettings;
import com.topfield.utilities.ColumnGroup;
import com.topfield.utilities.GroupableTableHeaderUI;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;

 

/**
  * GroupableTableHeader
  *
  * @version 1.0 10/20/98
  * @author Nobuo Tamemasa
  */

public class GroupableTableHeader extends JTableHeader {
  private static final String uiClassID = "GroupableTableHeaderUI";
  protected Vector columnGroups = null;
  String[] toolTips;
    
  public GroupableTableHeader(TableColumnModel model) {
    super(model);
    
    setOpaque(true);
    setForeground(Color.BLACK);
    setFont(new Font ("Serif", Font.BOLD, 14));
    setBackground(FrameSettings.getButtonColor());
    setUI(new GroupableTableHeaderUI());
    setReorderingAllowed(false);
  }
  
  public void updateUI(){
   setUI(new GroupableTableHeaderUI());
  }
  
  public void setReorderingAllowed(boolean b) {
    reorderingAllowed = false;
  }
    
  public void addColumnGroup(ColumnGroup g) {
    if (columnGroups == null) {
      columnGroups = new Vector();
    }
    columnGroups.addElement(g);
  }

  public Enumeration getColumnGroups(TableColumn col) {
    if (columnGroups == null) return null;
    Enumeration e = columnGroups.elements();
    while (e.hasMoreElements()) {
      ColumnGroup cGroup = (ColumnGroup)e.nextElement();
      Vector v_ret = (Vector)cGroup.getColumnGroups(col,new Vector());
      if (v_ret != null) { 
  return v_ret.elements();
      }
    }
    return null;
  }
  
  public void setColumnMargin() {
    if (columnGroups == null) return;
    int columnMargin = getColumnModel().getColumnMargin();
    Enumeration e = columnGroups.elements();
    while (e.hasMoreElements()) {
      ColumnGroup cGroup = (ColumnGroup)e.nextElement();
      cGroup.setColumnMargin(columnMargin);
    }
  }
  
  public String getToolTipText(MouseEvent e) {
      int col = columnAtPoint(e.getPoint());
      int modelCol = getTable().convertColumnIndexToModel(col);
      String retStr;
      try {
         retStr = toolTips[modelCol];
      } catch (NullPointerException ex) {
         retStr = "";
      } catch (ArrayIndexOutOfBoundsException ex) {
         retStr = "";
      }
      if (retStr.length() < 1) {
         retStr = super.getToolTipText(e);
      }
      return retStr;
   }
   public void setToolTipStrings(String[] toolTips) {
      this.toolTips = toolTips;
   }
  
}