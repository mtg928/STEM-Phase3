/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.reports;

import com.topfield.controller.ReportLoaderController;
import com.topfield.utilities.Application;
import com.topfield.dao.ServiceDao;
import com.topfield.daoImpl.ServiceDaoImpl;
import com.topfield.modal.Test1;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Murali
 */
public class FMECACriticalityReport extends JPanel {

    private ServiceDao svc = new ServiceDaoImpl();

    public FMECACriticalityReport() {

        setLayout(new BorderLayout());

        runReport2();

    }

    private void runReport2() {
        try {
            List<Test1> tests = svc.getTest1All();
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(tests);
            HashMap param = new HashMap();

            //URL url = new URL("https://www.topfieldconsultancy.co.uk/STEMS/reports/criticalityreport_1.jasper");
            //InputStream in = getClass().getResourceAsStream("C:\\Program Files (x86)\\STEMS\\criticalityreport_1.jrxml");
            //JasperDesign jasperDesign = JRXmlLoader.load(in);


             InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("reports/criticalityreport_1.jasper");
             
             JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, param, beanColDataSource);
             
            //JasperReport rpt = (JasperReport)JRLoader.loadObject(url);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(ReportLoaderController.getReport(), param, beanColDataSource);
            //JasperPrint jasperPrint = JasperFillManager.fillReport("C:\\Program Files (x86)\\STEMS\\criticalityreport_1.jasper", param, beanColDataSource);
            //JasperViewer.viewReport(jasperPrint, false);
            //add(new JRViewer(jasperPrint), BorderLayout.CENTER);
            JasperViewer jv = new JasperViewer(jasperPrint, false);
            add(jv.getContentPane(), BorderLayout.CENTER);

            // to directly popup save file
            // JasperPrintManager.printReport(jasperPrint, false);
        } catch (JRException ex) {
            Logger.getLogger(FMECACriticalityReport.class.getName()).log(Level.SEVERE, null, ex);
        }/* catch (MalformedURLException ex) {
            Logger.getLogger(FMECACriticalityReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FMECACriticalityReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FMECACriticalityReport.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

}
