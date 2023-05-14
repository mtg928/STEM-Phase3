/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolTip;
public class ToolTipBackgroundForeground {
    
  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JButton b = new JButton("Hello, World") {
      public JToolTip createToolTip() {
        JToolTip tip = super.createToolTip();
        tip.setBackground(Color.YELLOW);
        tip.setForeground(Color.RED);
        return tip;
      }
    };
    frame.add(b,"Center");
    b.setToolTipText("asdf");
    frame.setSize(300, 200);
    frame.setVisible(true);
  }
}
