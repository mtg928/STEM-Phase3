/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import com.topfield.main.InternalFrameDemo;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class HazardVisual extends JTabbedPane{ 

    public HazardVisual() {  
        add("General Hazards", new GeneralHazard(this));
    }

    public void refreshPage() {
        InternalFrameDemo.mainFrame.setCalculators(new HazardVisual(),true, true);
    } 
}
