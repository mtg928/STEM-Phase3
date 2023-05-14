/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.view.panel; 


import com.topfield.drawings.EditorGraph;
import com.topfield.drawings.GraphEd;
import com.topfield.calculators.rbd.RBDCalculator;
import com.topfield.graph.RBDDrawer;
import com.topfield.main.InternalFrameDemo;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.TopPanelButton;
import com.topfield.versioncontrol.DataReference;
import com.topfield.versioncontrol.ProAssumptions;
import com.topfield.view.train.ElectronicComponents;
import com.topfield.view.train.Failuredatabase;
import com.topfield.view.train.FunctionalArchitecture;
import com.topfield.view.train.SystemsArchitecture;
import com.topfield.visualization.HAZ001_1;
import com.topfield.visualization.HazardVisual;
import com.topfield.visualization.HeatMap;
import com.topfield.visualization.HelloWorld;
import com.topfield.visualization.RiskMatrixContainer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Murali
 */
public class ToolBar extends JPanel{
    
    private JPanel content = InternalFrameDemo.contentPanel;
    private TopPanelButton selected ;
    
    public ToolBar(){
        
       setLayout(new FlowLayout(FlowLayout.LEFT));
       setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
       setBackground(FrameSettings.getFrameColor());
       setForeground(FrameSettings.getFontColor());
        
        TopPanelButton hazards = new TopPanelButton("Hazards",null);
        //TopPanelButton birdsEye = new TopPanelButton("Birds Eye",null);
        //TopPanelButton faultTree = new TopPanelButton("FaultTree",null);
        TopPanelButton systems = new TopPanelButton("Systems",null);
        //TopPanelButton riskMatrix = new TopPanelButton("Risk Matrix",null); 
        //TopPanelButton heatMap = new TopPanelButton("Heat Map",null);
        //TopPanelButton electronicComponents = new TopPanelButton("Functions",null);
        TopPanelButton Failuredatabase = new TopPanelButton("Failure DB",null);
        TopPanelButton assumptions = new TopPanelButton("Assumptions",null);
        TopPanelButton dataRef = new TopPanelButton("References",null);
       // TopPanelButton rbd = new TopPanelButton("RBD",null);


        add(hazards);
        //add(birdsEye);
       // add(faultTree);
       // add(riskMatrix);
        //add(heatMap);
        add(systems);
        //add(electronicComponents);
        add(Failuredatabase);
        add(assumptions);
        add(dataRef);
       // add(rbd);
      
        
    }
    
    
public void setOnclickColour(TopPanelButton button){
      TopPanelButton test;

        for (int i = 0; i < getComponentCount(); i++) {
            test =(TopPanelButton)getComponent(i);
            if (test.equals(button)) {
                test.OnClickAction();
            }else{
               test.OffClickAction();
            }
        }

    }



public void setUnSelectAll(){
      TopPanelButton test;
        for (int i = 0; i < getComponentCount(); i++) {
            test =(TopPanelButton)getComponent(i);
 
             test.setBackground(FrameSettings.getButtonColor());
             test.setForeground(Color.WHITE);
            
        }

        setSelected(null);
}

    /**
     * @return the selected
     */
    public TopPanelButton getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(TopPanelButton selected) {
        this.selected = selected;
    }
    
    public void setOnclickAction(String button){
         InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        switch (button) {
            case "Hazards":
                InternalFrameDemo.mainFrame.setCalculators(new HazardVisual(),true, false);
                break;
            case "Birds Eye":
                InternalFrameDemo.mainFrame.setCalculators(new HelloWorld(),false, true);
                break;
            case "FaultTree":
                InternalFrameDemo.mainFrame.setCalculators(new HAZ001_1(),false, false);
                break;
            case "Systems":
                InternalFrameDemo.mainFrame.setCalculators(new SystemsArchitecture(),true, false);
                break;
            case "Risk Matrix":
                InternalFrameDemo.mainFrame.setCalculators(new RiskMatrixContainer(),false, false);
                break;
            case "Heat Map":
                InternalFrameDemo.mainFrame.setCalculators(new HeatMap(),false, false);
                break;
            case "Functions":
                InternalFrameDemo.mainFrame.setCalculators(new FunctionalArchitecture(),true, true);
                break;
             case "Failure DB":
                InternalFrameDemo.mainFrame.setCalculators(new Failuredatabase().makeUI(),false, false);
                break;
            case "Assumptions":
                InternalFrameDemo.mainFrame.setCalculators(new ProAssumptions(),true, true);
                break;
            case "References":
                InternalFrameDemo.mainFrame.setCalculators(new DataReference(),true, true);
                break;    
            default:
                InternalFrameDemo.mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                break;
        }
    
    
    }
    
    
    
}



