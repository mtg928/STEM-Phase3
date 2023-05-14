/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.controller;

import com.topfield.JavaFX.ListExample_UltraExtended;
import com.topfield.calculators.fmea.FMEACalculator;
import com.topfield.calculators.fmea.FMEAServices;
import com.topfield.calculators.fmea.FmeaMil1629A_old;
import com.topfield.calculators.fmea.FmeaRPN;
import com.topfield.main.InternalFrameDemo;
import static com.topfield.main.InternalFrameDemo.contentPanel;
import com.topfield.utilities.ExcelImporter;
import com.topfield.utilities.FaultTreeReader;
import com.topfield.utilities.FileSelector;
import com.topfield.utilities.StringFuntions;
import com.topfield.calculators.fmeca.FMECA;
import com.topfield.calculators.fmeca.FMECACalculator;
import com.topfield.calculators.faulttree.FaultTreeData;
import com.topfield.calculators.faulttree.FaultTreeGraph;
import com.topfield.calculators.faulttree.FaultTreeGraph3;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Murali
 */
public class ImportController {

    private FMEAServices fMEAServices = new FMEAServices();
    
    public void setImport(JPanel calculator) {

        try {
            if (((JTabbedPane) contentPanel.getComponent(0)).getComponentAt(1).getClass().getSimpleName().contentEquals("FmeaRpz")) {

                importFMEARpz();

            } else if (((JTabbedPane) contentPanel.getComponent(0)).getComponentAt(1).getClass().getSimpleName().contentEquals("FmeaMil1629A")) {
                importFMEAMil1629A();
            } else if (((JTabbedPane) contentPanel.getComponent(0)).getComponentAt(1).getClass().getSimpleName().contentEquals("FMECA")) {
                importFMECA();

            } else if (((JTabbedPane) contentPanel.getComponent(0)).getComponentAt(1).getClass().getSimpleName().contentEquals("FaultTreeData")) {
                importFaultTree();

            } else if (((JTabbedPane) contentPanel.getComponent(0)).getComponentAt(1).getClass().getSimpleName().contentEquals("FMEACalculator1")) {

            } else {

                JOptionPane.showMessageDialog(null, "Import is not allowed to this content - " + ((JTabbedPane) contentPanel.getComponent(0)).getComponentAt(1).getClass().getSimpleName());
            }

        } catch (ClassCastException ec) {
            ec.printStackTrace();
            JOptionPane.showMessageDialog(null, "Import is not allowed to this content");
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error :- " + e.getMessage());
            e.printStackTrace();
        } finally {
            InternalFrameDemo.mainFrame.removeLoder();
        }

    }

    private void importFMEARpz() {

        Object[] columnNames = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Importance (B)", "Failure detection", "Preventive and compensating measures", "Occurrence (A)", "Detection measures", "Detection (E)", "RPZ", "Improvement measures", "Responsible / Date", "Undertaken measures", "Importance (B)", "Occurrence (A)", "Detection (E)", "RPZ"};

        Object[] columnNames2 = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Importance (B)", "Failure detection", "Preventive and compensating measures", "Occurrence (A)", "Detection measures", "Detection (E)", "RPZ", "Improvement measures", "Responsible / Date", "Undertaken measures", "Importance (B)", "Occurrence (A)", "Detection (E)", "RPZ", "Created Time"};

        String path = FileSelector.getPathSelectedFile(new String[]{"xlsx"});
        FmeaRPN c = (FmeaRPN) ((FMEACalculator) contentPanel.getComponent(0)).getComponent(1);
        ExcelImporter i = new ExcelImporter();
        Object[][] tableData = i.ReadExcelNew(path);

        if (Arrays.equals(columnNames, tableData[1]) || Arrays.equals(columnNames2, tableData[1])) {
            tableData = ArrayUtils.remove(tableData, 0);
            tableData = ArrayUtils.remove(tableData, 0);

           // c.progressImportSetup(tableData);
        } else {
            //JOptionPane.showMessageDialog(null, "The file contains different format \n Please match your columns");
            ListExample_UltraExtended demo = new ListExample_UltraExtended();
            showInputDialog(demo, demo.createContentPane(StringFuntions.convertObjToStr(columnNames), StringFuntions.convertObjToStr(tableData[1])), columnNames.length);
            tableData = ArrayUtils.remove(tableData, 0);
            tableData = ArrayUtils.remove(tableData, 0);

            /*for (int j = 0; j < tableData.length; j++) {
                  for (int k = 0; k < 10; k++) {
                      System.out.print(tableData[j][k]);
                  }
                  System.out.println("---------------------------------------------");
              }*/
           // c.progressImportSetup(fMEAServices.setAlignColExport(demo.getMatchColumns(), tableData));
        }

    }

