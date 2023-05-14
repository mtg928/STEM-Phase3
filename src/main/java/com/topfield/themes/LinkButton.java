/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import com.topfield.main.InternalFrameDemo;
import com.topfield.settings.FrameSettings;
import com.topfield.view.panel.ToolBar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Murali
 */
public class LinkButton extends JButton implements MouseListener{
    
    private String text;

    public LinkButton(String text) {
        super(text);

         this.text = text;
         setBorder(BorderFactory.createRaisedBevelBorder());
         setPreferredSize(new Dimension(100, 20));
         setBackground(FrameSettings.getButtonColor());
         setForeground(Color.WHITE);
         addMouseListener(this);
         setOpaque(true);
    }

   
    
    
    @Override
    public void mouseClicked(MouseEvent e) {

      
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        ToolBar control = ((ToolBar)getParent());
        
       control.setSelected((TopPanelButton)e.getComponent()); 
       control.setOnclickColour((TopPanelButton)e.getComponent());
       control.setOnclickAction(text);
       InternalFrameDemo.mainFrame.getLeftBar().setUnSelectAll();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // setBackground(FrameSettings.getButtonColor());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      OnClickAction();
    }

    @Override
    public void mouseExited(MouseEvent e) {
       OffClickAction();

    }

   public void setTips(String tip){
   
       setToolTipText(tip);
   
   }
   
   
   public void OnClickAction(){
        
            setBackground(FrameSettings.getButtonSelColor());
            setForeground(FrameSettings.getButtonColor());
       
    }
    
    public void OffClickAction(){
    
        if ( ((ToolBar)getParent()).getSelected()== null || !((ToolBar)getParent()).getSelected().equals(this)  ) {
           setBackground(FrameSettings.getButtonColor());
           setForeground(Color.WHITE);
        }
    }

 
   


}
