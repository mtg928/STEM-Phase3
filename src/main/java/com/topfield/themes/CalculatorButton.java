/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import com.topfield.main.InternalFrameDemo;
import com.topfield.settings.FrameSettings;
import com.topfield.view.panel.LeftPanel;
import com.topfield.view.panel.ToolBar;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.IconUIResource;

/**
 *
 * @author Murali
 */
public class CalculatorButton extends JPanel{
    
    private JButton button = new JButton();
    private String caption;

    public CalculatorButton(String caption,String captionExt) {
        
      setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
      this.caption  = caption;
      
      
       ImageIcon add = new ImageIcon(getClass().getResource(FrameSettings.getImagePath()+caption+".png"));
       ImageIcon scaledadd = new ImageIcon(add.getImage().getScaledInstance(46, 46, 46));

       button.setIcon(scaledadd);
       button.setBorder(BorderFactory.createEmptyBorder());
       button.setContentAreaFilled(false);
       button.setBackground(FrameSettings.getFrameColor());
       button.setOpaque(true);
       //button.setText(caption);
       //button.setVerticalTextPosition(SwingConstants.BOTTOM);
       //button.setHorizontalTextPosition(SwingConstants.CENTER);
       //button.setFont(new Font("Arial", Font.PLAIN, 20));
       //button.setForeground(Color.WHITE);
       //button.setHorizontalTextPosition(SwingConstants.CENTER);
       button.setToolTipText(captionExt);
       
       button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
             LeftPanel leftPanel = ((LeftPanel)getParent());
             
                leftPanel.setSelected((CalculatorButton)((JButton)evt.getComponent()).getParent());
                leftPanel.setOnclickColour((CalculatorButton)((JButton)evt.getComponent()).getParent());
                InternalFrameDemo.mainFrame.getToolBar().setUnSelectAll();
                leftPanel.setOnclickAction(caption);
            }
           
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                    OnClickAction();
                    
                    //button.setBackground(FrameSettings.getButtonColor());
                    
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                    OffClickAction();
                   
                   //button.setBackground(FrameSettings.getFrameColor());
                    
                    
            }
        });

       
        add(button);
        
    }
    
    public JButton getButton(){
    
       return button;
    
    }
    
    public void OnClickAction(){
    
        ImageIcon companyName = new ImageIcon(getClass().getResource(FrameSettings.getImageSelPath()+caption+".png"));
        ImageIcon scaledcompanyName = new ImageIcon(companyName.getImage().getScaledInstance(46, 46, 46));
        button.setIcon(scaledcompanyName);
        
    
    }
    
    public void OffClickAction(){
    
       if ( ((LeftPanel)getParent()).getSelected()== null || !((LeftPanel)getParent()).getSelected().equals(this)  ) {
        ImageIcon companyName = new ImageIcon(getClass().getResource(FrameSettings.getImagePath()+caption+".png"));
        ImageIcon scaledcompanyName = new ImageIcon(companyName.getImage().getScaledInstance(46, 46, 46));
        button.setIcon(scaledcompanyName);
       }
    }
    
    public void OffALLClickAction(){
    
       
        ImageIcon companyName = new ImageIcon(getClass().getResource(FrameSettings.getImagePath()+caption+".png"));
        ImageIcon scaledcompanyName = new ImageIcon(companyName.getImage().getScaledInstance(46, 46, 46));
        button.setIcon(scaledcompanyName);
       
    }
    
    
}
