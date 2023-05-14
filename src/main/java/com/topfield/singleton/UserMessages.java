/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.singleton;

import javax.swing.JOptionPane;

/**
 *
 * @author Murali
 */
public class UserMessages {
    
    public static boolean notification =false;
    
    public static void setNotification(String heading,String message,int type){
    
       JOptionPane.showMessageDialog(null,message ,heading,type);  
    }
    
     public static void setNotificationUserPreference(String heading,String message,int type){
    
         if (notification) {
             JOptionPane.showMessageDialog(null,message ,heading,type);  
         }
       
    }
    
}
