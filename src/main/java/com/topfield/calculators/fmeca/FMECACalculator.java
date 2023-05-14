/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmeca;

import com.topfield.calculators.calcfile.MPGComponents;
import com.topfield.calculators.fmea.CalcHdrComponents;
import com.topfield.main.InternalFrameDemo;
import com.topfield.reports.FMECACriticalityReport;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Murali
 */
public class FMECACalculator extends JTabbedPane {

    public FMECACalculator() {

        //add("FMECA Components", new MPGComponents(this, "FMECA",new String[]{"Failure Mode Criticality","Failure Item Criticality"})); //new FMECAComponents(this)
        // add("FMECA Summary", new CalcHdrComponents(this, "FMECA",new String[]{"Failure Mode Criticality","Failure Item Criticality"})); //new FMECAComponents(this)
        add("FMECA Summary", new CalcHdrComponents(this, "FMECA",new String[]{"RAM","Safety"}));
        add("FMECA Calculator", null);  //new FMECA(0)
        //add("Fault Tree Graph", new Zoom());

        //add("Severity Graph", new SeverityGraph());
       // add("Criticality Report", null);

       setLazyLoading();

    }

    public void refreshPage() {

        FMECACalculator fMECACalculator = new FMECACalculator();
        InternalFrameDemo.mainFrame.getLeftBar().setFmecaCal(fMECACalculator);

    }

    
    public void setLazyLoading(){
    
       
     ChangeListener changeListener = new ChangeListener() {
         
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                //System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
                
                if (index ==2) {
                    //setComponentAt(2,null);
                    InternalFrameDemo.mainFrame.setLoader();
                    setComponentAt(2,new FMECACriticalityReport());
                    InternalFrameDemo.mainFrame.removeLoder();
                    
                }
            }
        };
        
        addChangeListener(changeListener);
    
    }
}
