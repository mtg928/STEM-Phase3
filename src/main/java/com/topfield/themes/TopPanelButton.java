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
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Murali
 */
public class TopPanelButton extends JButton implements MouseListener{
    
    private String text;

    public TopPanelButton(String text, JPanel action) {
        super(text);
        //setText(text);
         //setContentAreaFilled(false);
         //setFocusPainted(false);
         //setBackground(FrameSettings.getButtonColor());
         //setForeground(Color.WHITE);
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
      //setBackground(FrameSettings.getButtonSelectionColor());
      
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

 /*  @Override
   protected void paintComponent(Graphics g) {
            final Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(
                    new Point(0, 0), 
                    Color.WHITE, 
                    new Point(0, getHeight()), 
                    FrameSettings.getButtonColor()));
            g2.fillRect(0, 0, getWidth(), getHeight());
           // g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10f, 10f));
            //g2.fill(new RoundRectangle2D.Float(2, 2, getWidth() - 1 - 2 * 2,getHeight() - 1 - 2 * 2, 10f, 10f));
            g2.dispose();

            super.paintComponent(g);
    }*/

   


}
 
