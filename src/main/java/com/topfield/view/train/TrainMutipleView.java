/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.main.InternalFrameDemo;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Murali
 */
public class TrainMutipleView extends JPanel {
   
    
    private Canvas canvas;
    private JPanel topPanel;
    private JPanel middlePanel = new JPanel();
    private JPanel bottomPanel;
    
    JButton frontView = new JButton("Front View");
    JButton topView = new JButton("Top View");
    JButton sideView = new JButton("Side View");
    JButton bottomView = new JButton("Bottom View");

    public TrainMutipleView() {
        
        setLayout (new BorderLayout());
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(300,300));
        canvas.setMaximumSize(new Dimension(300,300));
        canvas.setMinimumSize(new Dimension(300,300));
        canvas.setBackground(Color.red);
        //canvas.setLocation(500, 900);
        //add(canvas);
        
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.lightGray);
        //topPanel.setPreferredSize(new Dimension(getWidth(), 100));
                
        frontView.setToolTipText("<html>Manchester<br>53.4800° N, 2.2400° W</html>");
        
        topPanel.add(frontView);
        topPanel.add(topView);
        topPanel.add(sideView);
        topPanel.add(bottomView);
        
        middlePanel.setLayout(new CardLayout());
        middlePanel.add(new JScrollPane(new SideViewTrain("/coachspecs/images/fullTrain.png",1000,125,120,12))); //level2_2 copy

        bottomPanel = new SideViewComponents();
        
        
        
        add(topPanel,BorderLayout.PAGE_START);
        add(middlePanel,BorderLayout.CENTER);
        add(new JScrollPane(bottomPanel),BorderLayout.PAGE_END);
        
        
        buttionClicks();
        
    }
    
    
    
   
    public void buttionClicks(){
        
        
        frontView.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){  
            //this.showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green."); 
            //showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green.");
            middlePanel.removeAll();
            middlePanel.add(new JScrollPane(new SideViewTrain("/coachspecs/images/Train_Courtesy.jpg",900,200,100,0)));
           //add(new JScrollPane(new SideViewTrain("/coachspecs/images/faceView.png",65,100,525,10)),BorderLayout.CENTER);
           //add(middlePanel,BorderLayout.CENTER);
           InternalFrameDemo.contentPanel.revalidate();
           InternalFrameDemo.contentPanel.repaint();
        }  
       });  
        
          topView.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){  
           //showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green."); 
           // showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green.");
            middlePanel.removeAll();
            middlePanel.add(new JScrollPane(new SideViewTrain("/coachspecs/images/level2_2 copy.png",1000,70,90,20)));
           InternalFrameDemo.contentPanel.revalidate();
           InternalFrameDemo.contentPanel.repaint();
        }  
       });  
        
           sideView.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){  
           //showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green."); 
           // showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green.");
            middlePanel.removeAll();
            middlePanel.add(new JScrollPane(new SideViewTrain("/coachspecs/images/train.png",1030,90,25,20)));
           InternalFrameDemo.contentPanel.revalidate();
           InternalFrameDemo.contentPanel.repaint();
        }  
       });  
           
           
          bottomView.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){  
           //showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green."); 
           // showMessageDialog(MainFrame.mainFrame, "Eggs are not supposed to be green.");
            middlePanel.removeAll();
            middlePanel.add(new JScrollPane(new SideViewTrain("/coachspecs/images/bottom.png",1030,90,25,20)));
           InternalFrameDemo.contentPanel.revalidate();
           InternalFrameDemo.contentPanel.repaint();
        }  
       });  
        
        
        
    }

    
}
