/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.calculators;

import com.topfield.main.InternalFrameDemo;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class SILcalculator extends JTabbedPane {

    public SILcalculator() {
        
        add("SIL Components", new SILComponents(this));
        //add("Fault Tree Graph", new Zoom());
        add("SIL Calculator", null);
        
        //add("Criticality Report",new FMECACriticalityReport());
        
        
    }
    
    public void refreshPage(){
        
         SILcalculator silCalculator = new SILcalculator();
         InternalFrameDemo.mainFrame.getLeftBar().setSilCal(silCalculator);

     
     
     }
    
}
