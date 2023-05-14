/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.faulttree;

import com.topfield.calculators.rbd.SPFComponents;
import com.topfield.main.InternalFrameDemo;
import com.topfield.modal.Spfhdr;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author Murali
 */
public class FaultTreeCalculator extends JTabbedPane {

    public FaultTreeCalculator() {

        //add("Fault Tree Components", new FaulttreeComponents(this));
        add("Fault Tree Summary", new SPFComponents(this,"FTA"));
        add("Fault Tree Model", null);
        add("FT Data Report",null ); //new FaultTreeData(this, 0)

        //add("Fault Tree Data", null);
        //add("Fault Tree Graph",null );
       
       
       /* Below code Effececy check */
       // add("Fault Tree Graph", null);
        //add("Fault Tree Data",null );

    }

    public void refreshPage() {

        FaultTreeCalculator faultTreeCalculator = new FaultTreeCalculator();
        InternalFrameDemo.mainFrame.getLeftBar().setFaulttreeCal(faultTreeCalculator);

    }

    public void refreshData(Spfhdr spfhdr) {
        this.setComponentAt(2, new FaultTreeData(this, spfhdr));
    }

    public void refreshGraph(Spfhdr spfhdr) {
        this.setComponentAt(1, new JgraphDrawer( spfhdr));
    }
    
    public FaultTreeData getFaultTable(){
    
    return (FaultTreeData) getComponentAt(2);
    }

}