    private void importFMEAMil1629A() {
        
        
    }
    
    
    private void importFMEAMil1629A_old() {

        Object[] columnNames = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Detection measures", "Improvement measures"};

        Object[] columnNames2 = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Detection measures", "Improvement measures", "Created Time"};

        String path = FileSelector.getPathSelectedFile(new String[]{"xlsx"});
        FmeaMil1629A_old c = (FmeaMil1629A_old) ((FMEACalculator) contentPanel.getComponent(0)).getComponent(1);
        ExcelImporter i = new ExcelImporter();
        Object[][] tableData = i.ReadExcelNew(path);

        if (Arrays.equals(columnNames, tableData[1]) || Arrays.equals(columnNames2, tableData[1])) {
            tableData = ArrayUtils.remove(tableData, 0);
            tableData = ArrayUtils.remove(tableData, 0);

            c.progressImportSetup(c.setAlignImport(tableData));
        } else {
            JOptionPane.showMessageDialog(null, "The file contains different format \n Please match your columns" + columnNames.length + " vs " + tableData[1].length);
            ListExample_UltraExtended demo = new ListExample_UltraExtended();
            showInputDialog(demo, demo.createContentPane(StringFuntions.convertObjToStr(columnNames), StringFuntions.convertObjToStr(tableData[1])), columnNames.length);
            tableData = ArrayUtils.remove(tableData, 0);
            tableData = ArrayUtils.remove(tableData, 0);

            /*for (int j = 0; j < tableData.length; j++) {
                  for (int k = 0; k < 10; k++) {
                      System.out.print(tableData[j][k]);
                  }
                  System.out.println("---------------------------------------------");
              }*/
            c.progressImportSetup(c.setAlignImport(fMEAServices.setAlignColExport(demo.getMatchColumnsNew(), tableData)));
        }

    }

    private void showInputDialog(ListExample_UltraExtended demo, JPanel optionpane, int columnLength) {

        int value = JOptionPane.showConfirmDialog(null, optionpane, "Match your columns", JOptionPane.OK_CANCEL_OPTION);

        if (value == JOptionPane.YES_OPTION) {

            if (demo.getMatchColumns().length == columnLength) {
                /* for (int i = 0; i < demo.getMatchColumns().length; i++) {
                    System.out.println(demo.getMatchColumns().length+" matchColumn - " + demo.getMatchColumns()[i]);
                }*/
            } else {
                JOptionPane.showMessageDialog(null, "Please match all(" + columnLength + ") columns. \n only " + demo.getMatchColumns().length + " are Matched");
                showInputDialog(demo, optionpane, columnLength);
            }

        } else if (value == JOptionPane.NO_OPTION) {

        }

    }

