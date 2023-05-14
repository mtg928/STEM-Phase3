/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.versioncontrol;

import com.topfield.dao.DatareferDao;
import com.topfield.daoImpl.DatareferDaoImpl;
import com.topfield.dao.MainProductGroupDao;
import com.topfield.daoImpl.MainProductGroupDaoImpl;
import com.topfield.modal.Datarefer;
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
import com.topfield.view.popup.ReferencePopup;
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
public class ManageReferences extends JPanel{
    
    private DatareferDao refDao = new DatareferDaoImpl();
    private JTable refTable; 
    private DataReference parent;
    

    public ManageReferences(DataReference parent) {
     setLayout(new BorderLayout());
     this.parent = parent; 
        
        List<Datarefer> dataMpg = refDao.getAllDatarefer();
        
        String[] columnNames = {"No","RefId","ItemId", "Ref Type","Title","Section","Value","DocumentNo/ URL","Version","" }; 
        String[][] data = new String[dataMpg.size()][columnNames.length+1];
        

   
        for (int i = 0; i < dataMpg.size(); i++) {
            data[i][0] =(i+1)+"";            
            data[i][1] =dataMpg.get(i).getRefid()+"";
            data[i][2] =dataMpg.get(i).getItemId();
            data[i][3] =dataMpg.get(i).getType();
            data[i][4] =dataMpg.get(i).getDescription();
            data[i][5] =dataMpg.get(i).getSection();
            data[i][6] =dataMpg.get(i).getValue();
            data[i][7] =dataMpg.get(i).getRefurl();
            data[i][8] =dataMpg.get(i).getVersion();
            data[i][9] ="View";
        }

  
        // Column Names 
        
        
        refTable = new JTable(data, columnNames); 
        
        Action dataView = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
               
              // int compId = Integer.parseInt(e.getActionCommand());
             //Object[] options1 = {"Edit", "Cancel"};
            ReferencePopup referencePopup = new ReferencePopup();
            Datarefer datarefer = null;

                int selRow = refTable.getSelectedRow();

                if (selRow >= 0) {

                  datarefer = refDao.findById(NumberConversion.NVL(refTable.getValueAt(selRow, 1)+"", 0));
                        referencePopup.setItemId(datarefer.getItemId());
                        referencePopup.setRefType(datarefer.getType());
                        referencePopup.setTitle(datarefer.getDescription());
                        referencePopup.setSection(datarefer.getSection());
                        referencePopup.setValue(datarefer.getValue());
                        referencePopup.setDocumentNo_URL(datarefer.getRefurl());
                        referencePopup.setVersion(datarefer.getVersion());
                        referencePopup.setAppType(datarefer.getCaltype());

                    JOptionPane.showMessageDialog(null, referencePopup);

                    

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

        
        ButtonColumn buttonColumn = new ButtonColumn(refTable, dataView, 9);
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
    
    
   

    private JPanel setbuttonSettings(DataReference parent) {
        
        
        
        
        PanelButton addButton = new PanelButton("Add Reference");
        addButton.addActionListener(new ActionListener() {
   
        public void actionPerformed(ActionEvent event) {
            Object[] options1 = {"Add", "Cancel"};
            ReferencePopup referencePopup = new ReferencePopup();
            Datarefer datarefer = new Datarefer();
            
                int result = JOptionPane.showOptionDialog(null, referencePopup, "Select to insert References",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {
                        datarefer.setItemId(referencePopup.getItemId());
                        datarefer.setType(referencePopup.getRefType());
                        datarefer.setDescription(referencePopup.getTitle());
                        datarefer.setSection(referencePopup.getSection());
                        datarefer.setValue(referencePopup.getValue());
                        datarefer.setRefurl(referencePopup.getDocumentNo_URL());
                        datarefer.setVersion(referencePopup.getVersion());
                        datarefer.setCaltype(referencePopup.getAppType());
                        datarefer.setUsername(UserInfo.getInstance().getuser());
                        refDao.Save(datarefer);
                        parent.refreshPage();
                    }
               
          }
        });
        
        PanelButton editButton = new PanelButton("Edit Reference");
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
            Object[] options1 = {"Edit", "Cancel"};
            ReferencePopup referencePopup = new ReferencePopup();
            Datarefer datarefer = null;

                int selRow = refTable.getSelectedRow();

                if (selRow >= 0) {

                  datarefer = refDao.findById(NumberConversion.NVL(refTable.getValueAt(selRow, 1)+"", 0));
                        referencePopup.setItemId(datarefer.getItemId());
                        referencePopup.setRefType(datarefer.getType());
                        referencePopup.setTitle(datarefer.getDescription());
                        referencePopup.setSection(datarefer.getSection());
                        referencePopup.setValue(datarefer.getValue());
                        referencePopup.setDocumentNo_URL(datarefer.getRefurl());
                        referencePopup.setVersion(datarefer.getVersion());
                        referencePopup.setAppType(datarefer.getCaltype());

                   int result = JOptionPane.showOptionDialog(null, referencePopup, "Select to insert References",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options1, null);

                    if (result == JOptionPane.YES_OPTION) {
                        datarefer.setItemId(referencePopup.getItemId());
                        datarefer.setType(referencePopup.getRefType());
                        datarefer.setDescription(referencePopup.getTitle());
                        datarefer.setSection(referencePopup.getSection());
                        datarefer.setValue(referencePopup.getValue());
                        datarefer.setRefurl(referencePopup.getDocumentNo_URL());
                        datarefer.setVersion(referencePopup.getVersion());
                        datarefer.setCaltype(referencePopup.getAppType());
                        datarefer.setUsername(UserInfo.getInstance().getuser());
                        refDao.Save(datarefer);
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


        PanelButton removeButton = new PanelButton("Remove Reference");
        removeButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent event) {
            
            int selRow = refTable.getSelectedRow();
            
               if(selRow >= 0){
                   
                   if (JOptionPane.showConfirmDialog(null, "Do you want to delete this reference?", "WARNING",
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
     parent.setComponentAt(0, new ManageReferences(parent)); 
   }
     
     
     
    
}