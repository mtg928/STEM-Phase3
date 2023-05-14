/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;


import com.topfield.calculators.calcfile.CalcFile;
import com.topfield.calculators.fmea.FMEACalculator;
import com.topfield.calculators.fmea.FmeaMil1629A;
import com.topfield.calculators.fmea.FmeaRPN;
import com.topfield.main.InternalFrameDemo;
import static com.topfield.main.InternalFrameDemo.contentPanel;
import com.topfield.reports.CCF_Report_Machine;
import com.topfield.reports.CCF_Report_NonPro;
import com.topfield.reports.CCF_Report_Pro;
import com.topfield.reports.HeatMapReport;
import com.topfield.utilities.ExcelExporter;
import com.topfield.calculators.ccf.CCFMachineryStandard;
import com.topfield.calculators.ccf.CCFcalculator;
import com.topfield.calculators.ccf.CCFcalculatorFromNonProgramable;
import com.topfield.calculators.ccf.CCFcalculatorFromProgramable;
import com.topfield.calculators.eventtree.EventTreeCalculator;
import com.topfield.calculators.eventtree.EventTreeGraph;
import com.topfield.calculators.faulttree.FaultTreeCalculator;
import com.topfield.calculators.fmeca.FMECACalculator;
import com.topfield.calculators.faulttree.FaultTreeData;
import com.topfield.calculators.fmeca.FmecaMil1629A;
import com.topfield.graph.RBDDrawer;
import com.topfield.print.PrintContantToPDF;
import com.topfield.settings.JScroll;
import com.topfield.singleton.FileSelection;
import com.topfield.singleton.SwingComponent;
import com.topfiled.interfaces.MyPrintable;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jgraph.JGraph;

/**
 *
 * @author Murali
 */
public class ExportController {

