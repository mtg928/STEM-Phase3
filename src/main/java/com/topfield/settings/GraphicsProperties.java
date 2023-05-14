/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.settings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Murali
 */
public class GraphicsProperties {
    
    //private static InputStream input;
    //private static Properties prop;
     public static final String path = System.getProperty("user.home")+"\\AppData\\STEMS\\";
     private static final String userPreferenceFile = "/preference.properties";

    public static void SetProperties(String theme,String button,String notification){
        
        

       try (OutputStream output = new FileOutputStream(path+"config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("Theme", theme);
            prop.setProperty("Button", button);
            prop.setProperty("Notification", notification);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }
    
    
    public static String getThemeProperties(){
      String res="";
          try (InputStream input = new FileInputStream(path+"config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                JOptionPane.showMessageDialog(null,"Sorry, unable to find config.properties");
                return "";
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            res =prop.getProperty("Theme");
            System.out.println(prop.getProperty("Theme"));
            System.out.println(prop.getProperty("Button"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

          return res;
    }
    
    
    public static String getButtonProperties(){
       String res="";
          try (InputStream input = new FileInputStream(path+"config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                JOptionPane.showMessageDialog(null,"Sorry, unable to find config.properties");
                return "";
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            res =prop.getProperty("Button");
            //System.out.println(prop.getProperty("Theme"));
            //System.out.println(prop.getProperty("Button"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

       return res;

    }
    
    public static Properties getPropertity(){
       Properties prop = new Properties();
       
     try (InputStream input = new FileInputStream(path+"config.properties")) {

            if (input == null) {
              JOptionPane.showMessageDialog(null,"Sorry, unable to find config.properties");
               //load a properties file from class path, inside static method
               return null;
            }


             prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
       return prop;
    
    }
    
    public static String getPropertityByName(String name){
    
      Properties pro = getPropertity();
      return  pro.getProperty("Button");
    }
    
   /* public static void setPropertityByName(String name,String item){
    
      Properties pro = getPropertity();
      return  
    }*/
    
    
   public String getNotificationUserPreference(){
    String res="";
    
    InputStream input = getClass().getResourceAsStream(userPreferenceFile); 
    //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    
        Properties prop = new Properties();

         try {
             // load a properties file
             prop.load(input);
         } catch (IOException ex) {
             Logger.getLogger(GraphicsProperties.class.getName()).log(Level.SEVERE, null, ex);
         }

            // get the property value and print it out
            System.out.println(prop.getProperty("db.url"));
            System.out.println(prop.getProperty("db.user"));
            System.out.println(prop.getProperty("db.password"));
    
    return res;
    }
    
    
/*public static void LoadGraphicsProperties() {
        
      if (prop == null) {
        
       input = GraphicsProperties.class.getClassLoader().getResourceAsStream("config.properties");
        prop = new Properties();
        try {
            prop.load(input);
        } catch (IOException ex) {
            //Logger.getLogger(GraphicsProperties.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Error - \n Load Theme"+ex); 
        } 
    }

  }*/
    
    
    public static void main(String[] args) {
        
        System.out.println("test - "+getPropertityByName("Theme"));
         //System.out.println("test - "+getThemeProperties());
    }
    
}
