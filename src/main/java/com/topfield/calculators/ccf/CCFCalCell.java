/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.ccf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Murali
 */
public class CCFCalCell extends JPanel{

    public CCFCalCell() {
        
        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel heading = new JLabel();
        heading.setFont(new Font("arial", Font.BOLD, 20));
        heading.setText("heading");
        
        JPanel ccfElement  = new JPanel();
        ccfElement.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel item = new JLabel();
        item.setText("Element");
        item.setPreferredSize(new Dimension(200,30 ));
        item.setBackground(Color.GREEN);
        item.setOpaque(true);
        
        JComboBox selection = new JComboBox();
        selection.addItem("Test");
        
        
        ccfElement.add(item);
        ccfElement.add(selection);
        
        add(heading);
        add(ccfElement);
    }
    
    
    
    
}
