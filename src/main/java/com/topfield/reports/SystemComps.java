/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.reports;

import com.topfield.modal.SubProductGroup;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Murali
 */
public class SystemComps extends JPanel{

    public SystemComps(String heading,Collection<SubProductGroup> subComp) {
        
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(heading));
        setBackground(new Color(149, 165, 166  ));
        JLabel headings = new JLabel();
        headings.setText(heading);
        
        JPanel j = new JPanel();
        j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
        //j.setPreferredSize(new Dimension(WIDTH, 200));
        j.setBackground(new Color(235, 245, 251));
        j.setAlignmentX(CENTER_ALIGNMENT);
        
        for (SubProductGroup subProductGroup : subComp) {
            j.add(new JCheckBox(subProductGroup.getSpgDescription()));
        }
        
        //add(headings,BorderLayout.NORTH);
        add(new JScrollPane(j),BorderLayout.CENTER);
        //j.setPreferredSize(new Dimension(WIDTH, 200));
        //add(new JScrollPane(j,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
       // add(headings,BorderLayout.SOUTH);
        
    }
    
    
    public String getTitle(){
    
      return ((TitledBorder)getBorder()).getTitle();
        
    }
    
    public void setTick(String subCom){
        JPanel panel= null;
        JCheckBox c = null;
        
        for (int i = 0; i < getComponentCount(); i++) {
            panel = (JPanel)(((JViewport)((JScrollPane)getComponent(i)).getComponent(0)).getComponent(0));
            
            for (int j = 0; j < panel.getComponentCount(); j++) {
                c = (JCheckBox)panel.getComponent(j);
                    if(c.getLabel().equals(subCom)){

                      c.setSelected(true);
                   }
                
            }

            
        }
    
    }
    
    public String getSelections(){
    String res="";
     JPanel panel= null;
        JCheckBox c = null;
    
      for (int i = 0; i < getComponentCount(); i++) {
            panel = (JPanel)(((JViewport)((JScrollPane)getComponent(i)).getComponent(0)).getComponent(0));
            
            for (int j = 0; j < panel.getComponentCount(); j++) {
                c = (JCheckBox)panel.getComponent(j);
                    if(c.isSelected()){

                      res=res+c.getText()+"#";
                   }
                
            }

            
        }
    
    
    return res;
    }
    
    
    
}
