/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;


import javax.swing.*;
import java.net.URL;

public class GifLoader {
    private JFrame frame;
    private JLabel label;
    private Icon icon;
    private URL url;

    public GifLoader() {
        initGUI();
        addWindowProperties();
    }

    private void initGUI() {
        frame = new JFrame("Loader"); // windows title is Loader, change
                                      // it if you don't like it!

        try {
            url = new URL("http://i.giphy.com/xT0GqdXpoKAazgF8t2.gif");
        } catch (Exception e) { // do nothing or just print a message }

        icon = new ImageIcon(url);
        label = new JLabel(icon);
    }
    }
    
    private void addWindowProperties() {
        frame.add(label);
        frame.pack(); // you don't know the size of the gif, so pack
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // you can also use JFrame.EXIT_ON_CLOSE
    }

    public void loadApplication() {
        frame.setVisible(true);
    }
    
    
     public static void main(String[] args) {
        GifLoader a = new GifLoader();
        a.loadApplication();
    }
}

