/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.calculators.eventtree;

import com.topfield.datamodel.Item;
import com.topfield.graph.EventRedirector;
import com.topfield.modal.Calcfile;
import com.topfield.modal.Eventtreehdr;
import com.topfield.settings.JScroll;
import java.awt.BorderLayout;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.topfield.utilities.ImageConversion;
import com.topfield.view.popup.ListSelectionPopup;
import com.topfield.view.popup.RBDSettingsPopup;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.Box;
/**
 *
 * @author Murali
 */
public class EventTreeGraph extends JPanel {

    protected Action undo, redo, remove, group, ungroup, tofront, toback, cut, copy, paste,save; // Actions which Change State
    public DendrogramPaintTest dendrogramPaintTest;
    private static JLabel saveInfo; 
    public EventTreeGraph(EventTreeCalculator parent, Eventtreehdr eventHdr) {
        
        dendrogramPaintTest = new DendrogramPaintTest(parent, eventHdr);
        saveInfo =new JLabel();
        setSavedHeading(false);
               // Use Border Layout
        setLayout(new BorderLayout());
        // Add a ToolBar
        add(createToolBar(), BorderLayout.NORTH);
        // Add the Graph as Center Component
        add( new JScroll(dendrogramPaintTest), BorderLayout.CENTER);
        
       // statusBar = createStatusBar();
        //add(statusBar, BorderLayout.SOUTH);
    }
    
    public DendrogramPaintTest getEventTree(){
    
        return dendrogramPaintTest;
    }
    
    public static void setSavedHeading(boolean edited){
        
        if (edited) {
          saveInfo.setText("Please save the Document     ");
          saveInfo.setForeground(Color.red);
        }else{
          saveInfo.setText("Document Saved     ");
          saveInfo.setForeground(new Color(14, 102, 85 ));
        }
    }
    
    
     public JToolBar createToolBar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        // Insert
        URL insertUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/InsertNode.png");
        ImageIcon insertIcon = ImageConversion.SetImageSize(insertUrl);
        toolbar.add(new AbstractAction("", insertIcon) {
            public void actionPerformed(ActionEvent e) {
  
                dendrogramPaintTest.performAddOrEdit();
                
            }
        }).setToolTipText("Insert Node");

        // Toggle Connect Mode
        URL connectUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/text.png");
        ImageIcon connectIcon = ImageConversion.SetImageSize(connectUrl);
        toolbar.add(new AbstractAction("", connectIcon) {
            public void actionPerformed(ActionEvent e) {
                  dendrogramPaintTest.performAddOrEditLevelCaptions();
            }
        }).setToolTipText("Add Or Edit Level Captions");

       
        // Remove
        URL removeUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/delete.png");
        ImageIcon removeIcon = ImageConversion.SetImageSize(removeUrl);
        remove = new AbstractAction("", removeIcon) {
            public void actionPerformed(ActionEvent e) {
                dendrogramPaintTest.performDelete();
            }
        };
        //remove.setEnabled(false);
        toolbar.add(remove).setToolTipText("Delete");

        // To Front
        toolbar.addSeparator();
        URL toFrontUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/Delete_text.png");
        ImageIcon toFrontIcon = ImageConversion.SetImageSize(toFrontUrl);
        tofront = new AbstractAction("", toFrontIcon) {
            public void actionPerformed(ActionEvent e) {
               dendrogramPaintTest.performDeleteLevelCaptions();
            }
        };
        toolbar.add(tofront).setToolTipText("Perform DeleteLevelCaptions");

        // Zoom Std
        toolbar.addSeparator();
        URL zoomUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/calculate.png");
        ImageIcon zoomIcon = ImageConversion.SetImageSize(zoomUrl);
        toolbar.add(new AbstractAction("", zoomIcon) {
            public void actionPerformed(ActionEvent e) {
               dendrogramPaintTest.performCalculate();
            }
        }).setToolTipText("Calculate");
        // Zoom In
        URL zoomInUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/zoomin.png");
        ImageIcon zoomInIcon = ImageConversion.SetImageSize(zoomInUrl);
        toolbar.add(new AbstractAction("", zoomInIcon) {
            public void actionPerformed(ActionEvent e) {
                 dendrogramPaintTest.performZoomIn();
            }
        }).setToolTipText("Zoom In");
        // Zoom Out
        URL zoomOutUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/zoomout.png");
        ImageIcon zoomOutIcon = ImageConversion.SetImageSize(zoomOutUrl);
        toolbar.add(new AbstractAction("", zoomOutIcon) {
            public void actionPerformed(ActionEvent e) {
                dendrogramPaintTest.performZoomOut();
            }
        }).setToolTipText("Zoom Out");

       
        
        // Save
        URL saveInUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/save.png");
        ImageIcon saveIcon = ImageConversion.SetImageSize(saveInUrl);
        toolbar.add(new AbstractAction("", saveIcon) {
            public void actionPerformed(ActionEvent e) {
                       dendrogramPaintTest.SaveAllNodes();
                       
                       //dendrogramPaintTest.SaveEventTreeDataNew();
            }
        }).setToolTipText("Save All");
/*
         // settings
        URL settingsUrl = getClass().getClassLoader().getResource(
                "small/icon/gray/settings.png");
        ImageIcon settingsIcon = ImageConversion.SetImageSize(settingsUrl);
        toolbar.add(new AbstractAction("", settingsIcon) {
            public void actionPerformed(ActionEvent e) {
                
                Object[] options1 = {"Apply", "Cancel"};
                RBDSettingsPopup rbdSettingsPopup = new RBDSettingsPopup();
                
                rBDSettings.setRBDSettingsPro(graph, rbdSettingsPopup);
                
                
                int result = JOptionPane.showOptionDialog(null, rbdSettingsPopup, "RBD Settings",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);

                if (result == JOptionPane.YES_OPTION) {
                    
                    graph.getGraphLayoutCache().edit(rBDSettings.setRBDSettings(graph, rbdSettingsPopup), null, null, null);
                     
                 //graph.getGraphLayoutCache().edit(rBDSettings.setRBDBackGroundColour(rBDSettings.RBDcellSelector(graph, RBDCell.RBD_VERTEX), rbdSettingsPopup.getRBDVertixBgColor(),rbdSettingsPopup.getRBDVertixFontColor(),rbdSettingsPopup.getRBDVertixBorderColor()), null, null, null);
                 //JOptionPane.showMessageDialog(null,"Diagram saved successfully");   
                    
                }
                
                
 
            }
        }).setToolTipText("Settings");*/

          toolbar.add(Box.createHorizontalGlue());
          toolbar.add(saveInfo);

        return toolbar;
    }
    
}
