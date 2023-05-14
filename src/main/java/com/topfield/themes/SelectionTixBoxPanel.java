/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Murali
 */
public class SelectionTixBoxPanel extends JPanel{

    public SelectionTixBoxPanel(String [] data) {
        
        super(new GridLayout(0, 1));
        
        for (String string : data) {
             add(new JCheckBox(string));
        }
 
       
        
    }
    
    
    
}
