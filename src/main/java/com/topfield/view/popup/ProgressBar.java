/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.popup;

import com.topfield.main.InternalFrameDemo;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

public class ProgressBar extends JFrame {
    
    private JProgressBar progressBar;
    private String title;

    public ProgressBar() {
        
        setTitle("Please wait");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setResizable(false);
        setAlwaysOnTop(true);
        setLocation((int) (InternalFrameDemo.mainFrame.getWidth()*(0.45)), (int) InternalFrameDemo.mainFrame.getHeight()/2 );
        
        initiate();
        
        getContentPane().add(progressBar, BorderLayout.NORTH);
        setVisible(true);
    }
    
    public void initiate(){
    
        title="Progressing...";
        
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createTitledBorder(title));
    
    }

    public void setProgressTitle(String title) {
        ((TitledBorder)progressBar.getBorder()).setTitle(title);
    }
    
    public void setPercentage(int percent) {
        progressBar.setValue(percent);
        //value =percent;
        
        if (progressBar.getMaximum()==percent) {
            dispose();
        }
    }
    
    public void setBountry(int min,int max) {
        
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
    }
    
  public static void main(String args[]) {
        ProgressBar progress =  new ProgressBar();
        progress.setBountry(0, 100000);
       
       for (int i = 0; i <=100000 ; i++) {
                            System.out.println("HII      bnjdnsvlfsmvml  dslf;lkf");
                            
                progress.setPercentage(i);            
       }
       
       
  }


  

}

