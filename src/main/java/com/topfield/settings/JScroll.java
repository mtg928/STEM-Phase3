/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author Murali
 */
public class JScroll extends JScrollPane{

    public JScroll(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        scrollSettings();
    }

    public JScroll(Component view) {
        super(view);
        scrollSettings();
    }

    public JScroll(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        scrollSettings();
    }

    public JScroll() {
        scrollSettings( );
    }
    
    public void scrollSettings(){
    
     
       
        getVerticalScrollBar().setPreferredSize(new Dimension(12, Integer.MAX_VALUE));
        getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override 
            protected void configureScrollBarColors(){
                this.thumbColor = FrameSettings.getButtonColor();
            }
        });
       
        getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE,12));
        getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override 
            protected void configureScrollBarColors(){
                this.thumbColor = FrameSettings.getButtonColor();
            }
        });
        
        setOpaque(false);
    
    }
    
    
    
    
}
