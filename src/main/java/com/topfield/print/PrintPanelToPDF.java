/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.print;

import com.qoppa.pdfWriter.PDFPrinterJob;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;


//import com.qoppa.pdfWriter.PDFPrinterJob;
/**
 *
 * @author Murali
 */
public class PrintPanelToPDF implements Printable {

    private JPanel print;

    public PrintPanelToPDF(JPanel print) {

        this.print = print;

    }
    
    

    public void printJobSetter() {

    }

    public static void main(String[] args) {

        // create a panel with a label and a textfield
        JPanel myPanel = new javax.swing.JPanel();
        myPanel.setName("myPanel");
        myPanel.setBorder(new javax.swing.border.EtchedBorder());
        myPanel.setBounds(new java.awt.Rectangle(0, 0, 300, 300));
        // create a label
        JLabel myLabel = new JLabel("MyLabel");
        myLabel.setText("Label");
        myLabel.setLocation(10, 10);
        myLabel.setSize(50, 20);
        myLabel.setVisible(true);
        // add label to panel
        myPanel.add(myLabel);
        // create a text field 
        JTextField myTextField = new JTextField();
        myTextField.setText("My Text");
        myTextField.setLocation(65, 10);
        myTextField.setSize(50, 20);
        myTextField.setVisible(true);
        // add text field to panel
        myPanel.add(myTextField);
        // you can add any swing component here
        // ..... 
        // revalidate and repaint the panel
        myPanel.revalidate();
        myPanel.repaint();
        // print the panel to the graphics on page 0

        try {
            // create a PDF Printer Job
            PDFPrinterJob printer = (PDFPrinterJob) PDFPrinterJob.getPrinterJob();
            // set the printable object 

            PageFormat pf = printer.defaultPage();
            Paper paper = new Paper();
            pf.setOrientation(PageFormat.LANDSCAPE);
            // paper.setSize(1000, 1000);
            double margin = 36; // half inch
            paper.setImageableArea(margin, margin, 1000 - margin * 2, 1000
                    - margin * 2);
            pf.setPaper(paper);

            //pj.setPrintable(new MyPrintable(), pf);
            printer.setPrintable(new PrintPanelToPDF(myPanel), pf);
            // set number of copies to 1 
            printer.setCopies(1);
            //printer.setPageable(pgbl);
            // print and save the document
            printer.print("G:\\Resources\\mydoc.pdf");
            // output done message 
            System.out.println("Done!");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex == 0) {

            // translate the graphics for margins
            g.translate(20, 20);

           /* Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("Serif", Font.PLAIN, 36));
            g2.setPaint(Color.black);
            g2.drawString("www.java2s.com", 100, 100);
            Rectangle2D outline = new Rectangle2D.Double(pf.getImageableX(), pf.getImageableY(), pf
                    .getImageableWidth(), pf.getImageableHeight());
            g2.draw(outline);*/

            print.print(g);
            return Printable.PAGE_EXISTS;
        } else {
            return Printable.NO_SUCH_PAGE;
        }
    }
}
