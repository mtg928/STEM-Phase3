/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.versioncontrol;

import com.topfield.dao.AssumptionsDao;
import com.topfield.daoImpl.AssumptionsDaoImpl;
import com.topfield.modal.Assumptions;
import com.topfield.modal.Datarefer;
import com.topfield.settings.FrameSettings;
import com.topfield.themes.ButtonColumn;
import com.topfield.themes.PanelButton;
import com.topfield.user.UserInfo;
import com.topfield.utilities.NumberConversion;
import com.topfield.utilities.WineCellRenderer;
import com.topfield.view.popup.AssumptionsPopup;
import com.topfield.view.popup.ReferencePopup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Murali
 */
public class ManageAssumptions extends JPanel{
    
    private AssumptionsDao refDao = new AssumptionsDaoImpl();
    private JTable refTable; 
    private ProAssumptions parent;
    

    public ManageAssumptions(ProAssumptions parent) {
     setLayout(new BorderLayout());
     this.parent = parent; 
        
        List<Assumptions> dataAss = refDao.getAllAssumptionsByProId(UserInfo.getInstance().getProjectNo());
        
        String[] columnNames = {"No","Assume Id","Name", "Description","Value","Equations","Reference","" }; 
        String[][] data = new String[dataAss.size()][columnNames.length+1];
        

   
        for (int i = 0; i < dataAss.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataAss.get(i).getAssId()+"";
            data[i][2] =dataAss.get(i).getName();
            data[i][3] =dataAss.get(i).getDescription();
            data[i][4] =dataAss.get(i).getValue()+"";
            data[i][5] =dataAss.get(i).getEquations();
            data[i][6] =dataAss.get(i).getReference().getDescription();
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
            AssumptionsPopup assumptionsPopup = new AssumptionsPopup();
            Assumptions assumptions = null;

                int selRow = refTable.getSelectedRow();

                if (selRow >= 0) {

                  assumptions = refDao.findById(NumberConversion.NVL(refTable.getValueAt(selRow, 1)+"", 0));
                        assumptionsPopup.setName(assumptions.getName());
                        assumptionsPopup.setDescription(assumptions.getDescription());
                        assumptionsPopup.setValue(assumptions.getValue());
                        assumptionsPopup.setEquations(assumptions.getEquations());
                        assumptionsPopup.setReference(assumptions.getReference());
 

                    JOptionPane.showMessageDialog(null, assumptionsPopup);

                    

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
    
    
   

    private JPanel setbuttonSettings(ProAssumptions parent) {
        
        
        
        
        PanelButton addButton = new PanelButton("Add Assumption");
        addButton.addActionListener(new ActionListener() {
   
        public void actionPerformed(ActionEvent event) {
            Object[] options1 = {"Add", "Cancel"};
            AssumptionsPopup assumptionsPopup = new AssumptionsPopup();
            Assumptions assumptions = new Assumptions();
  
            
                int result = JOptionPane.showOptionDialog(null, assumptionsPopup, "Select to insert Assumptions",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {
                        assumptions.setName(assumptionsPopup.getName());
                        assumptions.setDescription(assumptionsPopup.getDescription());
                        assumptions.setValue(assumptionsPopup.getValue());
                        assumptions.setEquations(assumptionsPopup.getEquations());
                        assumptions.setReference(assumptionsPopup.getReference());
                        assumptions.setProjectId(UserInfo.getInstance().getTclProject());
                        
                        refDao.Save(assumptions);
                        parent.refreshPage();
                    }
               
          }
        });
        
        PanelButton editButton = new PanelButton("Edit Assumption");
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
            Object[] options1 = {"Edit", "Cancel"};
            AssumptionsPopup assumptionsPopup = new AssumptionsPopup();
            Assumptions assumptions = null;

                int selRow = refTable.getSelectedRow();

                if (selRow >= 0) {

                    assumptions = refDao.findById(NumberConversion.NVL(refTable.getValueAt(selRow, 1)+"", 0));
                        assumptionsPopup.setName(assumptions.getName());
                        assumptionsPopup.setDescription(assumptions.getDescription());
                        assumptionsPopup.setValue(assumptions.getValue());
                        assumptionsPopup.setEquations(assumptions.getEquations());
                        assumptionsPopup.setReference(assumptions.getReference());

                   int result = JOptionPane.showOptionDialog(null, assumptionsPopup, "Select to insert Assumption",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {
                        assumptions.setName(assumptionsPopup.getName());
                        assumptions.setDescription(assumptionsPopup.getDescription());
                        assumptions.setValue(assumptionsPopup.getValue());
                        assumptions.setEquations(assumptionsPopup.getEquations());
                        assumptions.setReference(assumptionsPopup.getReference());
                        assumptions.setProjectId(UserInfo.getInstance().getTclProject());
                        
                        refDao.Save(assumptions);
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


        PanelButton removeButton = new PanelButton("Remove Assumption");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = refTable.getSelectedRow();
            
               if(selRow >= 0){
                   
                   if (JOptionPane.showConfirmDialog(null, "Do you want to delete this assumption? ", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            refDao.remove(Integer.parseInt(refTable.getValueAt(selRow, 1)+""));
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
     parent.setComponentAt(0, new ManageAssumptions(parent)); 
   }
     
     
     
    
}