    public void setExport(JPanel calculator) {
        
        
        
        JTabbedPane seletedTab =((JTabbedPane) contentPanel.getComponent(0));
        

        try {
            if (seletedTab.getComponentAt(1) instanceof CalcFile) {

                exportCMM(seletedTab);
                

            }else if (seletedTab.getComponentAt(1) instanceof RBDDrawer) {

                exportRBD(seletedTab);
                //System.out.println("RBD");

            }else if (seletedTab.getComponentAt(1)instanceof FmeaRPN) {

                exportFMEARpz();

            } else if (seletedTab.getComponentAt(1) instanceof FmeaMil1629A) {
                exportFMEAMil1629A();
            } else if (seletedTab.getComponentAt(1)instanceof FmecaMil1629A ) {
                exportFMECA();

            } else if (seletedTab instanceof CCFcalculator) {
                exportCCF(seletedTab);

            } else if (seletedTab.getClass().getSimpleName().contentEquals("HeatMap")) {
                exportHeatMap();

            } else if (seletedTab instanceof FaultTreeCalculator ) {
                
                if (seletedTab.getSelectedComponent()instanceof FaultTreeData) {
                    exportFaultTree();
                } else {
                  exportFaultTree(seletedTab);
                //exportFaultTreePDF(seletedTab);
                }
                


            } else if (seletedTab instanceof EventTreeCalculator) {
                   exportEventTree(seletedTab);
            } else {

                JOptionPane.showMessageDialog(null, "The content can't be exported" + ((JTabbedPane) contentPanel.getComponent(0)).getComponentAt(1).getClass().getSimpleName());
            }

        } catch (ClassCastException ec) {
            if (contentPanel.getComponent(0) instanceof JScroll) {
                //exportCCF();
                 ec.printStackTrace();
            } else {
                ec.printStackTrace();
                JOptionPane.showMessageDialog(null, "The content can't be exported");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error :- " + e.getMessage());
            e.printStackTrace();
        } finally {
            InternalFrameDemo.mainFrame.removeLoder();
        }

    }
    
     private void exportCMM(JTabbedPane seletedTab) {
        CalcFile c = (CalcFile) seletedTab.getComponentAt(1);


        ExcelExporter e = new ExcelExporter();
        e.WriteExcel2("CMM", c.getFMEATableData());
   
     }
   
     public void exportRBD(JTabbedPane seletedTab){

        RBDDrawer rBDDrawer =(RBDDrawer)seletedTab.getComponentAt(1);
        //JGraph rbdGraph =rBDDrawer.getGraph();
         BufferedImage imagebuf =rBDDrawer.getPrintContantGraph();

         List<String> path = FileSelection.getSelectionPath2(new int[]{0,3});
             
          if (path.size()>1) {
   
              if (path.get(1).equals("pdf")) {
                   PrintContantToPDF.printMxGraph(path.get(0), imagebuf);
              }else {
        
                  PrintContantToPDF.ExportToImage(path.get(0), path.get(1), imagebuf);
              }
                
           }             
        
        System.out.println("ssdv");
    }

    private void exportFMEARpz() {
        FmeaRPN c = (FmeaRPN) ((FMEACalculator) contentPanel.getComponent(0)).getComponent(1);
        // FILE_NAME = FileSelector.getPathSelected();
        //   System.out.println("bhjghjg "+FileSelector.getPathSelected());

        ExcelExporter e = new ExcelExporter();
        e.WriteExcel("FMEA", c.getFMEATableData(),c.getMargeCol());
    }
    
   private void exportFMEAMil1629A() {
        FmeaMil1629A c = (FmeaMil1629A) ((FMEACalculator) contentPanel.getComponent(0)).getComponent(1);
        // FILE_NAME = FileSelector.getPathSelected();
        //   System.out.println("bhjghjg "+FileSelector.getPathSelected());

        ExcelExporter e = new ExcelExporter();
        e.WriteExcel("FMEA", c.getFMEATableData(),c.getMargeCol());
    }

    private void exportFMECA() {
        FmecaMil1629A c = (FmecaMil1629A) ((FMECACalculator) contentPanel.getComponent(0)).getComponentAt(1);
        // FILE_NAME = FileSelector.getPathSelected();
        //   System.out.println("bhjghjg "+FileSelector.getPathSelected());

        ExcelExporter e = new ExcelExporter();
        e.WriteExcel("FMECA", c.getFMEATableData(),c.getMargeCol());
    }

    private void exportCCF(JTabbedPane seletedTab) {
        int selectedIndex = 0;
        JPanel ccfCal= null;
       
            CCFcalculator ccf = (CCFcalculator) seletedTab;
            selectedIndex = ccf.getSelectedIndex(); 
            ccfCal =  (JPanel)SwingComponent.getContentFromJscroll(ccf.getComponent(selectedIndex));
            // try {
            // contant = (JPanel)ccf.getContantPage();
            System.out.println("hhjghjgj"+ ccfCal.getClass().getSimpleName());

            if (ccfCal.getClass().getSimpleName().equals("CCFcalculatorFromProgramable")) {

                CCFcalculatorFromProgramable CCFForm = (CCFcalculatorFromProgramable) ccfCal;
                int[][] SecTot = new int[9][2];
                SecTot[0] = new int[]{CCFForm.getSeparationTotalA(), CCFForm.getSeparationTotalB()};
                SecTot[1] = new int[]{CCFForm.getDiversityTotalA(), CCFForm.getDiversityTotalB()};
                SecTot[2] = new int[]{CCFForm.getComplexityTotalA(), CCFForm.getComplexityTotalB()};
                SecTot[3] = new int[]{CCFForm.getAssessmentTotalA(), CCFForm.getAssessmentTotalB()};
                SecTot[4] = new int[]{CCFForm.getProceduresTotalA(), CCFForm.getProceduresTotalB()};
                SecTot[5] = new int[]{CCFForm.getCompetenceTotalA(), CCFForm.getCompetenceTotalB()};
                SecTot[6] = new int[]{CCFForm.getEnvironmentalTotalA(), CCFForm.getEnvironmentalTotalB()};
                SecTot[7] = new int[]{CCFForm.getEnvironmentalTestingTotalA(), CCFForm.getEnvironmentalTestingTotalB()};
                SecTot[8] = new int[]{CCFForm.getFinalTotalA(), CCFForm.getFinalTotalB()};

                new CCF_Report_Pro(SecTot, CCFForm.getAllSubData(), CCFForm.getFinalComments(), new String[]{CCFForm.getRawScore(), CCFForm.getBetaFactor()}).writeReport();
            } else if (ccfCal.getClass().getSimpleName().equals("CCFcalculatorFromNonProgramable")) {

                CCFcalculatorFromNonProgramable CCFForm = (CCFcalculatorFromNonProgramable) ccfCal;
                int[][] SecTot = new int[9][2];
                SecTot[0] = new int[]{CCFForm.getSeparationTotalA(), CCFForm.getSeparationTotalB()};
                SecTot[1] = new int[]{CCFForm.getDiversityTotalA(), CCFForm.getDiversityTotalB()};
                SecTot[2] = new int[]{CCFForm.getComplexityTotalA(), CCFForm.getComplexityTotalB()};
                SecTot[3] = new int[]{CCFForm.getAssessmentTotalA(), CCFForm.getAssessmentTotalB()};
                SecTot[4] = new int[]{CCFForm.getProceduresTotalA(), CCFForm.getProceduresTotalB()};
                SecTot[5] = new int[]{CCFForm.getCompetenceTotalA(), CCFForm.getCompetenceTotalB()};
                SecTot[6] = new int[]{CCFForm.getEnvironmentalTotalA(), CCFForm.getEnvironmentalTotalB()};
                SecTot[7] = new int[]{CCFForm.getEnvironmentalTestingTotalA(), CCFForm.getEnvironmentalTestingTotalB()};
                SecTot[8] = new int[]{CCFForm.getFinalTotalA(), CCFForm.getFinalTotalB()};

                new CCF_Report_NonPro(SecTot, CCFForm.getAllSubData(), CCFForm.getFinalComments(), new String[]{CCFForm.getRawScore(), CCFForm.getBetaFactor()}).writeReport();

            } else if (ccfCal.getClass().getSimpleName().equals("CCFMachineryStandard")) {

                CCFMachineryStandard CCFForm = (CCFMachineryStandard) ccfCal;

                new CCF_Report_Machine(CCFForm).writeReport();
                //  JOptionPane.showMessageDialog(null, "CCFForm "+CCFForm.getFinalComments());
            }

           
    }
    
   /* public void exportFaultTreePDF(JTabbedPane seletedTab){
        
        FaultTreeGraph2 rBDDrawer =(FaultTreeGraph2)seletedTab.getComponentAt(2);
        mxGraphComponent rbdGraph =rBDDrawer.getGraphComponent();
        

            PrinterJob pj = PrinterJob.getPrinterJob();

            if (pj.printDialog())
            {
                    PageFormat pf = rbdGraph.getPageFormat();
                    Paper paper = new Paper();
                    double margin = 36;
                    paper.setImageableArea(margin, margin, paper.getWidth()
                                    - margin * 2, paper.getHeight() - margin * 2);
                    pf.setPaper(paper);
                    pj.setPrintable(rbdGraph, pf);

                    try
                    {
                            pj.print();
                    }
                    catch (PrinterException e2)
                    {
                            System.out.println(e2);
                    }
            }
        
    }*/
    
    public void exportFaultTree(JTabbedPane seletedTab ){

        //FaultTreeGraph2 rBDDrawer =(FaultTreeGraph2)seletedTab.getComponentAt(2);
        //FaultTreeGraph3 rBDDrawer =(FaultTreeGraph3)seletedTab.getComponentAt(2);
        MyPrintable rBDDrawer =(MyPrintable)seletedTab.getComponentAt(1);
        BufferedImage imagebuf =rBDDrawer.getPrintContantGraph();

         List<String> path = FileSelection.getSelectionPath2(new int[]{0,3});
             
          if (path.size()>1) {
   
              if (path.get(1).equals("pdf")) {
                   PrintContantToPDF.printMxGraph(path.get(0), imagebuf);
              }else {
        
                  PrintContantToPDF.ExportToImage(path.get(0), path.get(1), imagebuf);
              }
                
           }      
        
        System.out.println("ssdv");
    }

    private void exportHeatMap() {
        HeatMapReport hm = new HeatMapReport();
        hm.writeReport();
    }

    
    
    private void exportFaultTree() {

        JTabbedPane jt = (JTabbedPane) contentPanel.getComponent(0);
        FaultTreeData ft = (FaultTreeData) jt.getComponent(2);

        Object[] options = {"Ms Excel", "Ms Access"};
        int n = JOptionPane.showOptionDialog(null, "Please select component type", "Component type",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, //do not use a custom Icon
                options, options[0]);

        if (n == 1) {
            //ft.saveAllTableData();
            ft.exportAccess();
        } else if (n == 0) {
            ExcelExporter e = new ExcelExporter();
            e.WriteExcel("FTA", ft.getHeadings(), ft.loadData());
        }
    }

    private void exportEventTree(JTabbedPane seletedTab) {
        
        //MyPrintable eventDrawer =(MyPrintable)seletedTab.getComponentAt(1); 
        MyPrintable eventDrawer = (MyPrintable)((EventTreeGraph)seletedTab.getComponentAt(1)).getEventTree();
        BufferedImage imagebuf =eventDrawer.getPrintContantGraph();

         List<String> path = FileSelection.getSelectionPath2(new int[]{0,3});
             
          if (path.size()>1) {
   
              if (path.get(1).equals("pdf")) {
                   PrintContantToPDF.printMxGraph(path.get(0), imagebuf);
              }else {
        
                  PrintContantToPDF.ExportToImage(path.get(0), path.get(1), imagebuf);
              }
                
           }        
  
        
        System.out.println("ssdv");
    }


    
   

}



    /*
    
       if(contentPanel.getComponent(0).getClass().getSimpleName().equals("FMEACalculator")){
                         
                 
                    
                }else if(contentPanel.getComponent(0).getClass().getSimpleName().equals("FMECACalculator")){
                         

                    
                }else if(contentPanel.getComponent(0).getClass().getSimpleName().equals("JScroll")){
                    
     
                  
                }else if(contentPanel.getComponent(0).getClass().getSimpleName().equals("HeatMap")){
                

                
                }else if(contentPanel.getComponent(0).getClass().getSimpleName().equals("FaultTreeCalculator")){
                
                       
                }else{
                
                 JOptionPane.showMessageDialog(null, "The content can't be exported");
                }
    
    
    
    
    
    */
