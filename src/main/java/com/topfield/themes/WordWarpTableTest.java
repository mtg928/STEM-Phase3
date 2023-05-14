/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Murali
 */
public class WordWarpTableTest extends JPanel{
    
    
     private static final String[] COLUMNS = { "SomeColumn", "OtherColumn", "OtherOtherColumn" };

    public WordWarpTableTest() {
        setLayout(new BorderLayout());
        
        WordWarpTable table = new WordWarpTable(13);
        
        Object[][] data = new Object[5][3];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = "Row: " + i + " - " + loremIpsum();
            data[i][1] = "Row: " + i + " Maybe something small?";
            data[i][2] = "Row: " + i + "___" + new StringBuilder(loremIpsum()).reverse().toString();
        }
        
        table.setModel(new DefaultTableModel(data, COLUMNS) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        });
        
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    
    
     private String loremIpsum() {
        return "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                + " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,"
                + " when an unknown printer took a galley of type and scrambled it to make a type specimen book."
                + " It has survived not only five centuries, but also the leap into electronic typesetting, "
                + "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset"
                + " sheets containing Lorem Ipsum passages, and more recently with desktop publishing software"
                + " like Aldus PageMaker including versions of Lorem Ipsum";
     }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test");
            frame.setLayout(new BorderLayout());

            frame.add(new WordWarpTableTest());
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    
}
