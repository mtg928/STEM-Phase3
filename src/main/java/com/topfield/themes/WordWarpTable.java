/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.settings.FrameSettings;
import com.topfield.utilities.GroupableTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class WordWarpTable extends JTable {
    
   private static JComboBox comboBox = new JComboBox();



    public WordWarpTable(int std) {
        super();

        getTableHeader().setOpaque(true);
        setOpaque(true);
        setDefaultRenderer(String.class, new WordWrapCellRenderer(std));
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setReorderingAllowed(true);
        getTableHeader().setBackground(FrameSettings.getButtonColor());
        getTableHeader().setForeground(Color.BLACK);
        getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));

        setCellSelectionEnabled(true);
        
        setBorder(new EtchedBorder(EtchedBorder.RAISED));
        setGridColor(Color.LIGHT_GRAY);
        
        getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                restoreRowHeight();
            }

        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                restoreRowHeight();
            }
        });
        
        comboBox.removeAllItems();
        if (std ==8 || std ==13 ) {
            comboBox.addItem("Category I (Catastrophic)");
            comboBox.addItem("Category II (Critical)");
            comboBox.addItem("Category III (Marginal)");
            comboBox.addItem("Category IV (Minor)");
        } else {
            comboBox.addItem("1");
            comboBox.addItem("2");
            comboBox.addItem("3");
            comboBox.addItem("4");
        }
        

    }

    protected JTableHeader createDefaultTableHeader() {
        return new GroupableTableHeader(columnModel);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column <= 3 || column >= getColumnCount()-2) {
            return false;
        } else {
            return true;
        }
    }

    private void restoreRowHeight() {
        if (getModel() == null) // causing NPE
        {
            return;
        }

        for (int row = 0; row < getRowCount(); row++) {
            int heightOfTheTallestComponent = -1;
            for (int column = 0; column < getColumnCount(); column++) {
                Component c = prepareRenderer(getDefaultRenderer(String.class), row, column);
                if (c.getPreferredSize().height > heightOfTheTallestComponent) {
                    heightOfTheTallestComponent = c.getPreferredSize().height;
                }
            }
            setRowHeight(row, heightOfTheTallestComponent);
        }
    }

    public static class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
        int std=0;
        private WordWrapCellRenderer(int std) {
            setLineWrap(true);
            setWrapStyleWord(true);
            this.std = std;
        }

        @Override
        public WordWrapCellRenderer getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ?"" :value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) < getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            
             setBackground(getColour(row,column,table,isSelected));
             
             
            
            return this;
        }

        public Color getColour(int rowId,int column,JTable table,boolean isSelected) {

            Color col = null;

            if (isSelected) {
                
               col =new Color(174, 214, 241);
                
            }else{
                
                if (FMEAServices.getSystemLevelsNew(rowId,table) == 1) {
                col = new Color(153, 163, 164);
                } else if (FMEAServices.getSystemLevelsNew(rowId,table) == 2) {
                    col = new Color(215, 219, 221); 
                } else if (FMEAServices.getSystemLevelsNew(rowId,table) == 3) {
                    col = FrameSettings.getButtonSelColor();
                } else {
                      col = Color.white;
                      if (column ==std) {
                         table.getColumnModel().getColumn(column).setCellEditor(new DefaultCellEditor(comboBox));
                      }
                }            
            
            }
  
            

            return col;
        }
    }

}
