/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.themes;

import com.topfield.settings.FrameSettings;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

/**
 *
 * @author Murali
 */
public class MenuBarTheme extends JMenuBar {
    private Color bgColor=FrameSettings.getFrameColor();
    private Color fgColor=FrameSettings.getFontColor();

    public MenuBarTheme() {
        
         setForeground(fgColor);
         setOpaque(true);
         
         UIManager.put("MenuItem.background", bgColor);
         UIManager.put("MenuItem.foreground", fgColor);
         UIManager.put("MenuItem.opaque", true);
    }
    
    public void setColor(Color color) {
        bgColor=color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

    }
}
