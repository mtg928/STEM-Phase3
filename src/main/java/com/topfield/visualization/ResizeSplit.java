/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class ResizeSplit {
    
    
  public static void main(String args[]) {
    String title = "Resize Split";

    final JFrame vFrame = new JFrame(title);
    vFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JButton topButton = new JButton("Top");
    JButton bottomButton = new JButton("Bottom");
    
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setTopComponent(topButton);
    splitPane.setBottomComponent(bottomButton);
    splitPane.setResizeWeight(.5d);
    
    ActionListener oneActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        splitPane.setResizeWeight(1.0);
        vFrame.setSize(300, 250);
        vFrame.validate();
      }
    };
    bottomButton.addActionListener(oneActionListener);

    ActionListener anotherActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        splitPane.setResizeWeight(0.5);
        vFrame.setSize(300, 250);
        vFrame.validate();
      }
    };
    topButton.addActionListener(anotherActionListener);
    
    
    vFrame.getContentPane().add(splitPane, BorderLayout.CENTER);
    vFrame.setSize(300, 150);
    vFrame.setVisible(true);

  }
}
