/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.train;

import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class FunctionalArchitecture extends JTabbedPane {

    public FunctionalArchitecture() {
        
        add("Manage functional components", new FunctionalComponents(this));
    }
    
}
