/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.main;

import com.topfield.info.WelcomeInfo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Murali
 */
public class WelcomePage extends javax.swing.JPanel {
    private BufferedImage image;
    private BufferedImage imageLogo;
    private ImageIcon scaledcompanyName;

    public WelcomePage() {
        
        //setLayout(new BorderLayout());
        setLayout(new GridBagLayout());
        
        try {
            image = ImageIO.read(getClass().getResource("/coachspecs/images/background.png"));
            imageLogo = ImageIO.read(getClass().getResource("/coachspecs/images/logo_black_size2.png"));
            
            ImageIcon companyName = new ImageIcon(getClass().getResource("/coachspecs/images/logo_black_size2.png"));
            scaledcompanyName = new ImageIcon(companyName.getImage().getScaledInstance(660, 135, 135));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
        
       JLabel welcomeText = new JLabel("<html> <div style='text-align: center;'> Welcome to the Safety integrity level System!<br/>"
       + "Railway and Transportation  <br/> Engineering tool. </div> </html>", SwingConstants.CENTER);
       
        add(new WelcomeInfo());
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        int midW = getWidth()/3;
        int midH = getHeight()/6;
        
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        
        g.drawImage(imageLogo, midW, midH, 396, 81, this);
        
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new WelcomePage());     //  <--- add it here
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
