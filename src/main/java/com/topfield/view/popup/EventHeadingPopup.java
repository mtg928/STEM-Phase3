/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 *
 * @author Murali
 */
public class EventHeadingPopup extends JPanel{
    
 private int eventCount;
 private EventCaptionElement[] eventEle; 

    public EventHeadingPopup(int eventCount) {
        super();
        
            setLayout(new BorderLayout());
            
            this.eventCount = eventCount;
            eventEle= new EventCaptionElement[eventCount];
        
            JPanel panel = new JPanel();
            EventCaptionElement element;

            panel.setBounds(61, 11, 81, 140);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            JLabel label = new JLabel();
            label.setText("<html><h1>Heading</h1>"); //<br/>Some other text here.</html>
            label.setFont(new Font("Myriad Pro",Font.PLAIN,15));
            add(label,BorderLayout.NORTH);
           

            for (int i = 0; i < eventCount; i++) {
               element = new EventCaptionElement(i);
               panel.add(element);
               eventEle[i] = element;
            }
            
            
            JButton showDialogButton = new JButton("Text Button");
            showDialogButton.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                  System.out.println(getLevelCaptions().toString());
              }
            });
            
           // panel.add(showDialogButton);

            add(panel,BorderLayout.CENTER);
    }


    public List<String> getLevelCaptions(){
     List<String> levelHeadings = new ArrayList<String>();
     
        for (int i = 0; i < eventEle.length; i++) {
            levelHeadings.add(eventEle[i].getCaptions());
        }
    
     return levelHeadings;
    }

    public void setLevelCaptions(List<String> levelHeadings){

        int countLev = Math.min(levelHeadings.size(), eventEle.length);
        
        for (int i = 0; i < countLev; i++) {
           eventEle[i].setCaptions(levelHeadings.get(i));
        }
    
    }
    

    public static void main(String[] args) {
        
                
        JFrame frame = new JFrame("DropDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 

        frame.setContentPane(new EventHeadingPopup(5));
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    
}
