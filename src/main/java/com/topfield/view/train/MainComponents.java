/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Murali
 */
public class MainComponents extends JPanel {
    
     public MainComponents(String caption,String imageUrl,String componentInfo) {
         setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
         
         //setPreferredSize(new Dimension(300, getHeight()));
         
         //add(Box.createRigidArea(new Dimension(50,20)));
         add(new MainComponent(caption,imageUrl,componentInfo));
         add(Box.createRigidArea(new Dimension(5,10)));
         add(new MainComponent(caption,imageUrl,componentInfo));
         add(Box.createRigidArea(new Dimension(5,10)));
         add(new MainComponent(caption,imageUrl,componentInfo));
         add(Box.createRigidArea(new Dimension(5,10)));
         add(new MainComponent(caption,imageUrl,componentInfo));
         add(Box.createRigidArea(new Dimension(5,10)));
         add(new MainComponent(caption,imageUrl,componentInfo));
         add(Box.createRigidArea(new Dimension(5,10)));
         add(new MainComponent(caption,imageUrl,componentInfo));
     }
}
