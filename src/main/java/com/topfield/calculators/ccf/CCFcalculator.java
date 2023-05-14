/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.ccf;


import com.topfield.calculators.rbd.SPFComponents;
import com.topfield.main.InternalFrameDemo;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Murali
 */
public class CCFcalculator extends JTabbedPane{
    
  private JPanel content = new JPanel();
  //private CCFcalculatorFromProgramable Pro;
  //private CCFcalculatorFromNonProgramable nonPro;
 // private CCFDiagnosticCoverage daiCov;

    public CCFcalculator() {

        //add("CCF of Components", new CcfComponents(this));
        add("CCF Summary", new SPFComponents(this,"CCF"));

        add("Calculator", null);
        
        
    }
    
    
    public JPanel getContantPage(){
    
      return content;
    }
        
    public void refreshPage(){
        
         CCFcalculator CCFcalculator = new CCFcalculator();
         InternalFrameDemo.mainFrame.getLeftBar().setCcfCal(CCFcalculator);
         

     
     
     }
}
