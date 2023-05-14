/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.calcfile;

import com.topfield.graph.RBDComponents;
import com.topfield.main.InternalFrameDemo;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Murali
 */
public class CalcCalculator extends JTabbedPane{

    
    private MPGComponents MPGComponents = new MPGComponents(this, "CMM",new String[]{"ALL","RBD","FMEA","FMECA","FTA"});
    private cmmReport cmmReport = new cmmReport();
    
    public CalcCalculator() {
        
        add("CMM Summary",MPGComponents );
        add("Function Components", null); //new RBDDrawer(1)
        add("Calcfile Linkage Report", cmmReport); //new RBDDrawer(1)
        
        
            ChangeListener changeListener = new ChangeListener() {
             public void stateChanged(ChangeEvent changeEvent) {
               JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
               
                  if (sourceTabbedPane.getSelectedIndex()==2) {
                      
                      try {
                        InternalFrameDemo.mainFrame.setLoader();
                        setReport();
                        System.out.println("Tab changed to: ");
                      } catch(Exception e){
                          e.printStackTrace();
                          JOptionPane.showMessageDialog(InternalFrameDemo.mainFrame,e.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
                      }finally{
                          InternalFrameDemo.mainFrame.removeLoder();
                      }

                   }
                    
             }
           };
           addChangeListener(changeListener);
        
    }

    
     public void refreshPage() {

        CalcCalculator calcCalculator = new CalcCalculator();
        InternalFrameDemo.mainFrame.getLeftBar().setCalcFile(calcCalculator);

    } 
     
    public void setReport(){

         String[] columnNames = { "APP Name", "APP Id", "Description", "CMM ID","Description" };
         String data[][] = new String[0][];
         
        if (MPGComponents.getSelectedId() > 0) {
            data = CMMValidation.list2DtoArray(CMMValidation.getCalcHdrList(MPGComponents.getSelectedId()));
            cmmReport.setHeading(MPGComponents.getSelectedId());
        } 
        
         cmmReport.getReportTable().setData(columnNames, data);
          

    } 
       
       
}
