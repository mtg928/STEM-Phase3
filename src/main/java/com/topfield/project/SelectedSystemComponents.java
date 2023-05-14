/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.project;

import com.topfield.dao.MainProductGroupDao;
import com.topfield.dao.SubProductGroupDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.daoImpl.SubProductGroupDaoImpl;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.SubProductGroup;
import com.topfield.reports.SystemComps;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Murali
 */
public class SelectedSystemComponents extends JPanel{
    
    private MainProductGroupDao mpgDao = new MainProductGroupDaoImpl();
    private SubProductGroupDao spgDao = new SubProductGroupDaoImpl();
    
    public SelectedSystemComponents()
    {   
     JPanel com=null;
        //Setting up Flow Layout
        setLayout(new GridLayout( mpgDao.getAllMPG().size()/4,4));
        
        setAlignmentY(TOP_ALIGNMENT);
        //setBackground(Color.white);
        
        for (MainProductGroup mainProductGroup : mpgDao.getAllMPG()) {
            com = new SystemComps(mainProductGroup.getMpgDescription(),mainProductGroup.getSubProductGroupCollection());
             // com = new SytemsComp(mainProductGroup.getMpgDescription(),mainProductGroup.getSubProductGroupCollection());
              /*com.setBackground(new Color(208, 211, 212));
              com.setPreferredSize(new Dimension(180, HEIGHT));
              com.setLayout(new BoxLayout(com, BoxLayout.Y_AXIS));
              JLabel j =new JLabel(mainProductGroup.getMpgDescription());
               j.setOpaque(true);
              j.setBackground(Color.red);
              com.add(j);
              com.setAlignmentX(LEFT_ALIGNMENT);
               for (SubProductGroup subProductGroup : mainProductGroup.getSubProductGroupCollection()) {      
                         com.add(new Checkbox(subProductGroup.getSpgDescription()));
               }*/
             add(com);
         }
        
        
        

        
      
    }
    
    
    public void SetData(String systems){
     SystemComps com=null;
     String select[] = systems.split("\\#");
     
        for (String string : select) {
            System.out.println(""+string);
            
        for (int i = 0; i < getComponentCount(); i++) {
            com = (SystemComps)getComponent(i);
            
            //System.out.println("Title - "+com.getTitle());
            com.setTick(string);
        }
            
        }
     
        //System.out.println(" size height"+ getComponentCount()+ " width -"+getSize().width);
        
        
    
    
    
    }
    
   public String getSelections(){
     String selections ="";
     SystemComps com=null;
     
     for (int i = 0; i < getComponentCount(); i++) {
            com = (SystemComps)getComponent(i);
            selections = selections+com.getSelections();
        }
     
    
    return selections;
   }
    
    
}
