/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.utilities.ImageProcessor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class SideViewTrain extends JPanel {
  
    private BufferedImage frontView;
    private String trainImage;
    private int width;
    private int height;
    private int x;
    private int y;
    

    public SideViewTrain(String trainImage, int width, int height, int x, int y) {
      this.trainImage = trainImage;
      this.width = width;
      this.height = height;
      this.x = x;
      this.y = y;
        
        //setBackground(Color.gray);
        
        JButton horver = new JButton("Horver");
        JLabel j = new JLabel();
        JPanel p = new JPanel();
        //p.setOpaque(false);
        p.setPreferredSize(new Dimension(100, 100));
        p.setToolTipText("fgggfdfgfgffdd");
        j.setToolTipText("yyyyyyyyyyy");
        //horver.setOpaque(false);
        horver.setVisible(true);
        horver.setToolTipText("Hi i am");
      //  add(horver);
       // add(j);
       // add(p);
  
    }
    

    
    /*
    public void paint(Graphics g){
        g.drawImage(frontView, 100, 100,frontView.getWidth(),frontView.getHeight(), this);
    
    }*/
   
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
       
       //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       setPreferredSize(new Dimension(getWidth(),130));
        
        Rectangle start = new Rectangle(150, 150, 200, 40);
        
       try {
            frontView = ImageIO.read(getClass().getResourceAsStream(trainImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
      
        
        g.drawImage(new ImageProcessor().scaleImage(frontView,frontView.getType(),width,height), x, y,width,height, this);
        
        g.setColor(Color.red); //1030 90
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString(" Train View", 500, 120);
        
//       ImageIcon companyName = new ImageIcon(getClass().getResource("/coachspecs/images/plus_2.png"));
//       ImageIcon scaledcompanyName = new ImageIcon(companyName.getImage().getScaledInstance(80, 80, 80));
//        
//       g.drawImage(scaledcompanyName.getImage(), 25, 200,frontView.getWidth(),frontView.getHeight(), this);
        
        //setPreferredSize(new Dimension(4000, 1000));
        
    }
    
    
    
}
