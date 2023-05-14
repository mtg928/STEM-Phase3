/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.print;

import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;
import com.qoppa.pdfWriter.PDFPrinterJob;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class PrintContantToPDF {
    
    public static void printMxGraph(String path,BufferedImage image){
         try {

                // create a PDF Printer Job  https://www.qoppa.com/pdfwriter/
                PDFPrinterJob printer = (PDFPrinterJob) PDFPrinterJob.getPrinterJob();

                PageFormat pf = printer.defaultPage();
                Paper paper = new Paper();
                pf.setOrientation(PageFormat.LANDSCAPE);
                double margin = 0; 
                paper.setSize(image.getHeight(),image.getWidth());
                //paper.setSize(1000, 2000);
                
                paper.setImageableArea(margin, margin, paper.getWidth(), paper.getHeight());
                pf.setPaper(paper);
                

                // set the printable object 
                printer.setPrintable(new PrintImageToPDF(image), pf);
                // set number of copies to 1 
                printer.setCopies(1);
                // print and save the document
                printer.print(path+".pdf");
                // output done message 
                System.out.println("Done!");
                
                JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+path+".pdf");

                
            } catch (Throwable t) {
                t.printStackTrace();
            }
            
    }



public static void printJpanel(File file,JPanel panel){
         try {
                // create a PDF Printer Job  https://www.qoppa.com/pdfwriter/
                PDFPrinterJob printer = (PDFPrinterJob) PDFPrinterJob.getPrinterJob();

                PageFormat pf = printer.defaultPage();
                Paper paper = new Paper();
                pf.setOrientation(PageFormat.LANDSCAPE);
                double margin = 0; 
                paper.setSize(panel.getHeight()+15, panel.getWidth()+30);
                //paper.setSize(1000, 2000);
                
                paper.setImageableArea(margin, margin, paper.getWidth(), paper.getHeight());
                pf.setPaper(paper);

                // set the printable object 
                printer.setPrintable(new PrintPanelToPDF(panel), pf);
                // set number of copies to 1 
                printer.setCopies(1);
                // print and save the document
                printer.print(file.getAbsolutePath()+".pdf");
                // output done message 
                System.out.println("Done!");
                
                JOptionPane.showMessageDialog(null, "File Export successfully \nExported file path:-"+file.getAbsolutePath()+".pdf");

                
            } catch (Throwable t) {
                t.printStackTrace();
            }
            
    }

    public static void ExportToImage(String path,String extension,BufferedImage image){
    
               try {
                       //ImageIO.write(image,"jpeg", new File(pth.toString()+".jpeg"));
                       ImageIO.write(image, extension, new File(path+"."+extension));
                       JOptionPane.showMessageDialog(null, path+"."+extension);

               } catch (Exception e) {
                   // TODO Auto-generated catch block
                   System.out.println("error");
                    e.printStackTrace();
               }
    
    }
    
}
