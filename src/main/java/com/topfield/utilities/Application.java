/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Murali
 */
public class Application {
    
     
    
    public static void main(String[] args) throws IOException {

        Application main = new Application();
        main.testData();
    }
    
    public void testData(){
        
        URL res = getClass().getClassLoader().getResource("hibernate.properties");
        File file;
        try {
            file = Paths.get(res.toURI()).toFile();
            String absolutePath = file.getAbsolutePath();
            
            printFile(file);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
      //  InputStream is = getClass().getResourceAsStream("/hibernate.properties");
   // is.get
        
       // File file = main.getFileFromResources("hibernate.properties");
       
       

        //String res = file.getName();
        
        //System.out.println("Path - "+file.toPath());
        
        //printFile(file);
    }

    // get file from classpath, resources folder
    public File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    private static void printFile(File file) throws IOException {

        if (file == null) return;

        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
    
}
