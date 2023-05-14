/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Murali
 */
public class ReportLoaderController {
    
    private static InputStream inputFile;
    
    public static JasperReport getReportFromComplile(){
        
       JasperReport jasperReport = null;
        try {
           URL url = new URL("https://www.topfieldconsultancy.co.uk/STEMS/reports/criticalityreport_1.jasper"); //jrxml
             URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputFile = urlc.getInputStream();
    
            //String path = "C:\\Program Files (x86)\\STEMS\\criticalityreport_1.jrxml";
            //InputStream input = new FileInputStream(new File(path));
            //InputStream input = new URL("https://www.topfieldconsultancy.co.uk/STEMS/reports/criticalityreport_1.jrxml").openStream();
            JasperDesign jasperDesign = JRXmlLoader.load(inputFile);
             jasperReport = JasperCompileManager.compileReport(jasperDesign);
            
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ReportLoaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportLoaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ReportLoaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    return jasperReport;
    }
    
    
     public  InputStream getReportLocal(){
    
             InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("reports/criticalityreport_1.jasper");
        
       
    return inputStream;
    }
    
    
    
    public static InputStream getReport(){
    
       /* if (inputFile == null) {
            inputFile = getReportByName();
        }*/
        
        inputFile = getReportByName();
    return inputFile;
    }
     
    
    
    
    
    
    public static InputStream getReportByName(){
         
       InputStream inputFile = null;  
        try {
            // add user agent
            URL url = new URL("https://www.topfieldconsultancy.co.uk/STEMS/reports/criticalityreport_1.jasper"); //jrxml
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
             inputFile = urlc.getInputStream();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ReportLoaderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportLoaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  inputFile;
     }
    
      
}
