/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.calculators;

import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class EvacuationCalculation extends JTabbedPane{

    public EvacuationCalculation() {
        
         //add("FMEA Components", new EvacuationCalculator());
        //add("Fault Tree Graph", new Zoom());
        add("Evacuation Calculator", new EvacuationCalculator());
        
    }
    
}
