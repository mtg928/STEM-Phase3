/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Murali
 */
public class SideViewComponents extends JPanel{
    
    private JPanel mainComponents;
    private JPanel subComponents;
    
     

    public SideViewComponents() {
        
        String componentInfo;
        
        setLayout(new BorderLayout());
        //setLayout(new FlowLayout(FlowLayout.LEFT));
        //setBackground(Color.BLUE);
       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
       setPreferredSize(new Dimension(getWidth(),screenSize.height*55/100 ));
       
       componentInfo = "There are various types of train that are designed for\n" +
                        " particular purposes. A train can consist of a combination of \n" +
                        " one or more locomotives and attached railroad cars, or a \n" +
                        " self-propelled multiple unit, or occasionally a single or \n" +
                        "  articulated powered coach called a railcar. Special kinds  \n" +
                        "of train running on corresponding purpose-built \"railways\" \n" +
                        "are monorails, high-speed railways,  maglev, atmospheric railways, \n" +
                        "rubber-tired underground, funicular and cog railways."; 
       
       
        
        mainComponents = new MainComponents("Train View 1","/coachspecs/images/faceView.png",componentInfo);
        //mainComponents = new JPanel();
        mainComponents.setBackground(Color.lightGray);
        
        Border border = mainComponents.getBorder();
        Border margin = new EmptyBorder(10,10,10,24);
        mainComponents.setBorder(new CompoundBorder(border, margin));
        
        
        
        
        subComponents = new BreakDownComponents();
        //subComponents = new JPanel();
        //subComponents.setBackground(Color.MAGENTA);
        
       add(new JScrollPane(mainComponents),BorderLayout.WEST);
       add(new JScrollPane(subComponents),BorderLayout.CENTER);
       
       //add(new JScrollPane(mainComponents));
       //add(new JScrollPane(subComponents));
        
    }
    
    
    
}
