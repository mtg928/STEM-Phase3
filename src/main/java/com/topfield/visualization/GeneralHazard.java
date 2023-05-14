/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.visualization;

import com.topfield.controller.BusinessException;
import com.topfield.dao.DatareferDao;
import com.topfield.dao.HazardDao;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.daoImpl.HazardDaoImpl;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.modal.Datarefer;
import com.topfield.modal.Hazards;
import com.topfield.modal.MainProductGroup;
import com.topfield.modal.Mpghdr;
import com.topfield.modal.TclProjects;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.user.UserInfo;
import com.topfield.valid.JTextFieldLimit;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.view.popup.FmeaHeaderPopup;
import com.topfield.view.popup.HazardsPopup;
import com.topfield.view.train.ManageArchitecture;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Murali
 */
public class GeneralHazard extends JPanel{
    
    private HazardDao hazDao = new HazardDaoImpl();
    private JTable refTable; 
    private HazardVisual parent;
    

    public GeneralHazard(HazardVisual parent) {
     setLayout(new BorderLayout());
     this.parent = parent; 
        
        List<Hazards> dataHaz = hazDao.getAllHazards();
        
        String[] columnNames = {"No","HazardId","Hazard code", "Hazard Type","Hazard Name","Hazid Linked","Created By","" }; 
        String[][] data = new String[dataHaz.size()][columnNames.length+1];
        

   
        for (int i = 0; i < dataHaz.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataHaz.get(i).getHazardId()+"";
            data[i][2] =dataHaz.get(i).getHazardscode();
            data[i][3] =dataHaz.get(i).getHazardType();
            data[i][4] =dataHaz.get(i).getHazardName();
            data[i][5] =dataHaz.get(i).getHazidLinked();
            data[i][6] =dataHaz.get(i).getCreatedBy().getUsername();
            data[i][7] ="View";
        }

  
        // Column Names 
        
        
        refTable = new JTable(data, columnNames); 
        
        Action dataView = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               
              // int compId = Integer.parseInt(e.getActionCommand());
             //Object[] options1 = {"Edit", "Cancel"};
            HazardsPopup hazardsPopup = new HazardsPopup();
            Hazards hazardRef = null;

                int selRow = refTable.getSelectedRow();

                if (selRow >= 0) {

                      hazardRef = hazDao.findById(NumberConversion.NVL(refTable.getValueAt(selRow, 1)+"", 0));
                        hazardsPopup.setHazardCode(hazardRef.getHazardscode());
                        hazardsPopup.setHazardType(hazardRef.getHazardType());
                        hazardsPopup.setHazardName(hazardRef.getHazardName());
                        hazardsPopup.setHazidLinked(hazardRef.getHazidLinked());

                    JOptionPane.showMessageDialog(null, hazardsPopup);

                    

                } else {
                    JOptionPane.showMessageDialog(null, "Please select row to edit");
                }
                
               
            }


        };
        
        for (int i = 0; i < refTable.getColumnCount(); i++) {
            
            refTable.setDefaultRenderer(refTable.getColumnClass(i), new WineCellRenderer());
            
            
        }
        
        refTable.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = refTable.rowAtPoint(evt.getPoint());
                int col = refTable.columnAtPoint(evt.getPoint());
                
                if (col ==6 ) {
                     

                }
            }
        });

        
        ButtonColumn buttonColumn = new ButtonColumn(refTable, dataView, 7);
        buttonColumn.setMnemonic(KeyEvent.VK_D);
  
        add(setbuttonSettings( parent),BorderLayout.NORTH); 
        //add(new JScrollPane(projects),BorderLayout.CENTER); 
        add(new JScrollPane(refTable),BorderLayout.CENTER); 
        
         //silTable.getTableHeader().setBackground(FrameSettings.getButtonSelColor());
         //silTable.getTableHeader().setBackground(Color.DARK_GRAY);
         
         refTable.getTableHeader().setOpaque(true);
         refTable.setOpaque(true);
         refTable.getTableHeader().setForeground(FrameSettings.getButtonColor());
         refTable.getTableHeader().setFont(refTable.getTableHeader().getFont().deriveFont(Font.BOLD));
         refTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
         refTable.setGridColor(Color.LIGHT_GRAY);
    }
    
    
   

    private JPanel setbuttonSettings(HazardVisual parent) {
        
        
        
        
        PanelButton addButton = new PanelButton("Add Hazard");
        addButton.addActionListener(new ActionListener() {
   
        public void actionPerformed(ActionEvent event) {
            Object[] options1 = {"Add", "Cancel"};
            HazardsPopup hazardsPopup = new HazardsPopup();
            Hazards hazardRef = new Hazards();
            
                int result = JOptionPane.showOptionDialog(null, hazardsPopup, "Select to insert References",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {
                        hazardRef.setHazardscode(hazardsPopup.getHazardCode());
                        hazardRef.setHazardType(hazardsPopup.getHazardType());
                        hazardRef.setHazardName(hazardsPopup.getHazardName());
                        hazardRef.setHazidLinked(hazardsPopup.getHazidLinked());
                        hazardRef.setCreatedBy(UserInfo.getInstance().getuser());
                        hazDao.Save(hazardRef);
                        parent.refreshPage();
                    }
               
          }
        });
        
        PanelButton editButton = new PanelButton("Edit Hazard");
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
            Object[] options1 = {"Edit", "Cancel"};
            HazardsPopup hazardsPopup = new HazardsPopup();
            Hazards hazardRef = null;

                int selRow = refTable.getSelectedRow();

                if (selRow >= 0) {

                  hazardRef = hazDao.findById(NumberConversion.NVL(refTable.getValueAt(selRow, 1)+"", 0));
                        hazardsPopup.setHazardCode(hazardRef.getHazardscode());
                        hazardsPopup.setHazardType(hazardRef.getHazardType());
                        hazardsPopup.setHazardName(hazardRef.getHazardName());
                        hazardsPopup.setHazidLinked(hazardRef.getHazidLinked());

                   int result = JOptionPane.showOptionDialog(null, hazardsPopup, "Select to insert References",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {
                        hazardRef.setHazardscode(hazardsPopup.getHazardCode());
                        hazardRef.setHazardType(hazardsPopup.getHazardType());
                        hazardRef.setHazardName(hazardsPopup.getHazardName());
                        hazardRef.setHazidLinked(hazardsPopup.getHazidLinked());
                        hazardRef.setCreatedBy(UserInfo.getInstance().getuser());
                        hazDao.Save(hazardRef);
                        parent.refreshPage();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select row to edit");
                }

                //model.removeRow(table.getSelectedRow());
                //  ccfDao.deleteCCFComponents(UserInfo.getInstance().getuser().getUsername(), UserInfo.getInstance().getTclProject().getProId(), TOOL_TIP_TEXT_KEY, SOMEBITS);
                //  refreshPage();
            }
        });


        PanelButton removeButton = new PanelButton("Remove Hazard");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = refTable.getSelectedRow();
            
               if(selRow >= 0){
                   
                    if (BusinessException.showBusinessConformation("Do you want to delete this hazard?",true)== JOptionPane.YES_OPTION){
                            hazDao.Delete(Integer.parseInt(refTable.getValueAt(selRow, 1)+""));
                            parent.refreshPage();
                    } else {
                        // no option
                    }



                }else{
                   JOptionPane.showMessageDialog(null, "Please select row to delete");
                }

          }


        });
        
        PanelButton refreashButton = new PanelButton("Refresh");
        refreashButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            parent.refreshPage();
          }
        });
        
        JPanel buttonPanel = new JPanel();
        //inputPanel.setBackground(new Color(214, 234, 248));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreashButton);
        
        return buttonPanel;
    }
     
     
 
     
   private void refreshPage() { 
     parent.setComponentAt(0, new GeneralHazard(parent)); 
   }
     
     
     
    
}