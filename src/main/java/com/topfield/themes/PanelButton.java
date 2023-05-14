/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import com.topfield.settings.FrameSettings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author Murali
 */
public class PanelButton extends JButton implements MouseListener{

    public PanelButton(String text) {
        super(text);
        setText(text);
        
         setBackground(FrameSettings.getButtonColor());
         setForeground(Color.WHITE);
         setBorder(BorderFactory.createEtchedBorder(0));
         setOpaque(true);
         //setBorder(BorderFactory.createLineBorder(FrameSettings.getButtonColor(), 1));
         setPreferredSize(new Dimension(150, 23));
         addMouseListener(this);
    }

   
    
    
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
      OnClickAction();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       //  setBackground(new Color(233, 247, 239));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
         OnClickAction();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        /*if(this.getModel().isPressed()){
         setBackground(new Color(107, 106, 104));
        }*/
        
        OffClickAction();
    }

   public void setTips(String tip){
   
       setToolTipText(tip);
   
   }

   public void OnClickAction(){
       setBackground(FrameSettings.getFrameColor());
       setForeground(FrameSettings.getButtonColor());
   }
   
   public void OffClickAction(){
       setBackground(FrameSettings.getButtonColor());
       setForeground(Color.WHITE);
   }


}
 

