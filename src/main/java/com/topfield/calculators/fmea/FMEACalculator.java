/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.fmea;

import com.topfield.calculators.calcfile.MPGComponents;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Mpghdr;
import javax.swing.JTabbedPane;
/**
 *
 * @author Murali
 */
public class FMEACalculator extends JTabbedPane{

    public FMEACalculator() {
        
       // add("FMEA Components", new MPGComponents(this,"FMEA",new String[]{"RAM","Safety"}));
       add("FMEA Summary", new CalcHdrComponents(this,"FMEA",new String[]{"RAM","Safety"}));
        //add("Fault Tree Graph", new Zoom());
        add("FMEA Calculator", null); //new FmeaRpz(new Fmeas())
        
        //add("Criticality Report",new FMECACriticalityReport());
        
    }
    
     public void refreshPage(){
        
         FMEACalculator fMEACalculator = new FMEACalculator();
         InternalFrameDemo.mainFrame.getLeftBar().setFmeaCal(fMEACalculator);

     }
      
    
}
