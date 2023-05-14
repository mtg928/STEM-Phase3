/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.project;

import com.topfield.user.UserInfo;
import com.topfield.dao.TclProjectsDao;
import com.topfield.daoImpl.TclProjectsDaoImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Murali
 */
public class ShowSelectedItemsAsList extends JPanel{
    
    JPanel jp = new JPanel();
    
    public ShowSelectedItemsAsList(String [] data) {
        
        JButton button = new JButton("Select All    ");
        //jp.setPreferredSize(new Dimension(800,400));
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        
        for (String comp : data) {
            jp.add(new JCheckBox(comp));
        }
        
        JScrollPane js = new JScrollPane(jp);
        js.setPreferredSize(new Dimension(250,350));
        add(js);
        add(button);
        
   /*     setPreferredSize(new Dimension(300, 200));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
         JButton button = new JButton("Select All");
        
        for (String comp : proDao.findById(UserInfo.getInstance().getProjectNo()).getSystemScope().split("\\#")) {
            panel.add(new JCheckBox(comp));
        }
        //panel.setPreferredSize(new Dimension(300, 200));
    /*JCheckBox check = new JCheckBox("Anchovies");
    panel.add(check);
    check = new JCheckBox("Garlic");
    panel.add(check);
    check = new JCheckBox("Onions");
    panel.add(check);
    check = new JCheckBox("Pepperoni");
    panel.add(check);
    check = new JCheckBox("Spinach");
    panel.add(check);*/
    
      button.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
             selectAll(button);
          }
        });
        
        
    
        //add(new JScrollPane(panel));
     /*   add(new JScrollPane(panel), BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        revalidate();
        repaint();*/
    }
    
    public String[] getSelectedComponents(){
     List<String> res = new ArrayList<>(); 
     
     for( int i=0; i<jp.getComponentCount(); i++ ) {
        JCheckBox checkBox = (JCheckBox)jp.getComponent( i );
        if( checkBox.isSelected() ) {
           String option = checkBox.getText();
           res.add(option);
           // System.out.println(" option -"+option );
        }
      }
    
    
    return res.toArray(new String[res.size()]);
    }
    
    public void selectAll(JButton button){
        
     if(button.getText().equals("Select All    ")){
        
      for( int i=0; i<jp.getComponentCount(); i++ ) {
        JCheckBox checkBox = (JCheckBox)jp.getComponent( i );
         checkBox.setSelected(true);
         button.setText("Deselect All");
      }
     }else if(button.getText().equals("Deselect All")){
     
       for( int i=0; i<jp.getComponentCount(); i++ ) {
        JCheckBox checkBox = (JCheckBox)jp.getComponent( i );
         checkBox.setSelected(false);
         button.setText("Select All    ");
     }
     
     }
     
    
    }
    
    
}
