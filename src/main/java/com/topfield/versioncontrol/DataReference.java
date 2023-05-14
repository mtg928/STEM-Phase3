/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.versioncontrol;

import com.topfield.main.InternalFrameDemo;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class DataReference extends JTabbedPane{ 

    public DataReference() {  
        add("Reference", new ManageReferences(this));
    }

    public void refreshPage() {
        InternalFrameDemo.mainFrame.setCalculators(new DataReference(),true, true);
    } 
}
