/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;

import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Eventtree;
import javax.swing.JTabbedPane;

/**
 *
 * @author Murali
 */
public class EventTreeCalculator extends JTabbedPane {

    public EventTreeCalculator() {
        
         
        add("EventTree Summary", new EventTreeComponents(this));    
        //add("Event Tree Data", null ); //new EventTreeData(this,new Eventtree())
        //add("Fault Tree Graph", new Zoom());
        //add("EventTree", new EventTreeModel());
        add("EventTree",null ); //new EventTreeCal(this);

    }
    
    public void refreshPage(){
        
         EventTreeCalculator eventTreeCalculator = new EventTreeCalculator();
         InternalFrameDemo.mainFrame.getLeftBar().setEventreeCal(eventTreeCalculator);

     }

}
