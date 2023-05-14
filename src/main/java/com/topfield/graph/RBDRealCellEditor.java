/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.graph;

import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import org.jgraph.JGraph;
import org.jgraph.graph.GraphCellEditor;

/**
 *
 * @author Murali
 */
public class RBDRealCellEditor extends AbstractCellEditor implements GraphCellEditor {
    
    
    JTextArea textArea = new JTextArea(10,10);
    JScrollPane editorComponent = new JScrollPane(textArea);

    public RBDRealCellEditor() {
        
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(border, 
          BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }
    

    @Override
    public Component getGraphCellEditorComponent(JGraph graph, Object value, boolean isSelected) {
       String stringValue = graph.convertValueToString(value);
		//delegate.setValue(stringValue);
                textArea.setText(stringValue);
		if (textArea instanceof JTextArea)
			((JTextArea) textArea).selectAll();
		return editorComponent;
    }
    
    @Override
    public Object getCellEditorValue() {
     return textArea.getText();
    }
    
}
