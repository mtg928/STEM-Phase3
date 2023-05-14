/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/*from   w w w. j a  v a  2 s  .c om*/
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

public class ListCheckBox {

  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JList list = new JList(new CheckListItem[] { new CheckListItem("apple"),
        new CheckListItem("orange"), new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        new CheckListItem("mango"),
        
        new CheckListItem("paw paw"), new CheckListItem("banana") });
    list.setCellRenderer(new CheckListRenderer());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent event) {
        JList list = (JList) event.getSource();
        int index = list.locationToIndex(event.getPoint());// Get index of item
                                                           // clicked
        CheckListItem item = (CheckListItem) list.getModel()
            .getElementAt(index);
        item.setSelected(!item.isSelected()); // Toggle selected state
        list.repaint(list.getCellBounds(index, index));// Repaint cell
      }
    });
    frame.getContentPane().add(new JScrollPane(list));
    frame.pack();
    frame.setVisible(true);
  }
}

class CheckListItem {

  private String label;
  private boolean isSelected = false;

  public CheckListItem(String label) {
    this.label = label;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  @Override
  public String toString() {
    return label;
  }
}

class CheckListRenderer extends JCheckBox implements ListCellRenderer {
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean hasFocus) {
    setEnabled(list.isEnabled());
    setSelected(((CheckListItem) value).isSelected());
    setFont(list.getFont());
    setBackground(list.getBackground());
    setForeground(list.getForeground());
    setText(value.toString());
    return this;
  }
}
