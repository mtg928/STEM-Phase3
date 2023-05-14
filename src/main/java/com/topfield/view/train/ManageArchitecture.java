/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import com.topfield.main.InternalFrameDemo;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class ManageArchitecture extends JTabbedPane {

    public ManageArchitecture() {
        
         add("Users Main Products", new ManageMainProducts(this));
         add("Users Sub Products", new ManageSubProducts(this));
         add("Users Sub Components", new ManageSubComponents(this));
    }
    
    
    public void refreshPage(){
        
         //ManageArchitecture systems = new ManageArchitecture();
         InternalFrameDemo.mainFrame.setCalculators( new ManageArchitecture(),true,true);
     }
    
}
