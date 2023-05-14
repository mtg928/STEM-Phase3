/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.rbd;
import com.topfield.graph.RBDComponents;
import com.topfield.main.InternalFrameDemo;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class RBDCalculator  extends JTabbedPane{

    public RBDCalculator() {
        
        
        add("RBD Summary", new SPFComponents(this,"RBD"));  //new RBDComponents(this,"RBD")
        add("RBD Drawer", null); //new RBDDrawer(1)

        
    }
    
     public void refreshPage(){
        
         RBDCalculator rBDCalculator = new RBDCalculator();
         InternalFrameDemo.mainFrame.setCalculators(new RBDCalculator(),true, true);

     }
}