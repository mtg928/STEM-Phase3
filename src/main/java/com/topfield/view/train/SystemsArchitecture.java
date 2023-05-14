/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.TclProjects;
import com.topfield.user.UserInfo;
import com.topfield.view.calculators.SILComponents;
import com.topfield.view.calculators.SILcalculator;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class SystemsArchitecture extends JTabbedPane {  

    public SystemsArchitecture() {
        
        add("Systems Architecture", new TrainSystems());
        //add("Users Main Products", new ManageMainProducts(this));
        //add("Users Sub Products", new ManageSubProducts(this));
        //add("Users Sub Components", new ManageSubComponents(this));
        //add("Manage functional components", new FunctionalComponents(this));
        //add("Manage Components", new AddComponents());
        
    }
    
    
     public void refreshPage(){
        
         SystemsArchitecture systems = new SystemsArchitecture();
        // InternalFrameDemo.mainFrame.getLeftBar().setSilCal(silCalculator);
     }
    
}