    private void importFMECA() {

        Object[] columnNames = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Failure Effect Probability(?)", "Failure Mode Ratio(?)", "Failure Rate(?p)", "Operating Time(t)", "Failure Mode Criticality(Cm)", "Failure Item Criticality(Cr)"};

        Object[] columnNames2 = {"Line no", "Subsystem Breakdown Code", "Subsystem/ Component", "Function", "Phase", "Failure mode", "Failure Cause", "Failure Effect:(Local System Level)",
            "Failure Effect:(Train Subsystem Level)", "Failure Effect:(Train Level)", "Failure detection", "Preventive and compensating measures", "Failure Effect Probability(?)", "Failure Mode Ratio(?)", "Failure Rate(?p)", "Operating Time(t)", "Failure Mode Criticality(Cm)", "Failure Item Criticality(Cr)", "Created Time"};

        String path = FileSelector.getPathSelectedFile(new String[]{"xlsx"});
        ExcelImporter i = new ExcelImporter();
        Object[][] tableData = i.ReadExcelNew(path/*"C:/Users/Murali/Desktop/FMEA.xlsx"*/);
        
        FMECA c = (FMECA) ((FMECACalculator) contentPanel.getComponent(0)).getComponentAt(1);

        if (Arrays.equals(columnNames, tableData[1]) || Arrays.equals(columnNames2, tableData[1])) {
            tableData = ArrayUtils.remove(tableData, 0);
            tableData = ArrayUtils.remove(tableData, 0);

            c.progressImportSetup(tableData);
        } else {
            JOptionPane.showMessageDialog(null, "The file contains different format \n Please match your columns" + columnNames.length + " vs " + tableData[1].length);
            ListExample_UltraExtended demo = new ListExample_UltraExtended();
            showInputDialog(demo, demo.createContentPane(StringFuntions.convertObjToStr(columnNames), StringFuntions.convertObjToStr(tableData[1])), columnNames.length);
            tableData = ArrayUtils.remove(tableData, 0);
            tableData = ArrayUtils.remove(tableData, 0);

            /*for (int j = 0; j < tableData.length; j++) {
                  for (int k = 0; k < 10; k++) {
                      System.out.print(tableData[j][k]);
                  }
                  System.out.println("---------------------------------------------");
              }*/
            c.progressImportSetup(fMEAServices.setAlignColExport(demo.getMatchColumns(), tableData));
        }

    }

    private void importFaultTree() {
        JTabbedPane jt = (JTabbedPane) contentPanel.getComponent(0);

        // JOptionPane.showMessageDialog(null, "jt.getSelectedIndex() - "+jt.getSelectedIndex());
        InternalFrameDemo.mainFrame.setLoader();
        
        
        try {

            if (jt.getSelectedIndex() == 1) {
                //JOptionPane.showMessageDialog(null, "jt.getSelectedIndex() - "+jt.getComponent(1));

                Object[] options = {"Ms Excel", "Ms Access"};
                int n = JOptionPane.showOptionDialog(null, "Please select component type", "Component type",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, //do not use a custom Icon
                        options, options[0]);

                FaultTreeData ft = (FaultTreeData) jt.getComponent(1);

                if (n == 1) {

                    ft.importAccess();

                } else if (n == 0) {

                    String path = FileSelector.getPathSelectedFile(new String[]{"xlsx"});
                    ExcelImporter i = new ExcelImporter();
                    Object[][] tableData = i.ReadExcelNew(path/*"C:/Users/Murali/Desktop/FMEA.xlsx"*/);
                    tableData = ArrayUtils.remove(tableData, 0);
                    //ft.setTableData(tableData);

                    ft.saveImportExcel(tableData);
                    ft.loadData();

                }

        } else if (jt.getSelectedIndex() == 2) {

            //FaultTreeGraph graph = (FaultTreeGraph) jt.getComponent(2);
            FaultTreeGraph3 graph = (FaultTreeGraph3) jt.getComponent(2);
            graph.loadFaultTreeImports(new FaultTreeReader().getLogonFTA());
            //new FaultTreeReader().getLogonFTA();
            graph.refreshAll();
           
        }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            InternalFrameDemo.mainFrame.removeLoder();
        }
        

    }

}

/*

                if(contentPanel.getComponent(0).getClass().getSimpleName().equals("FMEACalculator")){
                
                    
                }else if(((JTabbedPane)contentPanel.getComponent(0)).getClass().getSimpleName().equals("FMECACalculator")){
                 
                 
                 
               
                    
                }else if(contentPanel.getComponent(0).getClass().getSimpleName().equals("FaultTreeCalculator")){
                    
                   
                }
 */
